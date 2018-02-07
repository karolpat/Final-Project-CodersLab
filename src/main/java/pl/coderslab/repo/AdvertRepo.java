package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Advert;

public interface AdvertRepo extends JpaRepository<Advert, Long> {

	List<Advert> findAllByOwnerUsername(String username);
	
	List<Advert> findAllByHostUsername(String username);
	
	List<Advert> findTop5WherePromoted(int promoted);
	
	
}
