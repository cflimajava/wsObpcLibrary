package br.com.obpc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.representations.UserRepresentations;
import br.com.obpc.services.UserService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
public class ActivationController {
	
	private final UserService userService;
	private final JwtTokenHelper jwtHelper;
	
	
	@Autowired
	public ActivationController(UserService userService, JwtTokenHelper jwtHelper) {
		this.userService = userService;
		this.jwtHelper = jwtHelper;
	}

	
	@ApiOperation(value = "Active user by ID", notes = "Resource used activate user by id using link sent by email")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = UserRepresentations.class),
			@ApiResponse(code = 404, message = "User not found", response = ObjectNotFoundException.class) 
	})
	@GetMapping(value = "user/activation/{id}/{token}")
	public String emailConfirmation(
			@ApiParam(value = "id", required = true, type = "String") @PathVariable(value = "id") String id,
			@ApiParam(value = "token", required = true, type = "String") @PathVariable(value = "token") String token) throws Exception {

		jwtHelper.validateToken(token, id);
		userService.activateUserById(id);		
		
		return "activation";

	}
}
