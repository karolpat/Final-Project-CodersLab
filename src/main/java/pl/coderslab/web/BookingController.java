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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Room;
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

	@PostMapping("/book")
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

		List<Room> toShow = new ArrayList<>();
		for (Room r : byLocal) {

			LocalDate[] starts = dateRepo.findAllStart(r.getId());
			LocalDate[] ends = dateRepo.findAllEnd(r.getId());

			if (starts == null && ends == null) {
				toShow.add(r);
				
			}else if(starts != null && ends != null) {
				for(int i=0; i<starts.length; i++) {
					if(startDate.isBefore(starts[i]) && endDate.isBefore(starts[i])) {
						if(startDate.isAfter(ends[i-1])) {
							r.setAvailability(true);
							toShow.add(r);
						}else {
							r.setAvailability(false);
						}
					}else {
						r.setAvailability(false);
					}
				}
			}
		}
		model.addAttribute("rooms", toShow);

		return "index";

	}
}
