package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Request;
import pl.coderslab.repo.RequestRepo;

@Service
public class RequestService {

	@Autowired
	private RequestRepo requestRepo;
	
	public List<Request> rList(boolean checked){
		return requestRepo.findAllByChecked(checked);
	}
}
