package pl.coderslab.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.coderslab.entity.Chat;

public interface ChatRepo extends JpaRepository<Chat, Long> {

	
	Chat findByMessageSendToIdAndMessageSendFromId(long idTo, long idFrom);
}
