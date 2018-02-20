package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Faq;
import pl.coderslab.repo.FaqRepo;

@Service
public class FaqService {
	
	@Autowired
	private FaqRepo faqRepo;
	
	public Faq findById(long id) {
		return faqRepo.findOne(id);
	}
	
	public void saveFaq(Faq faq) {
		faqRepo.save(faq);
	}
	
	public void changeRate(Faq faq) {
		faq.setRate(faq.getRate() + 1);
		faqRepo.save(faq);
	}
}
