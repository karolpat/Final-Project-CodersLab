package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Chat;
import pl.coderslab.repo.ChatRepo;

@Service
public class ChatService {

	@Autowired
	private ChatRepo chatRepo;
	
	public ChatService(ChatRepo chatRepo) {
		this.chatRepo=chatRepo;
	}
	
	public List<Chat> findAllByUserId(long id){
		return chatRepo.findAllByUserId(id);
	}
	
	public Chat findOne(long id) {
		return chatRepo.findOne(id);
	}
	
	public void save(Chat chat) {
		chatRepo.save(chat);
	}
	
	public Chat saveAndFlush(Chat chat) {
		return chatRepo.saveAndFlush(chat);
	}
}
