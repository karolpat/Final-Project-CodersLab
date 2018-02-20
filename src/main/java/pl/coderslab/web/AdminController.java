package pl.coderslab.web;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Message;
import pl.coderslab.entity.Request;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.service.MessageService;
import pl.coderslab.service.RequestService;
import pl.coderslab.service.RoomService;
import pl.coderslab.service.UserService;

/**
 * @author karolpat
 *
 */
@Controller
public class AdminController {

	private UserService userServ;
	private MessageService messageService;
	private RequestService reqService;
	private RoomService roomService;


	public AdminController(UserService userServ, MessageService messageService, RequestService requestService, RoomService roomService) {
		this.userServ = userServ;
		this.messageService = messageService;
		this.reqService = requestService;
		this.roomService=roomService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/**
	 * List of all requests from users. Only for user with Admin role.
	 * 
	 * @param model
	 * @return view with requests list.
	 */
	@GetMapping("/admin/requests")
	public String showRequests(Model model) {

		List<Request> reqList = reqService.findAllChecked(false);
		model.addAttribute("rList", reqList);

		return "/admin/requests";
	}

	/**
	 * Action to accept user's request of becoming Owner.
	 * 
	 * @param id
	 *            - id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/owner/{id}")
	public String acceptOwnerReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.acceptOwnerReq(id);
		redirectAttributes.addFlashAttribute("info",
				"Request of " + user.getUsername() + " accepted. Now has role - Owner");

		return "redirect:/admin/requests";
	}

	/**
	 * Action to reject user's request of becoming Owner.
	 * 
	 * @param id
	 *            -id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/owner/reject/{id}")
	public String rejectOwnerReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.rejectOwnerReq(id);
		redirectAttributes.addFlashAttribute("info",
				"Request of " + user.getUsername() + " rejected. Role did not change.");

		return "redirect:/admin/requests";
	}

	/**
	 * Action to accept user's request of becoming Manager.
	 * 
	 * @param id
	 *            id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/manager/{id}")
	public String acceptManagerReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.acceptManagerReq(id);
		redirectAttributes.addFlashAttribute("info",
				"Request of " + user.getUsername() + " accepted. Now has role - Manager");

		return "redirect:/admin/requests";
	}

	/**
	 * Action to reject user's request of becoming Manager.
	 * 
	 * @param id
	 *            id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/manager/reject/{id}")
	public String rejectManagerReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.rejectManagerReq(id);
		redirectAttributes.addFlashAttribute("info",
				"Request of " + user.getUsername() + " rejected. Role did not change.");

		return "redirect:/admin/requests";
	}

	/**
	 * Action to accept user's request of becoming enabled
	 * 
	 * @param id
	 *            id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/enable/{id}")
	public String acceptEnableReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.acceptEnableReq(id);
		redirectAttributes.addFlashAttribute("info", "User - " + user.getUsername() + " is now enabled.");

		return "redirect:/admin/requests";
	}

	/**
	 * Action to reject user's request of becoming enabled
	 * 
	 * @param id
	 *            id of the request.
	 * @param redirectAttributes
	 * @return redirect to requests list.
	 */
	@PostMapping("/admin/request/enable/reject/{id}")
	public String rejectEnableReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		User user = reqService.rejectEnableReq(id);
		redirectAttributes.addFlashAttribute("info", "User - " + user.getUsername() + " is still unenable.");

		return "redirect:/admin/requests";
	}

	/**
	 * Shows all details of user with id given in the URL, also rooms that is owner
	 * of.
	 * 
	 * @param id
	 *            id of the user to be shown.
	 * @param model
	 * @return view of table with user personal details.
	 */
	@GetMapping("/admin/user/{id}")
	public String showUserDetails(@PathVariable("id") long id, Model model) {

		User user = userServ.findOne(id);
		List<Room> rList = roomService.findAllByOwnerId(id);
		Message message = new Message();

		model.addAttribute("message", message);
		model.addAttribute("rList", rList);
		model.addAttribute("user", user);

		return "/admin/user/user";
	}

	/**
	 * Shows all details of room with id given in the URL.
	 * 
	 * @param id
	 *            id of the room to be shown.
	 * @param model
	 * @return view of table with room details.
	 */
	@GetMapping("/admin/user/room/{id}")
	public String showUserRoom(@PathVariable("id") long id, Model model) {

		Room room = roomService.findOne(id);
		model.addAttribute("room", room);

		return "/admin/user/room";
	}

	/**
	 * Action of sending messages from author to receiver.
	 * 
	 * @param from
	 *            - id of user (author) who sends the message.
	 * @param to
	 *            - id of user (receiver) who will receive the message.
	 * @param content
	 *            - text of the message.
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/admin/message/{to}/{from}")
	public String sendMessage(@PathVariable("from") long from, @PathVariable("to") long to,
			@RequestParam("content") String content, RedirectAttributes redirectAttributes) {

		User receiver = userServ.findOne(to);
		messageService.sendMessage(from, to, content);

		redirectAttributes.addFlashAttribute("info", "Message to " + receiver.getUsername() + " sent.");

		return "redirect:/admin/requests";
	}

	/**
	 * List of all users. Possibility to send message to selected user.
	 * 
	 * @param model
	 * @return view with all users.
	 */
	@GetMapping("/admin/users")
	public String showUsers(Model model) {

		model.addAttribute("users", userServ.getUsers());
		return "/admin/users";
	}
	
	/** ModelAttribute to provide current user to views.
	 * @param model
	 */
	@ModelAttribute
	public void userModer(Model model) {
		User user = userServ.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}


}
