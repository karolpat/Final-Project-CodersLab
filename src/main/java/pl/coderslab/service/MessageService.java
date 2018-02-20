package pl.coderslab.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Message;
import pl.coderslab.repo.MessageRepo;

@Service
public class MessageService {
	
	private UserService userService;
	
	@Autowired
	private MessageRepo messageRepo;
	
	public String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	public MessageService(UserService userService) {
		this.userService=userService;
	}
	
	public List<Message> findAllByChatId(long id){
		return messageRepo.findAllByChatId(id);
	}
	
	public void reply(Chat chat, long idTo, String content) {
		Message message = new Message();
		message.setChat(chat);
		message.setContent(content);
		message.setCreated(LocalDate.now());
		message.setSendTo(userService.findOne(idTo));
		message.setSendFrom(userService.findByUserName(currentUser()));
		messageRepo.save(message);
		
	}
}
