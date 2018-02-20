package pl.coderslab.api;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver;

import pl.coderslab.entity.User;
import pl.coderslab.repo.RequestRepo;
import pl.coderslab.service.UserService;
import pl.coderslab.web.HomeController;

@RestController
@RequestMapping("/users")
public class UserResource {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	private UserService userService;
	private RequestRepo reqRepo;

	public UserResource(UserService userService, RequestRepo reqRepo) {
		this.userService = userService;
		this.reqRepo=reqRepo;
	}

	@GetMapping
	ResponseEntity getUsers() {
		return ResponseEntity.ok(userService.getUsers());
	}
	
	@GetMapping("/{id}")
	ResponseEntity getUser(@PathVariable("id") long id) {
		return ResponseEntity.ok(userService.getUser(id));
	}
	
	@PostMapping("/add")
	ResponseEntity createUser(@RequestBody User user) {
		user.setCreated(LocalDate.now());
		userService.saveUser(user);
		return ResponseEntity.accepted().build();
	}
	
	@PutMapping("/edit/{id}")
	ResponseEntity editUser(@RequestBody User user, @PathVariable("id") long id) {
		userService.updateUser(user,id);
		return ResponseEntity.accepted().build();
	}
	
	@DeleteMapping("/delete/{id}")
	ResponseEntity deleteUser(@PathVariable("id") long id) {
		
		userService.deleteUser(id);
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping("/admin/requests")
	@Secured("ROLE_ADMIN")
	ResponseEntity showRequests() {
		return ResponseEntity.ok(reqRepo.findAllByChecked(false));
	}
	
//	@GetMapping("/login")
//	ResponseEntity login()
	
	
//	@PostMapping("/request")
//	ResponseEntity createRequest(@RequestParam) {
//		
//	}

}
