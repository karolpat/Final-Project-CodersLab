package pl.coderslab.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.coderslab.entity.User;

@Controller
public class LoginController {

	private final String LOGIN_TEMPLATE = "/login";
	
	@RequestMapping(value= {"/login"}, method = RequestMethod.GET)
	public String loginForm() {
		
//		User u = new User();
//		model.addAttribute("user", u);
//		
		return LOGIN_TEMPLATE;
	}
	
//	@RequestMapping(value="/login", method = RequestMethod.POST)
//	public String login() {
//		
//		return "redirect: index";
//	}
}
