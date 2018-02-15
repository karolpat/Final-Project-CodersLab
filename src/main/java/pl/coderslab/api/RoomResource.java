package pl.coderslab.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.coderslab.service.RoomService;
import pl.coderslab.web.HomeController;

@RestController
@RequestMapping("/rooms")
public class RoomResource {

private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private RoomService roomService;

	public RoomResource(RoomService roomService) {
		this.roomService = roomService;
	}
	
	@GetMapping
	ResponseEntity getRooms() {
		return ResponseEntity.ok(roomService.findAll());
	}
	
	@GetMapping("/{id}")
	ResponseEntity getRooms(@PathVariable("id") long id) {
		return ResponseEntity.ok(roomService.findOne(id));
	}
	
	
}
