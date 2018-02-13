package pl.coderslab.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
import pl.coderslab.service.UserService;
@Controller
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	private static String UPLOADED_FOLDER = "/home/karolpat/eclipse-workspace/demo2/src/main/resources/static/storage/";
	
	private final String GET_OWNER="I want to become an owner.";
	private final String GET_ENABLED="I want to get enabled.";
	private final String GET_MANAGER="I want to become a manager.";
	private final String WAIT = "Please wait until admin accept your request.";
	
	private UserService userService;
	
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


	public UserController(UserService userService) {
		this.userService = userService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	
	@GetMapping("/user/profile/requests/upgrade/{username}")
	public String ownerReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {
		
		User user = userService.findByUserName(username);
		Request req = new Request();
		
		req.setDescription(GET_OWNER);
		req.setUser(user);
		user.setOwnerReq(true);
		
		redirectAttributes.addFlashAttribute("message", WAIT);
		
		reqRepo.save(req);
		userRepo.save(user);
		
		return "redirect:/user/profile";
	}
	
	@GetMapping("/user/profile/requests/enable/{username}")
	public String enabledReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {
		
		User user = userService.findByUserName(username);
		Request req = new Request();
		
		req.setDescription(GET_ENABLED);
		req.setUser(user);
		user.setEnableReq(true);
		
		redirectAttributes.addFlashAttribute("message", WAIT);
		
		reqRepo.save(req);
		userRepo.save(user);
		
		return "redirect:/user/profile";
	}
	
	@GetMapping("/user/profile/requests/manager/{username}")
	public String managerReq(@PathVariable("username") String username, RedirectAttributes redirectAttributes) {
		
		User user = userService.findByUserName(username);
		Request req = new Request();
		
		req.setDescription(GET_MANAGER);
		req.setUser(user);
		user.setManagerReq(true);
		
		redirectAttributes.addFlashAttribute("message", WAIT);
		
		reqRepo.save(req);
		userRepo.save(user);
		return "redirect:/user/profile";
	}
	
	
	@GetMapping("/user/profile")
	public String profile(Model model) {

		User user = userService.findByUserName(currentUser());
		Image image = imageRepo.findOneByUserId(user.getId());
		List<Room> rooms = roomRepo.findAllByOwnerId(user.getId());

		// log.info(image.getPath());
		// log.info(rooms.toString());

		model.addAttribute("rooms", rooms);
		model.addAttribute("image", image);
//		model.addAttribute("currUser", user);
		return "profile";
	}
	

	@PostMapping("/user/upload")
	public String updateAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		log.info(file.getName());
		log.info(file.getOriginalFilename());
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/user/profile";
		}

		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename().replaceAll(" ", ""));
			Files.write(path, bytes);

			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename().replaceAll(" ", "") + "'");
			User user = userService.findByUserName(currentUser());
			Image image = imageRepo.findOneById(user.getImage().getId());
			log.info(user.toString());
			log.info(image.toString());
			image.setPath("../storage/" + file.getOriginalFilename().replaceAll(" ", ""));
			imageRepo.save(image);
			userRepo.save(user);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "redirect:/user/profile";
	}
	
	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}
	
}
