package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pl.coderslab.entity.Localization;

public interface LocalizationRepo extends JpaRepository<Localization, Long> {


	/** Gives list of cities where rooms are located without repetitions.
	 * @return
	 */
	@Query(value="select distinct city from localization", nativeQuery=true)
	List<String> findAllCities();
}
