package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Localization;
import pl.coderslab.repo.LocalizationRepo;

@Service
public class LocalizationService {

	@Autowired
	private LocalizationRepo localizationRepo;
	
	public Localization newLocalization(Localization localization) {
		Localization tmp = localization;
		localizationRepo.save(tmp);
		return tmp;
	}
}
