package pl.coderslab.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Date;
import pl.coderslab.entity.Hotel;
import pl.coderslab.entity.Image;
import pl.coderslab.entity.Localization;
import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RoomRepo;

/**
 * @author karolpat
 *
 */
@Service
public class RoomService {
	
	private DateService dateService;

	@Autowired
	private RoomRepo roomRepo;
	
	public RoomService(DateService dateService) {
		this.dateService=dateService;
	}

	public List<Room> findAllByOwnerId(long id) {
		return roomRepo.findAllByOwnerId(id);
	}

	public List<Room> findAllByHotelId(long id) {
		return roomRepo.findAllByHotelId(id);
	}

	public List<Room> findAllByLocalizationCity(String city) {
		return roomRepo.findAllByLocalizationCity(city);
	}

	public List<Room> findAllByHotelAddressCity(String city) {
		return roomRepo.findAllByHotelAddressCity(city);
	}

	public List<Room> findAll() {
		return roomRepo.findAll();
	}

	public Room findOne(long id) {
		return roomRepo.findOne(id);
	}

	public List<Room> findAllByHost(long id) {
		return roomRepo.findAllHost(id);
	}
	
	public void save(Room room) {
		roomRepo.save(room);
	}

	/** Adds new room with given parameters.
	 * @param image - image entity.
	 * @param user - owner of the room, user who added the room.
	 * @param localization - localization entity of the room.
	 * @param room - binded room to be saved.
	 */
	public void addNewRoom(Image image, User user, Localization localization, Room room) {

		room.setOwner(user);
		room.setAdded(LocalDate.now());
		room.setImage(image);
		room.setLocalization(localization);
		roomRepo.save(room);
	}

	/** Gives list of rooms found by city and with capacity more or equal given.
	 * @param city - name of city given by user through select form.
	 * @param capacity - number of host in the room.
	 * @return list with cities.
	 */
	public List<Room> roomsByCityAndCapacity(String city, int capacity) {
		List<Room> byLocal = roomRepo.findAllByLocalizationCity(city);
		List<Room> byHotel = roomRepo.findAllByHotelAddressCity(city);
		byLocal.addAll(byHotel);
		List<Room> tmp = new ArrayList<>();
		for (Room r : byLocal) {
			//check if exact city is already in the list and the capacity of the room.
			if (!tmp.contains(r) && r.getCapacity() >= capacity) {
				tmp.add(r);
			}
		}
		return tmp;
	}
	
	/** Gives list of rooms that are available in exact period of time, with given capacity and located in city.
	 * @param city - city of room location.
	 * @param capacity - capacity of the room.
	 * @param from - start date.
	 * @param to - end date.
	 * @return list of rooms to show on page.
	 */
	public List<Room> availableSearchedRooms(String city, int capacity, String from, String to){
		
		List<Room> toShow = new ArrayList<>();
		for (Room r : roomsByCityAndCapacity(city, capacity)) {

			Date[] dates = dateService.findAllByRoomId(r.getId());

			if (dates.length == 0) {
				toShow.add(r);

			} else if (dates.length > 0) {
				for (int i = 0; i < dates.length; i++) {
					if (dateService.startDate(from).isAfter(dates[i].getEnd()) && i == dates.length - 1) {
						toShow.add(r);
					} else if (dateService.startDate(from).isAfter(dates[i].getEnd()) && dateService.endDate(to).isBefore(dates[i + 1].getStart())) {
						toShow.add(r);
					}
				}
			}

		}
		return toShow;
	}

	/** Adds new room to given hotel, sets given image and current user as an owner.
	 * @param hotel - hotel where the room is added
	 * @param image - image of the room to be added.
	 * @param user - user who is the owner of the room.
	 */
	public void addNewHotelRoom(Hotel hotel, Image image, User user) {
		Room room = new Room();
		room.setId(0);
		room.setLocalization(hotel.getAddress());
		room.setHotel(hotel);
		room.setAdded(LocalDate.now());
		room.setImage(image);
		roomRepo.save(room);
	}
}
