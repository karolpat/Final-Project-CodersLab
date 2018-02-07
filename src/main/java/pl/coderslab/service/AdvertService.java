package pl.coderslab.service;

import java.util.List;

import org.joda.time.LocalDate;

import pl.coderslab.entity.Advert;

public interface AdvertService {
	
	List<Advert> findAll();

	List<Advert> findAllByOwnerUsername(String username);

	List<Advert> findAllByHostUsername(String username);
	
	
}
