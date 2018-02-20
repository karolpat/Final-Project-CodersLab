package pl.coderslab.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.Message;
import pl.coderslab.entity.User;
import pl.coderslab.repo.MessageRepo;

@Service
public class MessageService {
	
	private UserService userService;
	private ChatService chatService;
	
	@Autowired
	private MessageRepo messageRepo;
	
	public String currentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	public MessageService(UserService userService, ChatService chatService) {
		this.userService=userService;
		this.chatService=chatService;
	}
	
	public List<Message> findAllByChatId(long id){
		return messageRepo.findAllByChatId(id);
	}
	
	/** Method to reply to message
	 * @param chat - id of chat where the messages are present.
	 * @param idTo - id of the author of message.
	 * @param content - text of the message.
	 */
	public void reply(Chat chat, long idTo, String content) {
		Message message = new Message();
		message.setChat(chat);
		message.setContent(content);
		message.setCreated(LocalDate.now());
		message.setSendTo(userService.findOne(idTo));
		message.setSendFrom(userService.findByUserName(currentUser()));
		messageRepo.save(message);
	}
	
	/** Method to send message to user.
	 * @param idFrom - id of the author of the message.
	 * @param idTo - id of the receiver of the message
	 * @param content - text of the message.
	 */
	public void sendMessage(long idFrom, long idTo, String content) {
		User author = userService.findOne(idFrom);
		User receiver = userService.findOne(idTo);
		
		List<User> chatUserList = new ArrayList<User>();
		chatUserList.add(author);
		chatUserList.add(receiver);
		
		Chat chat = new Chat();
		chatService.saveAndFlush(chat);
		
		chat.setUser(chatUserList);
		chatService.save(chat);
		
		Message mess = new Message();
		mess.setContent(content);
		mess.setSendFrom(author);
		mess.setSendTo(receiver);
		mess.setCreated(LocalDate.now());
		mess.setChat(chat);
		messageRepo.saveAndFlush(mess);

		List<Message> messList;

		if (chat.getMessage() == null) {
			messList = new ArrayList<>();
		} else {
			messList = chat.getMessage();
		}
		messList.add(mess);
		chat.setMessage(messList);
		
		chatService.save(chat);
		
	}
}
