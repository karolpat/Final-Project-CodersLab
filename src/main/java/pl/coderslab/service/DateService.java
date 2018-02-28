package pl.coderslab.service;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Date;
import pl.coderslab.entity.User;
import pl.coderslab.repo.DateRepo;
import pl.coderslab.repo.RoomRepo;
import pl.coderslab.web.HomeController;

/**
 * @author karolpat
 *
 */
@Service
public class DateService {

	private RoomRepo roomRepo;
	
	@Autowired
	private DateRepo dateRepo;
	
	public DateService(RoomRepo roomRepo) {
		this.roomRepo=roomRepo;
	}

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Check whether dates given as a String are correct. End date must be after the
	 * start date and start date must not be before today.
	 * 
	 * @param from
	 *            - String of start date.
	 * @param to
	 *            - String of end date.
	 * @return true or false depending on compared dates.
	 */
	public boolean checkDates(String from, String to) {
		if (startDate(from).isAfter(endDate(to)) || startDate(from).isBefore(LocalDate.now())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gives start date by String from thymeleaf form
	 * 
	 * @param from
	 *            - String to be parsed to LocalDate
	 * @return LocalDate of start date.
	 */
	public LocalDate startDate(String from) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtf.parseLocalDate(from);
	}

	/**
	 * Gives end date by String from thymeleaf form
	 * 
	 * @param from
	 *            - String to be parsed to LocalDate
	 * @return LocalDate of end date.
	 */
	public LocalDate endDate(String to) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtf.parseLocalDate(to);
	}

	/**
	 * Find all dates by given room id.
	 * 
	 * @param id
	 *            - id of given room to show all dates.
	 * @return - an array of all dates for exact room.
	 */
	public Date[] findAllByRoomId(long id) {
		return dateRepo.findAllByRoomId(id);
	}
	
	/** Books room, sets start and end dates and saves to room.
	 * @param from - start date.
	 * @param to - end date.
	 * @param id - id of the room to be booked.
	 */
	public void bookRoom(String from, String to, long id, User user) {
		Date date = new Date();
		date.setStart(startDate(from));
		date.setEnd(endDate(to));
		date.setRoom(roomRepo.findOne(id));
		date.setHost(user);
		dateRepo.save(date);
	}
	
	/** Gives Date for room where host is given by id/
	 * @param room - id of the room.
	 * @param host - id of user who is a host in the room.
	 * @return
	 */
	public Date findyByRoomAndHost(long room, long host) {
		return dateRepo.findByRoomIdAndHostId(room, host);
	}

}
