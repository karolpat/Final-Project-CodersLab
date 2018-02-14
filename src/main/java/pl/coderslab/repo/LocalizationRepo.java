package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Localization;

public interface LocalizationRepo extends JpaRepository<Localization, Long> {

	
//	Localization findByUserId(long id);
//	@Query(value="select city from localization", nativeQuery=true)
//	List<String> findAllCities();
	
	@Query(value="select city from localization where hotel_id is not null or room_id is not null", nativeQuery=true)
	List<String> findAllCities();
}
