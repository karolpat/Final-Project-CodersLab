package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Room;
import pl.coderslab.entity.User;

public interface RoomRepo extends JpaRepository<Room, Long> {

	
	List<Room> findAllByOwnerId(long id);
	
	List<Room>	findAllByHotelId(long id);
	
	List<Room> findAllByLocalizationCity(String city);
	
	List<Room> findAllByHotelAddressCity(String city);
	
	/** Gives list of rooms for which user by given id is a host.
	 * @param id - id of user who is host in rooms returned in the list.
	 * @return
	 */
	@Query(value="select * from room where host.id= ?", nativeQuery=true)
	List<Room> findAllHost(long id);
	
}
