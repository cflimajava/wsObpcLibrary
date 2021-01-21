package br.com.obpc.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obpc.dto.UserDTO;
import br.com.obpc.entities.User;
import br.com.obpc.exceptions.ForbiddenException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.exceptions.PasswordNotPresentException;
import br.com.obpc.exceptions.UsernameExistingException;
import br.com.obpc.representations.UserRepresentations;
import br.com.obpc.services.UserService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/user")
public class UserController {

	private final UserService userService;
	private final JwtTokenHelper jwtHelper;
	
	
	@Autowired
	public UserController(UserService userService, JwtTokenHelper jwtHelper) {
		this.userService = userService;
		this.jwtHelper = jwtHelper;
	}

	@ApiOperation(value = "Return a user data if username and password match", notes = "Resource used to check username/password")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "User found", response = User.class),
			@ApiResponse(code = 403, message = "Username or password is not matching", response = ForbiddenException.class) 
	})
	@GetMapping(value = "/login")
	public ResponseEntity<UserRepresentations> login(
			@ApiParam(value = "userName", required = true, type = "String") @RequestHeader(name = "username") String username,
			@ApiParam(value = "password", required = true, type = "String") @RequestHeader(name = "password") String password, 
			HttpServletRequest request) throws Exception {		
		
		User userAllowed = userService.getLoggin(username, password);
		
		UserRepresentations userAllowedRepresentaion = new UserRepresentations(userAllowed, request);
		
		return new ResponseEntity<UserRepresentations>(userAllowedRepresentaion, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Do logout deleting access token", notes = "Do logout deleting access token")
	@ApiResponses({ 
			@ApiResponse(code = 204, message = "", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "User not found", response = ObjectNotFoundException.class) 
	})
	@GetMapping(value = "/logout")
	public ResponseEntity<Void> logout(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token) throws Exception{
		jwtHelper.validateToken(token, requesterId);
		
		userService.doLogout(requesterId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Update password on database", notes = "Resource used to update password on database")
	@ApiResponses({ 
			@ApiResponse(code = 204, message = "", response = ResponseEntity.class),
			@ApiResponse(code = 404, message = "User not found", response = ObjectNotFoundException.class) 
	})
	@PutMapping(value = "/updatePassword")
	public ResponseEntity<Void> updatePassword(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "userId", required = true, type = "String") @RequestHeader(name = "userId") String userId,
			@ApiParam(value = "oldPassword", required = true, type = "String") @RequestHeader(name = "oldPassword") String oldPassword,
			@ApiParam(value = "newPassword", required = true, type = "String") @RequestHeader(name = "newPassword") String newPassword) throws Exception {
		
		jwtHelper.validateToken(token, requesterId);
		
		userService.updatePassword(userId, newPassword, oldPassword);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Update user data and return user object updated", notes = "Resource used to update user data on database")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = User.class),
			@ApiResponse(code = 404, message = "User not found", response = UsernameExistingException.class) 
	})
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserRepresentations> updateUser(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "UserDTO", required = true, type = "UserDTO") @RequestBody UserDTO userDTO, 
			HttpServletRequest request) throws Exception {
		
		jwtHelper.validateToken(token, requesterId);

		User updatedUser = userService.updateUser(new User(userDTO));
		
		UserRepresentations updatedUserRepresentation = new UserRepresentations(updatedUser, request);
		
		return new ResponseEntity<UserRepresentations>(updatedUserRepresentation, HttpStatus.OK);
	}

	@ApiOperation(value = "Create and return the user", notes = "Resource used to create user on database")
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "", response = User.class),
			@ApiResponse(code = 423, message = "Password is not present", response = PasswordNotPresentException.class),
			@ApiResponse(code = 423, message = "Username already exist", response = UsernameExistingException.class) 
	})
	@PostMapping(value = "/create")
	public ResponseEntity<UserRepresentations> createUser(
			@ApiParam(value = "Just username and password are required. Username need be a valid email", required = true, type = "UserDTO") @RequestBody UserDTO userDTO,	
			HttpServletRequest request) throws Exception {
		
		User userCreated = userService.createUser(userDTO.getUsername(), userDTO.getPassword());
		
		UserRepresentations userRepresentation = new UserRepresentations(userCreated, request);
		
		return new ResponseEntity<UserRepresentations>(userRepresentation, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Search user by id", notes = "Resource used to find user by id")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = UserRepresentations.class),
			@ApiResponse(code = 404, message = "User not found", response = ObjectNotFoundException.class) 
	})
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserRepresentations> getUserById(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "userId", required = true, type = "String") @PathVariable(value = "userId") String userId,
			HttpServletRequest request) throws Exception {

		jwtHelper.validateToken(token, requesterId);		
		
		User userFound = userService.getUserById(userId);

		UserRepresentations userFoundRepresentation = new UserRepresentations(userFound, request);		
		
		return new ResponseEntity<UserRepresentations>(userFoundRepresentation, HttpStatus.OK);

	}
	
	
	@ApiOperation(value = "Active user by ID", notes = "Resource used activate user by id using link sent by email")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = UserRepresentations.class),
			@ApiResponse(code = 404, message = "User not found", response = ObjectNotFoundException.class) 
	})
	@GetMapping(value = "/activation/{id}/{token}")
	public ResponseEntity<Void> emailConfirmation(
			@ApiParam(value = "id", required = true, type = "String") @PathVariable(value = "id") String id,
			@ApiParam(value = "token", required = true, type = "String") @PathVariable(value = "token") String token,
			HttpServletRequest request) throws Exception {

		jwtHelper.validateToken(token, id);		
		
		userService.activateUserById(id);		
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
