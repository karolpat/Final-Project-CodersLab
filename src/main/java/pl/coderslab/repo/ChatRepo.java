package pl.coderslab.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Message;

public interface ChatRepo extends JpaRepository<Chat, Long> {

	
	Chat findByMessageSendToIdAndMessageSendFromId(long idTo, long idFrom);
	
	List<Chat> findAllByUserId(long id);
	
	
}
