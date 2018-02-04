package pl.coderslab.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.entity.Faq;
import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;
import pl.coderslab.repo.FaqRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.StudentRepo;
import pl.coderslab.service.UserService;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	private UserService userService;
	
	@Autowired
	private StudentRepo stud;
	
	@Autowired
	private FaqRepo faqRepo;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public HomeController(UserService userService) {
		this.userService=userService;
	}
	
	@GetMapping("/register")
	public String registerForm(Model model) {
		
		User u = new User();
		model.addAttribute("user", u);
		
		model.addAttribute("roleList", roleRepo.findAll());
		return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(@Valid User user, BindingResult bresult, Model model) {
		if (bresult.hasErrors()) {
			log.info("błąd"+user.toString());
			model.addAttribute(user);
			return "/register";

		} else {
			log.info(user.toString());
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
		model.addAttribute("faqList",faqRepo.findAll());
		return "index";
	}
	
	@RequestMapping("/like")
	public String indexFaq(@RequestParam long id) {
		Faq faq = faqRepo.findOne(id);
		faq.setRate(faq.getRate()+1);
		faqRepo.save(faq);
		
		return "redirect:/";
	}

	
	@RequestMapping("/forms")
	public String forms() {
		return "forms";
	}
	
	
	
	@RequestMapping("/students")
	public String list(ModelMap model, @SortDefault("firstName") Pageable pageable){
		model.addAttribute("studs", stud.findAll(pageable));
		
		return "list";
	}
}
