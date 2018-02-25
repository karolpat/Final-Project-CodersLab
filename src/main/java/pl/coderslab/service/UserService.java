package pl.coderslab.service;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;

import pl.coderslab.entity.Image;
import pl.coderslab.entity.User;

/**
 * @author karolpat
 *
 */
public interface UserService {

	/**
	 * Get User by its username
	 * 
	 * @param name
	 *            - username of user to be found.
	 * @return
	 */
	public User findByUserName(String name);

	/**
	 * Saving new user while register. Encrypts password, sets user's avatar to
	 * default and role to USER.
	 * 
	 * @param user
	 *            - new registered user.
	 * @param image
	 *            - default user's avatar.
	 */
	public void saveUser(User user, Image image);

	/**
	 * REST API Saves edited user.
	 * 
	 * @param user
	 *            - edited user to be saved.
	 */
	public void saveUser(User user);

	/**
	 * @return list of all users.
	 */
	public List<User> getUsers();

	/**
	 * @param id
	 *            - id of user to be returned.
	 * @return user of given id.
	 */
	public User getUser(long id);

	/**
	 * Saves edited user.
	 * 
	 * @param user
	 *            - user to be edited.
	 * @param id
	 *            - id of user to be edited.
	 */
	public void updateUser(User user, long id);

	/**
	 * Get user by exact id.
	 * 
	 * @param id
	 *            - id of user to be found.
	 * @return
	 */
	public User findOne(long id);

	/**
	 * Deletes user by id.
	 * 
	 * @param id
	 *            - id of user to be deleted.
	 */
	public void deleteUser(long id);

	/**
	 * User's details to be updated. From personal details form.
	 * 
	 * @param user
	 *            - user to be updated.
	 * @param id
	 *            - id of user to be updated.
	 */
	public void editUser(User user, long id);

	/**
	 * Saved user edited by accepting or rejecting requests.
	 * 
	 * @param user
	 *            - user to be updated.
	 */
	public void save(User user);

	/**
	 * Saves user with updated image.
	 * 
	 * @param user
	 *            - user to be updated.
	 * @param image
	 *            - uploaded by user image.
	 */
	public void saveWithImage(User user, Image image);

	/**
	 * Creates list of id of rooms by current user id
	 * 
	 * @param id
	 *            - id of the user who is host in rooms which id numbers create list
	 * @return
	 */
	public List<BigInteger> findRoomsWhereUserIsHost(long id);

	/**
	 * Generates a pdf file with all details connected to booked room.
	 * 
	 * @param roomId
	 *            - id booked room.
	 * @param dateId
	 *            - date of stay.
	 * @param user
	 *            - user that made the booking
	 * @throws FileNotFoundException 
	 */
	public void printConfirmation(long roomId, long dateId, User user) throws FileNotFoundException;

}
