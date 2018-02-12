package pl.coderslab.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Message;
import pl.coderslab.entity.Request;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.ChatRepo;
import pl.coderslab.repo.MessageRepo;
import pl.coderslab.repo.RequestRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;

@Controller
public class AdminController {

	private UserService userServ;

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RequestRepo reqRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private RoomRepo roomRepo;

	@Autowired
	private ChatRepo chatRepo;

	@Autowired
	private MessageRepo messageRepo;

	public AdminController(UserService userServ) {
		this.userServ = userServ;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@GetMapping("/admin/requests")
	public String showRequests(Model model) {

		List<Request> reqList = reqRepo.findAllByChecked(false);
		model.addAttribute("rList", reqList);

		return "/admin/requests";
	}

	@PostMapping("/admin/request/owner/{id}")
	public String acceptOwnerReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

		Request req = reqRepo.findOne(id);
		log.info("id" + req.getId());

		req.setChecked(true);
		User user = userRepo.findOne(req.getUser().getId());
		user.setRole(roleRepo.findBySubName("Owner"));

		redirectAttributes.addFlashAttribute("info",
				"Request of " + user.getUsername() + " accepted. Now has role - Owner");

		userRepo.save(user);
		reqRepo.save(req);

		return "redirect:/admin/requests";
	}
	
	@PostMapping("/admin/request/enable/{id}")
	public String acceptEnableReq(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
		
		Request req = reqRepo.findOne(id);
		log.info("id" + req.getId());

		req.setChecked(true);
		User user = userRepo.findOne(req.getUser().getId());
		log.info(user.toString());
		user.setEnabled(true);

		redirectAttributes.addFlashAttribute("info",
				"User - " + user.getUsername() + " is now enabled.");
		log.info(user.toString());
		log.info(" "+user.isEnabled());

		userRepo.save(user);
		reqRepo.save(req);

		return "redirect:/admin/requests";
	}

	@GetMapping("/admin/user/{id}")
	public String showUserDetails(@PathVariable("id") long id, Model model) {

		User user = userRepo.findOne(id);
		List<Room> rList = roomRepo.findAllByOwnerId(id);
		Chat chat = new Chat();
		Message message = new Message();

		log.info(user.toString());
		log.info(user.getImage().getPath());

		model.addAttribute("chat", chat);
		model.addAttribute("message", message);
		model.addAttribute("rList", rList);
		model.addAttribute("user", user);
		
		return "/admin/user/user";
	}

	@GetMapping("/admin/user/room/{id}")
	public String showUserRoom(@PathVariable("id") long id, Model model) {

		Room room = roomRepo.findOne(id);
		model.addAttribute("room", room);

		return "/admin/user/room";
	}

	@PostMapping("admin/user/message/{from}/{to}")
	public String sendMessage(@PathVariable("from") long idFrom, @PathVariable("to") long idTo, RedirectAttributes redirectAttributes) {
		
		User author = userRepo.findOne(idFrom);
		User receiver = userRepo.findOne(idTo);
		
		redirectAttributes.addFlashAttribute("info",
				"Message to " + receiver.getUsername() + " sent.");

		return "redirect:/admin/requests";
	}

	@ModelAttribute
	public void userModer(Model model) {
		User user = userServ.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
