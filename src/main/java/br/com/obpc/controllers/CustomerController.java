package br.com.obpc.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obpc.entities.Book;
import br.com.obpc.entities.Customer;
import br.com.obpc.representations.CustomerRepresentation;
import br.com.obpc.representations.UserRepresentations;
import br.com.obpc.services.CustomerService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	private final JwtTokenHelper jwtHelper;
	
	@Autowired
	public CustomerController(CustomerService customerService,  JwtTokenHelper jwtHelper) {
		this.customerService = customerService;
		this.jwtHelper = jwtHelper;
	}



	@ApiOperation(value = "Create and return a customer", notes = "Resource used to insert a customer data on database")
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "", response = Customer.class) 
	})
	@PostMapping(value = "/create")
	public ResponseEntity<CustomerRepresentation> createCustomer(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "Customer data", required = true, type = "CustomerDTO") @RequestBody Customer dto,	
			HttpServletRequest request) throws Exception {

		jwtHelper.validateToken(token, requesterId);
		
		Optional<Customer> optCustomer = customerService.createCustomer(dto);
		
		CustomerRepresentation customerRepresentation = new CustomerRepresentation(optCustomer.get(), request);
		
		return new ResponseEntity<CustomerRepresentation>(customerRepresentation, HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "Update and return a customer", notes = "Resource used to update a customer data on database")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = Customer.class) 
	})
	@PutMapping(value = "/update")
	public ResponseEntity<CustomerRepresentation> updateCustomer(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "Customer data", required = true, type = "Customer") @RequestBody Customer dto,	
			HttpServletRequest request) throws Exception {

		jwtHelper.validateToken(token, requesterId);
		
		Optional<Customer> optCustomer = customerService.updateCustomer(dto);
		
		CustomerRepresentation customerRepresentation = new CustomerRepresentation(optCustomer.get(), request);
		
		return new ResponseEntity<CustomerRepresentation>(customerRepresentation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Search customer by id", notes = "Resource used to find customer by id")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = UserRepresentations.class)
	})	
	@GetMapping(value = "/{customerId}")
	public ResponseEntity<CustomerRepresentation> getCustomerById(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "customerId", required = true, type = "String") @PathVariable(value = "customerId") String customerId,
			HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Customer customer = customerService.getCustomerById(customerId);
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		
		if(customer != null) {
			customerRepresentation = new CustomerRepresentation(customer, request);
		}
		
		return new ResponseEntity<CustomerRepresentation>(customerRepresentation, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Search customer by id", notes = "Resource used to find customer by id")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "", response = UserRepresentations.class)
	})	
	@GetMapping(value = "/getByUserId/{userId}")
	public ResponseEntity<CustomerRepresentation> getCustomerByUserId(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "customerId", required = true, type = "String") @PathVariable(value = "userId") String userId,
			HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Customer> customerOpt = customerService.getCustomerByUserId(userId);
		CustomerRepresentation customerRepresentation = new CustomerRepresentation();
		
		if(customerOpt.isPresent()) {
			customerRepresentation = new CustomerRepresentation(customerOpt.get(), request);
		}
		
		return new ResponseEntity<CustomerRepresentation>(customerRepresentation, HttpStatus.OK);
		
	}
	
	
	@ApiOperation(value = "Delete a customer data" , notes = "Resource used to delete a customer data on database")
	@ApiResponses({@ApiResponse(code = 204, message = "", response = Book.class)})
	@DeleteMapping(value="/{customerId}")
	public ResponseEntity<Void> DeleteCustomer(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@PathVariable(value = "customerId") String customerId, HttpServletRequest request) throws Exception{
				
		jwtHelper.validateToken(token, requesterId);
		
		customerService.deleteCustomer(customerId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

}
