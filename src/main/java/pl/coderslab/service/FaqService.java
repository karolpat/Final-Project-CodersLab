package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Faq;
import pl.coderslab.repo.FaqRepo;

@Service
public class FaqService {
	
	@Autowired
	private FaqRepo faqRepo;
	
	public FaqService(FaqRepo faqRepo) {
		this.faqRepo=faqRepo;
	}
	
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
	
	public List<Faq> findAll(){
		return faqRepo.findAll();
	}
}
