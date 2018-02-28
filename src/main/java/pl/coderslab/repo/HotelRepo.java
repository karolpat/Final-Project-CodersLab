package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, Long> {

	
	/** Gives list of hotels that belong to user.
	 * @param id - id of user who is owner of the hotels.
	 * @return
	 */
	List<Hotel> findAllByHOwnerId(long id);
}
