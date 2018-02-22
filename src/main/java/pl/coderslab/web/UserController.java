package pl.coderslab.web;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Image;
import pl.coderslab.entity.Request;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.FaqRepo;
import pl.coderslab.repo.ImageRepo;
import pl.coderslab.repo.LocalizationRepo;
import pl.coderslab.repo.RequestRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.ImageService;
import pl.coderslab.service.RequestService;
import pl.coderslab.service.RoomService;
import pl.coderslab.service.UserService;

@Controller
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private static String UPLOADED_FOLDER = "/home/karolpat/eclipse-workspace/demo2/src/main/resources/static/storage/";

	private final String WAIT = "Please wait until admin accept your request.";

	private UserService userService;
	private RoomService roomService;
	private ImageService imageService;
	private RequestService requestService;

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
	private RequestRepo reqRepo;

	public UserController(UserService userService, RoomService roomService, ImageService imageService,
			RequestService requestService) {
		this.userService = userService;
		this.roomService = roomService;
		this.imageService = imageService;
		this.requestService = requestService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/**
	 * Sending request to admin to become an owner.
	 * 
	 * @param username
	 *            - username of user who wants to be an owner.
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/user/profile/requests/upgrade/{username}")
	public String ownerReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {

		User user = userService.findByUserName(username);
		requestService.sendOwnerReq(user);
		user.setOwnerReq(true);

		redirectAttributes.addFlashAttribute("message", WAIT);
		userService.save(user);

		return "redirect:/user/profile";
	}

	/**
	 * Sending request to admin to become enabled.
	 * 
	 * @param username
	 *            - username of user who wants to be enabled.
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/user/profile/requests/enable/{username}")
	public String enabledReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {

		User user = userService.findByUserName(username);
		user.setEnableReq(true);
		redirectAttributes.addFlashAttribute("message", WAIT);

		userService.save(user);

		return "redirect:/user/profile";
	}

	/**
	 * Sending request to admin to become a manager.
	 * 
	 * @param username
	 *            - username of user who wants to be a manager.
	 * @param redirectAttributes
	 * @return
	 */
	@GetMapping("/user/profile/requests/manager/{username}")
	public String managerReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {

		User user = userService.findByUserName(username);
		user.setManagerReq(true);
		redirectAttributes.addFlashAttribute("message", WAIT);

		userService.save(user);

		return "redirect:/user/profile";
	}

	/**
	 * @return view to show user's details.
	 */
	@GetMapping("/user/profile")
	public String profile() {
		return "profile";
	}

	/**
	 * Saving uploaded image as an user's avatar.
	 * 
	 * @param file
	 *            - an uploaded image.
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/user/upload")
	public String updateAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/user/profile";
		}

		try {
			Image image = imageService.addNewImage(file);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename().replaceAll(" ", "") + "'");
			User user = userService.findByUserName(currentUser());
			userService.saveWithImage(user, image);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}

	@GetMapping("/user/rooms")
	public String roomsAsHost(Model model) {

		User user = userService.findByUserName(currentUser());
		model.addAttribute("room", roomService.findAllByHost(user.getId()));

		return "rooms";
	}

	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
