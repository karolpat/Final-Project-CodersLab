package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Request;

public interface RequestRepo extends JpaRepository<Request, Long> {
	
	List<Request> findAllByChecked(boolean check);

}
