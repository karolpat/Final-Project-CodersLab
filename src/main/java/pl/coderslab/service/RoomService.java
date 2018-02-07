package pl.coderslab.service;

import java.util.List;

import pl.coderslab.entity.Room;

public interface RoomService {
	
	List<Room> showAllPromoted();

	List<Room> findAllByOwnerUsername(String username);

	List<Room> findAllByHostUsername(String username);

}
