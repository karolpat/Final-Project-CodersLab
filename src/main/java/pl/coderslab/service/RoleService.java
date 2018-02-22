package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Role;
import pl.coderslab.repo.RoleRepo;

@Service
public class RoleService {

	@Autowired
	private RoleRepo roleRepo;
	
	public List<Role> findAll(){
		return roleRepo.findAll();
	}
	
	public Role findBySubName(String subname) {
		return roleRepo.findBySubName(subname);
	}
}
