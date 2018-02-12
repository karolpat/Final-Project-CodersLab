package pl.coderslab.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import pl.coderslab.entity.Faq;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.FaqRepo;
import pl.coderslab.repo.ImageRepo;
import pl.coderslab.repo.LocalizationRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;

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


	public HomeController(UserService userService) {
		this.userService = userService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@GetMapping({ "/user/profile", "/manager/profile" })
	public String profile(Model model) {

		User user = userService.findByUserName(currentUser());
		Image image = imageRepo.findOneByUserId(user.getId());
		List<Room> rooms = roomRepo.findAllByOwnerId(user.getId());

		// log.info(image.getPath());
		// log.info(rooms.toString());

		model.addAttribute("rooms", rooms);
		model.addAttribute("image", image);
		model.addAttribute("currUser", user);
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

			// Get the file and save it somewhere
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
		// return "profile";
	}

	@GetMapping("/user/offer")
	public String offerForm(Model model) {

		Room room = new Room();
		Localization loc = new Localization();

		model.addAttribute("loc", loc);
		model.addAttribute("room", room);

		return "offerForm";
	}

	@PostMapping("/user/offer")
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
					"You successfully uploaded '" + file.getOriginalFilename().replaceAll(" ", "") + "'");

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

	@RequestMapping("/*/forms")
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
	public String index(Model model) {
		log.info("some log");
		model.addAttribute("rooms", roomRepo.findAll());
		model.addAttribute("faqList", faqRepo.findAll());
		return "index";
	}

	@RequestMapping("/like")
	public String indexFaq(@RequestParam long id) {
		Faq faq = faqRepo.findOne(id);
		faq.setRate(faq.getRate() + 1);
		faqRepo.save(faq);

		return "redirect:/";
	}

	@RequestMapping("/user/rooms")
	public String list(ModelMap model) {
		User user = userService.findByUserName(currentUser());

		// for(Room r : roomRepo.findAllByOwnerId(user.getId())) {
		// log.info(r.getImage().getPath());
		// }
		model.addAttribute("rooms", roomRepo.findAllByOwnerId(user.getId()));
		return "roomList";
	}

	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

	@GetMapping("/us")
	@ResponseBody
	public String list() {
		List<User> list = userRepo.findAll();
		Gson gson = new Gson();

		String result = gson.toJson(list);

		return result;

	}


}
