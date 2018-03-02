package pl.coderslab.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.coderslab.entity.Chat;
import pl.coderslab.entity.User;
import pl.coderslab.repo.ChatRepo;
import pl.coderslab.service.ChatService;

@RunWith(SpringJUnit4ClassRunner.class)
public class ChatServiceTest {

	private ChatRepo chatRepo;
	private ChatService chatService;
	private User user;
	
	@Before
	public void setUp() {
		chatRepo=mock(ChatRepo.class);
		chatService= new ChatService(chatRepo);
		user = new User(1);
	}
	
	@Test
	public void testFindAllByUserId() {
		
		//given
		Chat first = new Chat();
		Chat second = new Chat();
		first.setUser(new ArrayList(Arrays.asList(user)));
		second.setUser(new ArrayList(Arrays.asList(user)));
		List<Chat> list = Arrays.asList(first, second);
		when(chatRepo.findAllByUserId(1)).thenReturn(list);
		
		//when
		List<Chat> result = chatService.findAllByUserId(1);
		
		//then
		assertEquals(list, result);
		assertEquals(first, result.get(0));
	}

	@Test
	public void testFindOne() {
		
		//given
		Chat first = new Chat(1);
		when(chatRepo.findOne(1l)).thenReturn(first);
		//when
		Chat result = chatService.findOne(1);
		//then
		assertEquals(first, result);
	}

}
