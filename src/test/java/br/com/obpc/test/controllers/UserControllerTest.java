package br.com.obpc.test.controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.obpc.controllers.UserController;
import br.com.obpc.exceptions.ForbiddenException;
import br.com.obpc.exceptions.InvalidUsernameException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.exceptions.PasswordNotPresentException;
import br.com.obpc.exceptions.TokenForbiddenException;
import br.com.obpc.exceptions.UsernameExistingException;
import br.com.obpc.repository.UserRepository;
import br.com.obpc.representations.UserRepresentations;
import br.com.obpc.services.UserService;
import br.com.obpc.test.fixtures.FixtureUserData;
import br.com.obpc.token.JwtTokenHelper;

@RunWith(JUnitPlatform.class)
public class UserControllerTest {

	private static final String USER_ID_FAKE = "100-fakeId";
	private static final String USERNAME_FAKE = "adm";
	private static final String PASSWORD_FAKE = "password";
	private static final String TOKEN_FAKE = "token_fake112020";
	private static final String REQUESTER_FAKE = "requesterIdFake";
	private static final String NEW_PASSWORD_FAKE = "newPassword_fake";
	
	private FixtureUserData fixture;
	
	private UserController controller;
	@Mock
	private UserService service;	
	@Mock
	private UserRepository repo;
	@Mock
	private JwtTokenHelper jwtHelper;
	@Mock
	private HttpServletRequest request;
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new UserController(service, jwtHelper);
		fixture =  new FixtureUserData();
	}
	
	
	@Test
	public void login_sucess_return_200() throws Exception {
		
		when(service.getLoggin(jwtHelper, USERNAME_FAKE, PASSWORD_FAKE)).thenReturn(fixture.getMockUserFound());	
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://baseURLTest/obpc/user"));
		when(request.getRequestURI()).thenReturn("/obpc/user");
		
		ResponseEntity<UserRepresentations> userOptional = controller.login(USERNAME_FAKE, PASSWORD_FAKE, request);		
		UserRepresentations userAllowed = userOptional.getBody();
	
		assertTrue(userOptional.getStatusCode().equals(HttpStatus.OK));
		assertTrue(userAllowed != null);
		assertTrue(userAllowed.get_link().getSelf().getHref().equals("http://baseURLTest/obpc/user/"+FixtureUserData.MOCK_USER_ID));
		assertTrue(userAllowed.getUsername().equals("UserFound"));
		assertTrue(userAllowed.getRole().equals("ROLE"));
		assertTrue(userAllowed.getActive());
		
		
	}
	
	@Test
	public void login_fail_wrong_username_or_password_return_403() throws Exception {
		
		when(service.getLoggin(jwtHelper,  USERNAME_FAKE, PASSWORD_FAKE)).thenThrow(new ForbiddenException());
		
		ForbiddenException exception = assertThrows(ForbiddenException.class, 
				()-> controller.login(USERNAME_FAKE, PASSWORD_FAKE, request));
		
		assertTrue(exception.getStatus() == HttpStatus.FORBIDDEN.value());
		assertTrue(exception.getMessage().equals("Login forbiden"));
		
	}
	
	@Test
	public void update_password_success_return_204() throws Exception{
		
		when(service.updatePassword(USER_ID_FAKE, NEW_PASSWORD_FAKE, PASSWORD_FAKE)).thenReturn(fixture.getMockOptionalUser());
		
		ResponseEntity<Void> response = controller.updatePassword(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, PASSWORD_FAKE, NEW_PASSWORD_FAKE);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}
	
	
	@Test
	public void update_password_fail_wrong_old_password_return_403() throws Exception{
		
		when(service.updatePassword(USER_ID_FAKE, NEW_PASSWORD_FAKE, PASSWORD_FAKE)).thenThrow(new ForbiddenException("Password is not matching"));
		
		ForbiddenException exception = assertThrows(ForbiddenException.class,
				() -> controller.updatePassword(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE,  PASSWORD_FAKE, NEW_PASSWORD_FAKE));
		
		assertTrue(exception.getStatus() == HttpStatus.FORBIDDEN.value());
		assertTrue(exception.getMessage().equals("Password is not matching"));
		
	}
	
	@Test
	public void update_password_fail_invalid_token_return_403() throws Exception{
		
		when(jwtHelper.validateToken(TOKEN_FAKE, REQUESTER_FAKE)).thenThrow(new TokenForbiddenException());
		
		TokenForbiddenException exception = assertThrows(TokenForbiddenException.class,
				() -> controller.updatePassword(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, PASSWORD_FAKE, NEW_PASSWORD_FAKE));
		
		assertTrue(exception.getStatus() == HttpStatus.FORBIDDEN.value());
		assertTrue(exception.getMessage().equals("Invalid token"));
		
	}
	
	@Test
	public void update_password_fail_wrong_user_id_return_404() throws Exception {
		when(service.updatePassword(USER_ID_FAKE, NEW_PASSWORD_FAKE, PASSWORD_FAKE)).thenThrow(new ObjectNotFoundException("User not found"));
		
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> controller.updatePassword(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, PASSWORD_FAKE, NEW_PASSWORD_FAKE));
		
		assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND.value());
		assertTrue(exception.getMessage().equals("User not found"));
	}
	
	
	@Test
	public void update_user_success_return_204() throws Exception{
		
		when(service.updateUser(any())).thenReturn(fixture.getMockUserFound());
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://baseURLTest/obpc/user"));
		when(request.getRequestURI()).thenReturn("/obpc/user");
		
		ResponseEntity<UserRepresentations> response = controller.updateUser(REQUESTER_FAKE, TOKEN_FAKE, fixture.getMockUserDTO(), request);
		
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
	}
	
	
	@Test
	public void update_user_fail_invalid_token_return_403() throws Exception{
		
		when(jwtHelper.validateToken(any(), any())).thenThrow(new TokenForbiddenException());
		
		TokenForbiddenException exception = assertThrows(TokenForbiddenException.class,
				() -> controller.updateUser(REQUESTER_FAKE, TOKEN_FAKE, fixture.getMockUserDTO(), request));
		
		assertTrue(exception.getStatus() == HttpStatus.FORBIDDEN.value());
		assertTrue(exception.getMessage().equals("Invalid token"));
		
	}
	
	@Test
	public void update_user_fail_username_already_used_return_404() throws Exception {
		when(service.updatePassword(USER_ID_FAKE, NEW_PASSWORD_FAKE, PASSWORD_FAKE)).thenThrow(new UsernameExistingException());
		
		UsernameExistingException exception = assertThrows(UsernameExistingException.class,
				() -> controller.updatePassword(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, PASSWORD_FAKE, NEW_PASSWORD_FAKE));
		
		assertTrue(exception.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertTrue(exception.getMessage().equals("Username already exist"));
	}
	
	@Test
	public void create_user_success_return_201() throws Exception {
		
		when(service.createUser(any(), any())).thenReturn(fixture.getMockUserFound());
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://baseURLTest/obpc/user"));
		when(request.getRequestURI()).thenReturn("/obpc/user");
		
		
		ResponseEntity<UserRepresentations> response = controller.createUser(fixture.getMockNewUserDTO(), request);
		
		UserRepresentations userCreated = response.getBody();
		
		assertTrue(response.getStatusCode().equals(HttpStatus.CREATED));
		assertTrue(userCreated.getId() == fixture.getMockUserFound().getId());
		
	}
	
	
	@Test
	public void create_user_fail_password_is_not_present_return_422() throws Exception {
		
		when(service.createUser(any(), any())).thenThrow(new PasswordNotPresentException());
		
		PasswordNotPresentException exception = assertThrows(PasswordNotPresentException.class, 
				()-> controller.createUser(fixture.getMockNewUserDTO(), request));
		
		assertTrue(exception.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertTrue(exception.getMessage().equals("Password is not present"));
		
	}
	
	@Test
	public void create_user_fail_invalid_username_format_412() throws Exception {
		
		when(service.createUser(any(), any())).thenThrow(new InvalidUsernameException());
		
		InvalidUsernameException exception = assertThrows(InvalidUsernameException.class, 
				()-> controller.createUser(fixture.getMockNewUserDTO(), request));
		
		assertTrue(exception.getStatus() == HttpStatus.PRECONDITION_FAILED.value());
		assertTrue(exception.getMessage().equals("Username is not a valid email address"));
		
	}
	
	@Test
	public void create_user_fail_username_alreday_exist_return_422() throws Exception {
		when(service.createUser(any(), any())).thenThrow(new UsernameExistingException());
		
		UsernameExistingException exception = assertThrows(UsernameExistingException.class, 
				()-> controller.createUser(fixture.getMockNewUserDTO(), request));
		
		assertTrue(exception.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY.value());
		assertTrue(exception.getMessage().equals("Username already exist"));
	}
	
	@Test
	public void get_user_by_id_success_return_200() throws Exception{
		
		when(service.getUserById(any())).thenReturn(fixture.getMockUserFound());
		when(request.getRequestURL()).thenReturn(new StringBuffer("http://baseURLTest/obpc/user"));
		when(request.getRequestURI()).thenReturn("/obpc/user");
		
		ResponseEntity<UserRepresentations> response = controller.getUserById(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, request);
		
		UserRepresentations user = response.getBody();
		
		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(user != null);
		assertTrue(user.getId().equals(USER_ID_FAKE));
	}
	
	@Test
	public void get_user_by_id_fail_invalid_token_return_403() throws Exception {
		
		when(jwtHelper.validateToken(any(), any())).thenThrow(new TokenForbiddenException());
		
		TokenForbiddenException exception = assertThrows(TokenForbiddenException.class,
				() -> controller.getUserById(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, request));
		
		assertTrue(exception.getStatus() == HttpStatus.FORBIDDEN.value());
		assertTrue(exception.getMessage().equals("Invalid token"));
	}
	
	@Test
	public void get_user_by_id_fail_user_not_found_return_404() throws Exception {
		
		when(service.getUserById(any())).thenThrow(new ObjectNotFoundException("User not found"));
		
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class,
				() -> controller.getUserById(REQUESTER_FAKE, TOKEN_FAKE, USER_ID_FAKE, request));
		
		assertTrue(exception.getStatus() == HttpStatus.NOT_FOUND.value());
		assertTrue(exception.getMessage().equals("User not found"));
	}
}
