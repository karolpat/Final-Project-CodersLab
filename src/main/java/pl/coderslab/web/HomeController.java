package pl.coderslab.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Faq;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Message;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.ChatRepo;
import pl.coderslab.repo.FaqRepo;
import pl.coderslab.repo.ImageRepo;
import pl.coderslab.repo.LocalizationRepo;
import pl.coderslab.repo.MessageRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;
import pl.coderslab.util.Currency;

@Controller
public class HomeController {

	private static String UPLOADED_FOLDER = "/home/karolpat/eclipse-workspace/demo2/src/main/resources/static/storage/";

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	private UserService userService;

	// @Autowired
	// private StudentRepo stud;

	@Autowired
	private FaqRepo faqRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private LocalizationRepo localRepo;
 
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ImageRepo imageRepo;

	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private ChatRepo chatRepo;
	
	@Autowired
	private MessageRepo messageRepo;


	public HomeController(UserService userService) {
		this.userService = userService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}


	@GetMapping("/owner/offer")
	public String offerForm(Model model) {

		Room room = new Room();
		Localization loc = new Localization();

		model.addAttribute("loc", loc);
		model.addAttribute("room", room);

		return "offerForm";
	}

	@PostMapping("/owner/offer")
	public String offer(Room room, Localization localization, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		log.info(localization.getCity());
		log.info(room.getName());

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/user/offer";
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename().replaceAll(" ", ""));
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded new room");

			Image image = new Image();
			imageRepo.saveAndFlush(image);

			roomRepo.saveAndFlush(room);

			image.setPath("../storage/" + file.getOriginalFilename().replaceAll(" ", ""));
			room.setImage(image);
			User user = userService.findByUserName(currentUser());
			room.setOwner(user);
			room.setAdded(LocalDate.now());
			// roomRepo.saveAndFlush(room);
			localRepo.saveAndFlush(localization);
			room.setLocalization(localization);

			localRepo.save(localization);
			roomRepo.save(room);
			imageRepo.save(image);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/user/profile";
	}

	@GetMapping("user/profile/edit")
	public String editForm(Model model) {

		User user = userService.findByUserName(currentUser());

		model.addAttribute("user", user);

		return "editForm";
	}

	@PostMapping("/profile/edit")
	public String saveEdit(Model model, String firstName, String lastName, String gender, String country, String city,
			String street, int phoneNumber) {

		User user = userService.findByUserName(currentUser());
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setCountry(country);
		user.setCity(city);
		user.setStreet(street);
		user.setPhoneNumber(phoneNumber);
		log.info(user.toString());
		userRepo.save(user);

		return "redirect:/user/profile";
	}

	@RequestMapping("/charts")
	public String charts() {
		return "charts";
	}

	@RequestMapping("/forms")
	public String forms() {
		return "forms";
	}

	@RequestMapping("/tables")
	public String tables() {
		return "tables";
	}

	@GetMapping("/register")
	public String registerForm(Model model) {

		User u = new User();
		model.addAttribute("user", u);

		model.addAttribute("roleList", roleRepo.findAll());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Valid User user, BindingResult bresult, Model model) {
		if (bresult.hasErrors()) {
			log.info("błąd" + user.toString());
			model.addAttribute(user);
			model.addAttribute("message", bresult.getAllErrors());
			return "/register";

		} else {
			log.info(user.toString());
			user.setCreated(new LocalDate());
			Image image = new Image();
			image.setPath("../storage/default.jpg");
			imageRepo.saveAndFlush(image);
			user.setImage(image);
			userService.saveUser(user);
			return "redirect:/login";

		}
	}

	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "admin";
	}

	@GetMapping("/")
	public String index(Model model) throws Exception {
		log.info("some log");
		model.addAttribute("rooms", roomRepo.findAll());
		model.addAttribute("faqList", faqRepo.findAll());
		Currency currency = new Currency();
		
		currency.getCurrency();
		return "index";
	}

	@RequestMapping("/like")
	public String indexFaq(@RequestParam long id) {
		Faq faq = faqRepo.findOne(id);
		faq.setRate(faq.getRate() + 1);
		faqRepo.save(faq);
		
//		User user= userService.findByUserName(currentUser());
//		log.info(user.getRole().getName());

		return "redirect:/";
	}

	@RequestMapping("/owner/rooms")
	public String list(ModelMap model) {
		User user = userService.findByUserName(currentUser());

		// for(Room r : roomRepo.findAllByOwnerId(user.getId())) {
		// log.info(r.getImage().getPath());
		// }
		model.addAttribute("rooms", roomRepo.findAllByOwnerId(user.getId()));
		return "roomList";
	}

	
	@GetMapping("/user/chat")
	public String chat(Model model) {
		
		User user = userService.findByUserName(currentUser());
		List<Chat> chat = chatRepo.findAllByUserId(user.getId());
		
		model.addAttribute("chat", chat);
		log.info(chatRepo.findAllByUserId(user.getId()).get(0).toString());
		
		return "chat";
	}
	
	@GetMapping("/user/messages/{id}")
	public String messages(Model model, @PathVariable("id") long id) {
		
		List<Message> mess = messageRepo.findAllByChatId(id);
		
		model.addAttribute("mess", mess);
		
		return "messages";
	}
	
	@PostMapping("/user/message/{to}/{chat}")
	public String reply(HttpServletRequest request, @PathVariable("to") long to, @PathVariable("chat") long chat, @RequestParam("content") String content) {
		
		String previous = request.getHeader("Referer");
		
		Message message = new Message();
		message.setChat(chatRepo.findOne(chat));
		log.info(message.getChat().getId()+"ok");
		message.setContent(content);
		message.setCreated(LocalDate.now());
		message.setSendFrom(userService.findByUserName(currentUser()));
		message.setSendTo(userRepo.findOne(to));
		
		messageRepo.save(message);
		return "redirect:"+previous;
	}

	@GetMapping("/us")
	@ResponseBody
	public String list() {
		List<User> list = userRepo.findAll();
		Gson gson = new Gson();

		String result = gson.toJson(list);

		return result;

	}
	
	@GetMapping("/room/promote/{id}")
	public String promoteForm(@PathVariable("id") long id, Model model) {
		
		model.addAttribute("room", roomRepo.findOne(id));
		
		return "promoteForm";
	}
	
	@GetMapping("/send/{to}")
	public String sendForm(@PathVariable("to") long to, Model model) {
		
		User receiver = userRepo.findOne(to);
		model.addAttribute("user", receiver);
		
		return "sendForm";
	}
	
	@PostMapping("/send/message/{to}/{from}")
	public String sendMessage(@PathVariable("from") long from, @PathVariable("to") long to, @RequestParam("content") String content, RedirectAttributes redirectAttributes) {
		
		User author = userRepo.findOne(from);
		User receiver = userRepo.findOne(to);
		
		List<User> chatUserList = new ArrayList<User>();
		chatUserList.add(author);
		chatUserList.add(receiver);
		
		Chat chat = new Chat();
		chatRepo.saveAndFlush(chat);
//		chatRepo.saveAndFlush(chat);
		
		chat.setUser(chatUserList);
		
		Message mess = new Message();
		mess.setContent(content);
		mess.setSendFrom(author);
		mess.setSendTo(receiver);
		mess.setCreated(LocalDate.now());
		mess.setChat(chat);
		messageRepo.saveAndFlush(mess);
		
		List<Message> messList;
		
		if(chat.getMessage()==null) {
			messList = new ArrayList<>();
		}else {
			messList = chat.getMessage();
		}
		messList.add(mess);
		chat.setMessage(messList);
		log.info(mess.getContent());
		chatRepo.save(chat);
		
		
		redirectAttributes.addFlashAttribute("info",
				"Message to " + receiver.getUsername() + " sent.");

		return "redirect:/admin/users";
	}
	
	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
