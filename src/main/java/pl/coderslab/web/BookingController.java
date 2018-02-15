package pl.coderslab.web;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pl.coderslab.entity.Date;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.ChatRepo;
import pl.coderslab.repo.DateRepo;
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
public class BookingController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

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
	private ChatRepo chatRepo;

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private DateRepo dateRepo;

	public BookingController(UserService userService) {
		this.userService = userService;
	}

	public String currentUser() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}

	@GetMapping("/book")
	public String showForm(Model model) {

		List<String> cities = localRepo.findAllCities();
		List<String> tmp = new ArrayList<>();
		for (String c : cities) {
			if (!tmp.contains(c)) {
				tmp.add(c);
			}
		}

		model.addAttribute("cities", tmp);
		// model.addAttribute("start", dateRepo.findAllStart());
		// model.addAttribute("end", dateRepo.findAllEnd());

		return "/book/bookForm";
	}

	@PostMapping("/book/search")
	public String test(@RequestParam("capacity") int capacity, @RequestParam("city") String city,
			@RequestParam("from") String from, @RequestParam("to") String to, Model model) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate startDate = dtf.parseLocalDate(from);
		LocalDate endDate = dtf.parseLocalDate(to);

		if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
			model.addAttribute("info", "Please select correct dates");
			return "/book/bookForm";
		}

		List<Room> byLocal = roomRepo.findAllByLocalizationCity(city);
		List<Room> byHotel = roomRepo.findAllByHotelAddressCity(city);
		byLocal.addAll(byHotel);
		List<Room> tmp = new ArrayList<>();
		for(Room r:byLocal) {
			if(!tmp.contains(r) && r.getCapacity()>=capacity) {
				tmp.add(r);
			}
		}

		List<Room> toShow = new ArrayList<>();
		for (Room r : tmp) {
			
			Date[] dates = dateRepo.findAllByRoomId(r.getId());

			if (dates.length==0) {
				toShow.add(r);
				
			}else if(dates.length > 0) {
				for(int i=0; i<dates.length; i++) {
					if(startDate.isAfter(dates[i].getEnd()) && i==dates.length-1)  {
						log.info("if1");
							toShow.add(r);
						}else if(startDate.isAfter(dates[i].getEnd()) && endDate.isBefore(dates[i+1].getStart())){
							toShow.add(r);
						}
					}
				}
			
		}
		model.addAttribute("city", city);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("capacity", capacity);
		model.addAttribute("rooms", toShow);

		return "/book/search";

	}
	
	@GetMapping("/book/confirm/{id}/{from}/{to}")
	public String confirmBooking(@PathVariable("id") long id, @PathVariable("from") String from, @PathVariable("to") String to, Model model) throws Exception {
		
		Currency currency = new Currency();
		List<Double> currencies = currency.getCurrency();
		
		Room room = roomRepo.findOne(id);
		double price = room.getPrice();
		double eur = (price*currencies.get(0))/currencies.get(1);
		double gbp = (price*currencies.get(0))/currencies.get(2);
		
		model.addAttribute("eur", eur);
		model.addAttribute("gbp", gbp);
		model.addAttribute("price", price);
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		model.addAttribute("room", room);
		
		
		return "/book/confirmation";
	}
	
	@PostMapping("/book/confirm/{id}/{from}/{to}")
	public String booking(@PathVariable("id") long id, @PathVariable("from") String from, @PathVariable("to") String to, RedirectAttributes redirectAttributes) {
		
		
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		LocalDate startDate = dtf.parseLocalDate(from);
		LocalDate endDate = dtf.parseLocalDate(to);
		
		Room room = roomRepo.findOne(id);
		Date date = new Date();
		User user = userService.findByUserName(currentUser());
		
		if(user.isEnabled()==true) {
			date.setStart(startDate);
			date.setEnd(endDate);
			date.setRoom(roomRepo.findOne(id));
			dateRepo.save(date);
			
			List<User> hosts = room.getHost();
			hosts.add(user);
			roomRepo.save(room);
			
			List<Room> asHost = user.getRoomsAsHost();
			asHost.add(room);
			userRepo.save(user);
			
			log.info("success");
			
			return "redirect:/user/profile";
		}else {
			redirectAttributes.addFlashAttribute("info", "You have no permission to book a room.");
			return "redirect:/";
		}
		
		
	}
}
