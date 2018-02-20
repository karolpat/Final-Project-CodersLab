package pl.coderslab.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	private final String LOGIN_TEMPLATE = "/login";

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String loginForm() {
		return LOGIN_TEMPLATE;
	}
}
