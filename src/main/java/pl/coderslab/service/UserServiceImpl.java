package pl.coderslab.service;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Image;
import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RoleRepo;
import pl.coderslab.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	

	private final UserRepo userRepository;
	private final RoleRepo roleRepository;
	private final BCryptPasswordEncoder passwordEncoder;
		

	public UserServiceImpl(UserRepo userRepository, RoleRepo roleRepository, BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public User findByUserName(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public void saveUser(User user, Image image) {
		
		user.setCreated(new LocalDate());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRole(userRole);
		user.setImage(image);
		userRepository.save(user);

	}
	
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	
	public User	getUser(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void updateUser(User user, long id) {
		User tmp = userRepository.findOne(id);
		user.setId(tmp.getId());
		user.setEmail(tmp.getEmail());
		user.setUsername(tmp.getUsername());
		user.setPassword(tmp.getPassword());
		userRepository.save(user);
	}

	@Override
	public User findOne(long id) {
		return userRepository.findOne(id);
	}

	@Override
	public void deleteUser(long id) {
		User user = userRepository.findOne(id);
		user.setActive(false);
		userRepository.save(user);
	}
	
	public void editUser(User user, long id) {
		User tmp = userRepository.findOne(id);
		
		tmp.setFirstName(user.getFirstName());
		tmp.setLastName(user.getLastName());
		tmp.setGender(user.getGender());
		tmp.setCountry(user.getCountry());
		tmp.setCity(user.getCity());
		tmp.setStreet(user.getStreet());
		tmp.setPhoneNumber(user.getPhoneNumber());
		userRepository.save(tmp);
	}

	@Override
	public void saveUser(User user) {
		user.setCreated(new LocalDate());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		user.setRole(userRole);
		userRepository.save(user);		
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void saveWithImage(User user, Image image) {
		user.setImage(image);
		userRepository.save(user);
	}
	
	

}
