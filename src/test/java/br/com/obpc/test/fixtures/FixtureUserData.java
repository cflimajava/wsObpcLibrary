package br.com.obpc.test.fixtures;

import java.util.Optional;

import br.com.obpc.dto.UserDTO;
import br.com.obpc.entities.User;

public class FixtureUserData {
	
	public final static String MOCK_USER_ID = "100-fakeId";

	private  User mockUserFound;
	
	private UserDTO mockUserDTO;
	
	private UserDTO mockNewUserDTO;
	
	public FixtureUserData() {
		this.mockUserFound = new User(MOCK_USER_ID, "UserFound", null, "ROLE", true);		
		this.mockUserDTO = new UserDTO(MOCK_USER_ID, "UserFound", "password", "ROLE", true);
		this.mockNewUserDTO = new UserDTO("UserFound", "password");
	}

	public User getMockUserFound() {
		return mockUserFound;
	}

	public void setMockUserFound(User mockUserFound) {
		this.mockUserFound = mockUserFound;
	}

	public  Optional<User> getMockOptionalUser() {
		return Optional.of(this.mockUserFound);
	}

	public UserDTO getMockUserDTO() {
		return mockUserDTO;
	}

	public void setMockUserDTO(UserDTO mockUserDTO) {
		this.mockUserDTO = mockUserDTO;
	}

	public UserDTO getMockNewUserDTO() {
		return mockNewUserDTO;
	}

	public void setMockNewUserDTO(UserDTO mockNewUserDTO) {
		this.mockNewUserDTO = mockNewUserDTO;
	}
	
}
