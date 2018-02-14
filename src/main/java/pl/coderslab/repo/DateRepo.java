package pl.coderslab.repo;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Date;

public interface DateRepo extends JpaRepository<Date, Long> {
	
	@Query(value="select start from date where room_id=?", nativeQuery=true)
	LocalDate[] findAllStart(long id);
	
	@Query(value="select end from date where room_id=?", nativeQuery=true)
	LocalDate[] findAllEnd(long id);

}
