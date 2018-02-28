package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Date;

public interface DateRepo extends JpaRepository<Date, Long> {
	
	/** Gives an array of all start dates for room by given id.
	 * @param id - id of the room for which array of start dates will be returned.
	 * @return
	 */
	@Query(value="select start from date where room_id=?", nativeQuery=true)
	Date[] findAllStart(long id);
	
	/** Gives an array of all end dates for room by given id.
	 * @param id - id of the room for which array of end dates will be returned.
	 * @return
	 */
	@Query(value="select end from date where room_id=?", nativeQuery=true)
	Date[] findAllEnd(long id);

	/**Gives an array for all dates for room by given id.
	 * @param id - id of the room for which array of dates will be returned.
	 * @return
	 */
	Date[] findAllByRoomId(long id);
	
	/** Gives Date for room where host is given by id/
	 * @param room - id of the room.
	 * @param host - id of user who is a host in the room.
	 * @return
	 */
	@Query(value="select * from date where room_id= ? and host_id= ?", nativeQuery=true)
	Date findByRoomIdAndHostId(long room, long host);
	
}
