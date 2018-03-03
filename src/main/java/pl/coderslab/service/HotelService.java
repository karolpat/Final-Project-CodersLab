package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Hotel;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.User;
import pl.coderslab.repo.HotelRepo;

@Service
public class HotelService {

	@Autowired
	private HotelRepo hotelRepo;
	
	public HotelService(HotelRepo hotelRepo) {
		this.hotelRepo=hotelRepo;
	}
	
	public List<Hotel> findAllByHOwnerId(long id){
		return hotelRepo.findAllByHOwnerId(id);
	}
	
	public Hotel findById(long id) {
		return hotelRepo.findOne(id);
	}
	
	public void addHotel(Hotel hotel, Image image, Localization hotelLocalization, User user) {
		hotel.sethOwner(user);
		hotel.setImages(image);
		hotel.setAddress(hotelLocalization);
		hotelRepo.save(hotel);
	}
}
