package pl.coderslab.repo;


import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Image;

public interface ImageRepo extends JpaRepository<Image, Long>{
	
	Image findOneByUserId(long id);
	
	Image findOneById(long id);

}
