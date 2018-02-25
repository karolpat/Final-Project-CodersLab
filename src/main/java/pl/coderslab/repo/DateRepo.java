package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Date;

public interface DateRepo extends JpaRepository<Date, Long> {
	
	@Query(value="select start from date where room_id=?", nativeQuery=true)
	Date[] findAllStart(long id);
	
	@Query(value="select end from date where room_id=?", nativeQuery=true)
	Date[] findAllEnd(long id);

	Date[] findAllByRoomId(long id);
	
	@Query(value="select * from date where room_id= ? and host_id= ?", nativeQuery=true)
	Date findByRoomIdAndHostId(long room, long host);
	
}
