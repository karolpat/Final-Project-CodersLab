package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Localization;

public interface LocalizationRepo extends JpaRepository<Localization, Long> {

	
//	Localization findByUserId(long id);
}
