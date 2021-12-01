package br.com.obpc.web.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class InitController {
	
	@GetMapping(value= {"/", "/login", "/search", "/users", "/booking", "/bookingDetail"})
	public String init() {
		return "Init";
	}

	@GetMapping(value= {"/bookingDetail/{id}"})
	public void initBooking(HttpServletResponse response) throws IOException {
		response.sendRedirect("/obpc/bookingDetail");
	}
	
	@GetMapping(value= {"/customerForm/{userId}"})
	public void initCustomer(HttpServletResponse response) throws IOException {
		response.sendRedirect("/obpc/customerForm");
	}

}
