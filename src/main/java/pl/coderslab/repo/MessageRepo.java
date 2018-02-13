package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {

	List<Message> findAllByChatId(long id);
}
