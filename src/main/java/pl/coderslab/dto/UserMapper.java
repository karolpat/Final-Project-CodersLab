package pl.coderslab.dto;

import pl.coderslab.entity.User;

public class UserMapper {

	public UserMapper() {}
	
	static UserDTO toDTO(User user ) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail(user.getEmail());
		userDTO.setUsername(user.getUsername());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setCreated(user.getCreated());
		userDTO.setGender(user.getGender());
		
		return userDTO;
	}
}
