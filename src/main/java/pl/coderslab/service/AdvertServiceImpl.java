package pl.coderslab.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

import pl.coderslab.entity.Advert;
import pl.coderslab.repo.AdvertRepo;

public class AdvertServiceImpl implements AdvertService {

	@Autowired
	private AdvertRepo advertRepo;
	
	@Override
	public List<Advert> findAll() {
		return advertRepo.findAll();
	}

	@Override
	public List<Advert> findAllByOwnerUsername(String username) {
		return advertRepo.findAllByOwnerUsername(username);
	}

	@Override
	public List<Advert> findAllByHostUsername(String username) {
		return advertRepo.findAllByHostUsername(username);
	}

	@Override
	public List<Advert> checkAvailability(LocalDate start, LocalDate end) {
		
		
		
		return null;
	}

	@Override
	public List<Advert> showAllAvailable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advert> showAllPromoted() {
		// TODO Auto-generated method stub
		return null;
	}

}
