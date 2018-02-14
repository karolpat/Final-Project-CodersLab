package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Hotel;

public interface HotelRepo extends JpaRepository<Hotel, Long> {

	
	List<Hotel> findAllByHOwnerId(long id);
}
