package pl.coderslab.web;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Faq;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Message;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.service.ChatService;
import pl.coderslab.service.FaqService;
import pl.coderslab.service.ImageService;
import pl.coderslab.service.LocalizationService;
import pl.coderslab.service.MessageService;
import pl.coderslab.service.RoomService;
import pl.coderslab.service.UserService;
import pl.coderslab.util.PdfGenerating;

/**
 * @author karolpat
 *
 */
@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	private UserService userService;
	private ImageService imageService;
	private LocalizationService localizationService;
	private RoomService roomService;
	private FaqService faqService;
	private ChatService chatService;
	private MessageService messageService;

	public HomeController(UserService userService, ImageService imageService, LocalizationService localizationService,
			RoomService roomService, FaqService faqService, ChatService chatService, MessageService messageService) {
		this.userService = userService;
		this.imageService = imageService;
		this.localizationService = localizationService;
		this.roomService = roomService;
		this.faqService=faqService;
		this.chatService=chatService;
		this.messageService=messageService;
	}

	/** Quick method to get username of current logged in user.
	 * @return username of curent user.
	 */
	public String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	/** Registration form of new user.
	 * @param model
	 * @return
	 */
	@GetMapping("/register")
	public String registerForm(Model model) {

		model.addAttribute("user", new User());
		return "register";
	}

	/** Registration form of new user.
	 * @param user - new user that is being registering.
	 * @param bresult
	 * @param model
	 * @return login form.
	 */
	@PostMapping("/register")
	public String register(User user, BindingResult bresult, Model model){
		if (bresult.hasErrors()) {
			model.addAttribute(user);
			model.addAttribute("message", bresult.getAllErrors());
			return "/register";

		} else {
			Image image = imageService.defaultUserImage();
			userService.saveUser(user, image);
			return "redirect:/login";
		}
	}

	/** User(Owner role)
	 * @param model
	 * @return view of room addition form.
	 */
	@GetMapping("/owner/offer")
	public String offerForm(Model model) {

		Room room = new Room();
		Localization loc = new Localization();

		model.addAttribute("loc", loc);
		model.addAttribute("room", room);

		return "offerForm";
	}

	/** Form to upload new room of User(Owner role)
	 * @param room - entity to be save with parameters that are inserted by User.
	 * @param localization - country, city and street that is inserted by User is bound to Localization entity. Room has only reference to this location.
	 * @param file - image file that is uploaded by user.
	 * @param redirectAttributes - allows to show message on redirected view.
	 * @return
	 */
	@PostMapping("/owner/offer")
	public String offer(Room room, Localization localization, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/user/offer";
		}
		try {

			Image image = imageService.addNewImage(file);
			Localization roomLocalization = localizationService.newLocalization(localization);
			User user = userService.findByUserName(currentUser());
			roomService.addNewRoom(image, user, roomLocalization, room);
			localizationService.roomLocalization(roomLocalization, room);

			redirectAttributes.addFlashAttribute("message", "You successfully uploaded new room");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}

	/**Form of user personal details editing.
	 * @param model
	 * @return
	 */
	@GetMapping("user/profile/edit")
	public String editForm(Model model) {

		User user = userService.findByUserName(currentUser());
		model.addAttribute("user", user);

		return "editForm";
	}

	/**Form of user personal details editing.
	 * @param model
	 * @param user - user of whom profile is edited. 
	 * @param bresult
	 * @return
	 */
	@PostMapping("/profile/edit")
	public String saveEdit(Model model, User user, BindingResult bresult) {

		if (!bresult.hasErrors()) {
			User tmp = userService.findByUserName(currentUser());
			userService.editUser(user, tmp.getId());
			return "redirect:/user/profile";
		} else {

			return "redirect:/user/profile/edit";
		}
	}

	
	/** Welcome page where some room offers and faq section are presented.
	 * @param model
	 * @return index - welcome page;
	 * @throws FileNotFoundException 
	 */
	@GetMapping("/")
	public String index(Model model, HttpServletResponse response) throws FileNotFoundException {
		model.addAttribute("rooms", roomService.findAll());
		model.addAttribute("faqList", faqService.findAll());
		PdfGenerating test = new PdfGenerating();
		String fileName = "pdfs/"+currentUser();
		test.testPdf(fileName);
		//TODO
//		 try {
//		      // get your file as InputStream
//		      InputStream is = new FileInputStream(fileName);
//		      // copy it to response's OutputStream
//		      org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
//		      response.flushBuffer();
//		    } catch (IOException ex) {
//		      log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
//		      throw new RuntimeException("IOError writing file to output stream");
//		    }
		return "index";
	}

	/** Changing faq question rate when user press the like button.
	 * @param id - id of question from faq section that like button has been pressed.
	 * @return welcome page.
	 */
	@RequestMapping("/like")
	public String indexFaq(@RequestParam long id) {
		Faq faq = faqService.findById(id);
		faqService.changeRate(faq);

		return "redirect:/";
	}

	/** List of all rooms that user (Owner role) owns.
	 * @param model
	 * @return
	 */
	@RequestMapping("/owner/rooms")
	public String list(ModelMap model) {
		User user = userService.findByUserName(currentUser());

		//List of rooms that belong to current user (Owner role).
		model.addAttribute("rooms", roomService.findAllByOwnerId(user.getId()));
		return "roomList";
	}

	/** List of all available chats of current user.
	 * @param model
	 * @return a view with all chats.
	 */
	@GetMapping("/user/chat")
	public String chat(Model model) {

		User user = userService.findByUserName(currentUser());

		//List of all chats
		model.addAttribute("chat", chatService.findAllByUserId(user.getId()));
		return "chat";
	}

	/** A list of messages between users in chat of given in URL id.
	 * @param model
	 * @param id - id of chat which messages are shown.
	 * @return
	 */
	@GetMapping("/user/messages/{id}")
	public String messages(Model model, @PathVariable("id") long id) {

		List<Message> mess = messageService.findAllByChatId(id);

		model.addAttribute("mess", mess);

		return "messages";
	}

	/** Action to reply to user of id given in the URL. Author (sender) of the message is the current user.
	 * @param request
	 * @param to - id to find user who will receive the message.
	 * @param chat - id of the chat where message will be added.
	 * @param content - text that is sent between users.
	 * @return
	 */
	@PostMapping("/user/message/{to}/{chat}")
	public String reply(HttpServletRequest request, @PathVariable("to") long to, @PathVariable("chat") long chat,
			@RequestParam("content") String content) {
		String previous = request.getHeader("Referer");

		Chat tmpChat = chatService.findOne(chat);
		messageService.reply(tmpChat, to, content);
		
		return "redirect:" + previous;
	}

	/** Action to promote user room. Still TODO
	 * @param id - id of room that should be promoted
	 * @param model
	 * @return
	 */
	@GetMapping("/room/promote/{id}")
	public String promoteForm(@PathVariable("id") long id, Model model) {
		
		//TODO still does not work.
		
		model.addAttribute("room", roomService.findOne(id));

		return "promoteForm";
	}

	/** The form to send message to user of id given in URL.
	 * @param to - id of user who will receive the message.
	 * @param model
	 * @return
	 */
	@GetMapping("/send/{to}")
	public String sendForm(@PathVariable("to") long to, Model model) {

		User receiver = userService.findOne(to);
		model.addAttribute("user", receiver);

		return "sendForm";
	}

	/** Action of sending messages from author to receiver.
	 * @param from - id of user (author) who sends the message.
	 * @param to - id of user (receiver) who will receive the message.
	 * @param content - text of the message.
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/send/message/{to}/{from}")
	public String sendMessage(@PathVariable("from") long from, @PathVariable("to") long to,
			@RequestParam("content") String content, RedirectAttributes redirectAttributes) {

		User receiver = userService.findOne(to);
		messageService.sendMessage(from, to, content);
		redirectAttributes.addFlashAttribute("info", "Message to " + receiver.getUsername() + " sent.");

		return "redirect:/user/chat";
	}

	/** ModelAttribute to provide current user to views.
	 * @param model
	 */
	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
