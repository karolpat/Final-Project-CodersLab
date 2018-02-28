package pl.coderslab.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.service.DateService;
import pl.coderslab.service.LocalizationService;
import pl.coderslab.service.RoomService;
import pl.coderslab.service.UserService;
import pl.coderslab.util.Currency;

@Controller
public class BookingController {


	private UserService userService;
	private LocalizationService localizationService;
	private RoomService roomService;
	private DateService dateService;

	public BookingController(UserService userService, DateService dateService, RoomService roomService,
			LocalizationService localizationService) {
		this.userService = userService;
		this.localizationService = localizationService;
		this.roomService = roomService;
		this.dateService = dateService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	/**
	 * Provide the list of cities to thymeleaf room booking form.
	 * 
	 * @param model
	 * @return view of room booking form.
	 */
	@GetMapping("/book")
	public String showForm(Model model) {

		List<String> cities = localizationService.findAllCities();
		model.addAttribute("cities", cities);
		return "/book/bookForm";
	}

	/**
	 * Provide list of rooms that are available and fulfill conditions given by the
	 * user. First checks whether selected dates are correct. Start date must not be
	 * before end date and before current date.
	 * 
	 * @param capacity
	 *            - number of people.
	 * @param city
	 *            - city of the room.
	 * @param from
	 *            - start date.
	 * @param to
	 *            - end date.
	 * @param model
	 * @return view with rooms to be booked.
	 */
	@PostMapping("/book/search")
	public String checkParamsAndSearch(@RequestParam("capacity") int capacity, @RequestParam("city") String city,
			@RequestParam("from") String from, @RequestParam("to") String to, Model model) {

		if (dateService.checkDates(from, to) == false) {
			model.addAttribute("info", "Please select correct dates");
			return "/book/bookForm";
		}
		List<Room> toShow = roomService.availableSearchedRooms(city, capacity, from, to);

		model.addAttribute("city", city);
		model.addAttribute("from", dateService.startDate(from));
		model.addAttribute("to", dateService.startDate(to));
		model.addAttribute("capacity", capacity);
		model.addAttribute("rooms", toShow);

		return "/book/search";
	}

	/**
	 * Confirmation page where user can check the booking room again and price in 3
	 * currencies: USD, EUR and GBP.
	 * 
	 * @param id
	 *            - id of the room to be shown.
	 * @param from
	 *            - start date of the reservation.
	 * @param to
	 *            - end date of the reservation.
	 * @param model
	 * @return view with confirmation page.
	 * @throws Exception
	 */
	@GetMapping("/book/confirm/{id}/{from}/{to}")
	public String confirmBooking(@PathVariable("id") long id, @PathVariable("from") String from,
			@PathVariable("to") String to, Model model) throws Exception {

		Currency currency = new Currency();
		List<Double> currencies = currency.getCurrency();

		Room room = roomService.findOne(id);
		double price = room.getPrice();
		double eur = (price * currencies.get(0)) / currencies.get(1);
		double gbp = (price * currencies.get(0)) / currencies.get(2);

		model.addAttribute("eur", eur);
		model.addAttribute("gbp", gbp);
		model.addAttribute("price", price);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("room", room);

		return "/book/confirmation";
	}

	/**
	 * When the room is confirmed by user it is booked. New date is saved in
	 * reference to room by given id. Room's list of hosts is updated by new user
	 * who booked the room. User's list of rooms as host is updated by new room
	 * which booked.
	 * 
	 * @param id
	 *            - id of the room to be booked.
	 * @param from
	 *            - start date.
	 * @param to
	 *            - end date.
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping("/book/confirm/{id}/{from}/{to}")
	public String booking(@PathVariable("id") long id, @PathVariable("from") String from, @PathVariable("to") String to,
			RedirectAttributes redirectAttributes) {

		Room room = roomService.findOne(id);
		User user = userService.findByUserName(currentUser());
		if (user != null) {

			if (user.isEnabled() == true) {
				dateService.bookRoom(from, to, id, user);

				List<User> hosts = room.getHost();
				hosts.add(user);
				roomService.save(room);

				List<Room> asHost = user.getRoomsAsHost();
				asHost.add(room);
				userService.save(user);

				return "redirect:/user/profile";
			} else {
				redirectAttributes.addFlashAttribute("info", "You have no permission to book a room.");
				return "redirect:/";
			}
		} else {
			redirectAttributes.addFlashAttribute("info", "You have no permission to book a room. Log in");
			return "redirect:/";
		}
	}
}
