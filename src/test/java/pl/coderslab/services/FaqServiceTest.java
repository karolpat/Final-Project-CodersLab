package pl.coderslab.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.coderslab.entity.Faq;
import pl.coderslab.repo.FaqRepo;
import pl.coderslab.service.FaqService;
@RunWith(SpringJUnit4ClassRunner.class)
public class FaqServiceTest {
	
	private FaqRepo faqRepo;
	private FaqService faqService;
	
	@Before
	public void setUp() {
		faqRepo=mock(FaqRepo.class);
		faqService = new FaqService(faqRepo);
	}

	@Test
	public void testFindById() {
		//given
		Faq first = new Faq(1);
		when(faqRepo.findOne(1l)).thenReturn(first);
		//when
		Faq result = faqService.findById(1);
		//then
		assertEquals(first, result);
		assertThat(result).isInstanceOf(Faq.class);
	}


	@Test
	public void testChangeRate() {
		
		Faq first = new Faq();
		first.setRate(4);
		
		faqService.changeRate(first);
		int result = first.getRate();
		
		assertEquals(5, result);
	}

	@Test
	public void testFindAll() {
		
		List<Faq> list = Arrays.asList(new Faq(), new Faq());
		when(faqRepo.findAll()).thenReturn(list);
		
		List<Faq> result = faqService.findAll();
		
		assertEquals(list, result);
		assertThat(result).hasOnlyElementsOfType(Faq.class);
		assertThat(result).hasSize(2);
	}

}
