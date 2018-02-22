package pl.coderslab.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.coderslab.entity.Request;
import pl.coderslab.entity.User;
import pl.coderslab.repo.RequestRepo;

/**
 * @author karolpat
 *
 */
@Service
public class RequestService {

	private final String GET_OWNER = "I want to become an owner.";
	private final String GET_MANAGER="I want to become a manager.";
	private final String GET_ENABLED = "I want to get enabled.";

	private UserService userService;
	private RoleService roleService;

	@Autowired
	private RequestRepo requestRepo;

	public RequestService(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	/**
	 * @param checked
	 *            - whether or not requests are flagged as checked.
	 * @return list of requests that are checked.
	 */
	public List<Request> findAllChecked(boolean checked) {
		return requestRepo.findAllByChecked(checked);
	}

	public Request findOne(long id) {
		return requestRepo.findOne(id);
	}

	/**
	 * Method to accept user's OnwerRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User acceptOwnerReq(long id) {

		Request req = findReqSetChecked(id);

		User user = userService.findOne(req.getUser().getId());
		user.setRole(roleService.findBySubName("Owner"));

		requestRepo.save(req);
		userService.save(user);
		return user;
	}

	/**
	 * Method to reject user's OnwerRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User rejectOwnerReq(long id) {

		Request req = findReqSetChecked(id);
		User user = userService.findOne(req.getUser().getId());
		user.setOwnerReq(false);

		requestRepo.save(req);
		userService.save(user);

		return user;
	}

	/**
	 * Method to accept user's ManagerRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User acceptManagerReq(long id) {

		Request req = findReqSetChecked(id);
		User user = userService.findOne(req.getUser().getId());
		user.setRole(roleService.findBySubName("Manager"));

		requestRepo.save(req);
		userService.save(user);

		return user;
	}

	/**
	 * Method to reject user's ManagerRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User rejectManagerReq(long id) {

		Request req = findReqSetChecked(id);
		User user = userService.findOne(req.getUser().getId());
		user.setManagerReq(false);

		requestRepo.save(req);
		userService.save(user);

		return user;
	}

	/**
	 * Method to reject user's EnabledRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User acceptEnableReq(long id) {
		Request req = findReqSetChecked(id);
		User user = userService.findOne(req.getUser().getId());
		user.setEnabled(true);

		userService.save(user);
		requestRepo.save(req);

		return user;
	}

	/**
	 * Method to reject user's EnabledRequest.
	 * 
	 * @param id
	 *            id of request that is being considered.
	 * @return user whose request is being considered.
	 */
	public User rejectEnableReq(long id) {
		Request req = findReqSetChecked(id);
		User user = userService.findOne(req.getUser().getId());
		user.setEnableReq(false);

		userService.save(user);
		requestRepo.save(req);

		return user;
	}

	/**
	 * Find request by given id to set checked to true
	 * 
	 * @param id
	 *            id of request to be set checked to true.
	 * @return request to be accepted/rejected.
	 */
	private Request findReqSetChecked(long id) {

		Request req = requestRepo.findOne(id);
		req.setChecked(true);
		return req;
	}

	/**
	 * Sends user's request to become an owner.
	 * 
	 * @param user
	 *            - user who wants to be an owner.
	 */
	public void sendOwnerReq(User user) {
		Request req = new Request();

		req.setDescription(GET_OWNER);
		req.setUser(user);

		requestRepo.save(req);
	}
	
	/**
	 * Sends user's request to become a manager.
	 * 
	 * @param user
	 *            - user who wants to be a manager.
	 */
	public void sendManagerReq(User user) {
		Request req = new Request();

		req.setDescription(GET_MANAGER);
		req.setUser(user);

		requestRepo.save(req);
	}

	/**
	 * Sends user's request to become enabled.
	 * 
	 * @param user
	 *            - user who wants to be enabled.
	 */
	public void sendEnableReq(User user) {
		Request req = new Request();

		req.setDescription(GET_ENABLED);
		req.setUser(user);

		requestRepo.save(req);
	}
}
