package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Faq;

public interface FaqRepo extends JpaRepository<Faq, Long> {
	
	
}
