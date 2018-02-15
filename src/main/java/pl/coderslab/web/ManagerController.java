package pl.coderslab.web;

import java.util.List;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Hotel;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.HotelRepo;
import pl.coderslab.repo.ImageRepo;
import pl.coderslab.repo.LocalizationRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;

@Controller
public class ManagerController {

	private UserService userService;

	@Autowired
	private HotelRepo hotelRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private LocalizationRepo localizationRepo;
	
	@Autowired
	private RoomRepo roomRepo;
	
	@Autowired
	private ImageRepo imageRepo;
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	public ManagerController(UserService userServ) {
		this.userService = userServ;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@GetMapping("/manager/hotels")
	public String showHotels(Model model) {

		User user = userService.findByUserName(currentUser());
		model.addAttribute("hotels", hotelRepo.findAllByHOwnerId(user.getId()));
		return "/manager/hotels";
	}

	@GetMapping("/manager/hotel/add/{username}")
	public String hotelForm(@PathVariable("username") String username, Model model) {
			
		Hotel hotel = new Hotel();
		Localization localization = new Localization();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("local", localization);
		
		return "/manager/hotelForm";
	}
	
	@PostMapping("/manager/hotel/add/{username}")
	public String addHotel(@PathVariable("username") String username, Model model,RedirectAttributes redirectAttributes, Hotel hotel, Localization localization, BindingResult bresult) {
		
		User user = userRepo.findByUsername(currentUser());
		
		if(!bresult.hasErrors()) {
			hotel.sethOwner(user);
			localizationRepo.saveAndFlush(localization);
			Image image = new Image();
			image.setPath("../storage/hotel.png");
			imageRepo.saveAndFlush(image);
			hotel.setImages(image);
			hotel.setAddress(localization);
			hotelRepo.save(hotel);
			localization.setHotel(hotel);
			localizationRepo.save(localization);
			
			redirectAttributes.addFlashAttribute("info", "Hotel has been added.");
		}else {
			
			log.info("lipa");
			model.addAttribute("info", "There is some errors");
			return "/manager/hotelForm";
		}
		
		
		return "redirect:/manager/hotels";
	}
	
	@GetMapping("/manager/hotel/{id}")
	public String showHotel(@PathVariable("id") long id, Model model) {
		
		List<Room> rList = roomRepo.findAllByHotelId(id);
		Hotel hotel = hotelRepo.findOne(id);
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("rList", rList);
		
		return "/manager/showHotel";
	}
	
	@GetMapping("/manager/room/add/{id}")
	public String addRoomHotelForm(@PathVariable("id") long id, Model model) {
		
		Hotel hotel = hotelRepo.findOne(id);
		Room room = new Room();
		
		model.addAttribute("hotel", hotel);
		model.addAttribute("room", room);
		return "/manager/addRoom";
	}
	
	@PostMapping("/manager/room/add/{id}")
	public String addRoomHotel(@PathVariable("id") long id, Model model, Room room, BindingResult bresult) {
		
		Hotel hotel = hotelRepo.findOne(id);
		Room r = room;
		Image image = new Image();
		image.setPath("../storage/room.png");
		imageRepo.saveAndFlush(image);
		
		if(!bresult.hasErrors()) {
			r.setId(0);
			r.setLocalization(hotel.getAddress());
			r.setHotel(hotel);
			r.setAdded(LocalDate.now());
			r.setImage(image);
			log.info("git");
			roomRepo.save(r);			
			return "redirect:/manager/hotel/"+id;
		}else {
			log.info("lipa");
			return "/manager/room/add/"+id;
		}
	}
	
	@GetMapping("/manager/show/room/{id}")
	public String showRoom(@PathVariable("id") long id, Model model) {
		
		Room room = roomRepo.findOne(id);
		
		model.addAttribute("room", room);
		
		return "/manager/showRoom";
	}
	
	
	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
