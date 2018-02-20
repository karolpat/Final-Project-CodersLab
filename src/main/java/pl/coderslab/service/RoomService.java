package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Room;
import pl.coderslab.repo.RoomRepo;

@Service
public class RoomService {

	@Autowired
	private RoomRepo roomRepo;

	public List<Room> findAllByOwnerId(long id){
		return roomRepo.findAllByOwnerId(id);
	}

	public List<Room> findAllByHotelId(long id){
		return roomRepo.findAllByHotelId(id);
	}

	public List<Room> findAllByLocalizationCity(String city){
		return roomRepo.findAllByLocalizationCity(city);
	}

	public List<Room> findAllByHotelAddressCity(String city){
		return roomRepo.findAllByHotelAddressCity(city);
	}
	
	public List<Room> findAll(){
		return roomRepo.findAll();
	}
	
	public Room findOne(long id) {
		return roomRepo.findOne(id);
	}
	
	public List<Room> findAllByHost(long id){
		return roomRepo.findAllHost(id);
	}

}
