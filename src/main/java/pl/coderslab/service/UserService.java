package pl.coderslab.service;

import java.util.List;

import pl.coderslab.entity.Image;
import pl.coderslab.entity.User;

public interface UserService {
	
	public User findByUserName(String name);
	public void saveUser(User user, Image image);
	public void saveUser(User user);
	public List<User> getUsers();
	public User	getUser(long id);
	public void updateUser(User user, long id);
	public User findOne(long id);
	public void deleteUser(long id);
	public void editUser(User user, long id);
	
}
