package pl.coderslab.web;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Hotel;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.service.HotelService;
import pl.coderslab.service.ImageService;
import pl.coderslab.service.LocalizationService;
import pl.coderslab.service.RoomService;
import pl.coderslab.service.UserService;

/**
 * @author karolpat
 *
 */
@Controller
public class ManagerController {

	private UserService userService;
	private HotelService hotelService;
	private ImageService imageService;
	private LocalizationService localizationService;
	private RoomService roomService;



	public ManagerController(UserService userServ, HotelService hotelService, ImageService imageService,
			LocalizationService localizationService, RoomService roomService) {
		this.userService = userServ;
		this.hotelService = hotelService;
		this.imageService = imageService;
		this.localizationService = localizationService;
		this.roomService=roomService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/**
	 * Gives all hotels of current User (Manager Role)
	 * 
	 * @param model
	 * @return view with table of all user's hotels.
	 */
	@GetMapping("/manager/hotels")
	public String showHotels(Model model) {

		User user = userService.findByUserName(currentUser());
		model.addAttribute("hotels", hotelService.findAllByHOwnerId(user.getId()));
		return "/manager/hotels";
	}

	/**
	 * Shows form to add new hotel by user (Manager Role)
	 * 
	 * @param username
	 *            - username of the current user.
	 * @param model
	 * @return view with form to add new hotel.
	 */
	@GetMapping("/manager/hotel/add/{username}")
	public String hotelForm(@PathVariable("username") String username, Model model) {

		Hotel hotel = new Hotel();
		Localization localization = new Localization();

		model.addAttribute("hotel", hotel);
		model.addAttribute("local", localization);

		return "/manager/hotelForm";
	}

	/**
	 * Adding new hotel to current user (Manager Role)
	 * 
	 * @param username
	 *            - username of current user.
	 * @param model
	 * @param redirectAttributes
	 * @param hotel
	 *            - hotel that is being added.
	 * @param localization
	 *            - localization of the hotel
	 * @param bresult
	 * @param file
	 *            - image of the hotel that is uploaded.
	 * @return
	 */
	@PostMapping("/manager/hotel/add/{username}")
	public String addHotel(@PathVariable("username") String username, Model model,
			RedirectAttributes redirectAttributes, Hotel hotel, Localization localization, BindingResult bresult,
			@RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/manager/hotelForm";
		}

		try {
			Image image = imageService.addNewImage(file);
			Localization hotelLocalization = localizationService.newLocalization(localization);
			User user = userService.findByUserName(currentUser());
			hotelService.addHotel(hotel, image, hotelLocalization, user);
			localizationService.hotelLocalization(hotelLocalization, hotel);
			redirectAttributes.addFlashAttribute("info", "Hotel has been added.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/manager/hotels";
	}

	/**
	 * Shows details of the hotel by given id
	 * 
	 * @param id
	 *            - id of the hotel to be shown
	 * @param model
	 * @return
	 */
	@GetMapping("/manager/hotel/{id}")
	public String showHotel(@PathVariable("id") long id, Model model) {

		List<Room> rList = roomService.findAllByHotelId(id);
		Hotel hotel = hotelService.findById(id);

		model.addAttribute("hotel", hotel);
		model.addAttribute("rList", rList);

		return "/manager/showHotel";
	}

	/**
	 * Shows details of the room by given id
	 * 
	 * @param id
	 *            - id of the room to be shown
	 * @param model
	 * @return
	 */
	@GetMapping("/manager/room/add/{id}")
	public String addRoomHotelForm(@PathVariable("id") long id, Model model) {

		Hotel hotel = hotelService.findById(id);
		Room room = new Room();

		model.addAttribute("hotel", hotel);
		model.addAttribute("room", room);
		return "/manager/addRoom";
	}

	/** Adds new room to the hotel.
	 * @param id - id of the hotel where the room is added.
	 * @param model
	 * @param room - room that is being added to the hotel.
	 * @param bresult
	 * @param redirectAttributes
	 * @param file - image of the room to be added.
	 * @return
	 */
	@PostMapping("/manager/room/add/{id}")
	public String addRoomHotel(@PathVariable("id") long id, Model model, Room room, BindingResult bresult,
			RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile file) {

		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:/manager/hotelForm";
		}
		try {
			Image image = imageService.addNewImage(file);
			User user = userService.findByUserName(currentUser());
			Hotel hotel = hotelService.findById(id);
			roomService.addNewHotelRoom(hotel, image, user);
			
			redirectAttributes.addFlashAttribute("info", "Hotel has been added.");
		} catch (IOException e) {
			e.printStackTrace();
		}

			return "/manager/room/add/" + id;
	}

	/** Shows details of the room by given id.
	 * @param id - id of the room to be shown
	 * @param model
	 * @return
	 */
	@GetMapping("/manager/show/room/{id}")
	public String showRoom(@PathVariable("id") long id, Model model) {

		Room room = roomService.findOne(id);

		model.addAttribute("room", room);

		return "/manager/showRoom";
	}

	@ModelAttribute
	public void userModer(Model model) {
		User user = userService.findByUserName(currentUser());
		model.addAttribute("currUser", user);
	}

}
