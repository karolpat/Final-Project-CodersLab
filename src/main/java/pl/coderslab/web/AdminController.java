package pl.coderslab.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.coderslab.entity.Request;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RequestRepo;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.UserRepo;
import pl.coderslab.service.UserService;

@Controller
public class AdminController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RequestRepo reqRepo;
	
	@Autowired
	private UserService userServ;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@RequestMapping("/admin/requests")
	public String showRequests(Model model) {
		
		List<Request> reqList = reqRepo.findAllByChecked(false);
		model.addAttribute("rList", reqList);
		
		return "/admin/requests";
	}
	
	@PostMapping("/admin/request/owner/{id}")
	public String acceptReq(@PathVariable("id") long id) {
		
		Request req = reqRepo.findOne(id);
		log.info("id" + req.getId());
		
		req.setChecked(true);
		User user = userRepo.findOne(req.getUser().getId());
		user.setRole(roleRepo.findBySubName("Owner"));
		
		userRepo.save(user);
		reqRepo.save(req);
		
		return "redirect:/admin/requests";
	}
	
}
