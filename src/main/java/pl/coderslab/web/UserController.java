package pl.coderslab.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.coderslab.entity.Request;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RequestRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;
@Controller
public class UserController {
	
	private final String GET_OWNER="I want to become an owner.";
	
	private UserService userService;

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RequestRepo reqRepo;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/user/profile/requests/{username}")
	public String ownerReq(@PathVariable("username") String username, Model model) {
		
		User user = userService.findByUserName(username);
		Request req = new Request();
		
		req.setDescription(GET_OWNER);
		req.setUser(user);
		user.setOwnerReq(true);
		
		model.addAttribute("message", "Wait until admin accept your response");
		
		reqRepo.save(req);
		userRepo.save(user);
		
		return "redirect:/user/profile";
	}
}
