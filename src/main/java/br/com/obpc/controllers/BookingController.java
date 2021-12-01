package br.com.obpc.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import br.com.obpc.dto.BookingDTO;
import br.com.obpc.entities.Booking;
import br.com.obpc.exceptions.BookingUnprocessableException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.representations.BookingRepresentation;
import br.com.obpc.representations.Representation;
import br.com.obpc.services.BookingService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/booking")
public class BookingController {
	
	private final BookingService service;
	
	private final JwtTokenHelper jwtHelper;
	
	
	@Autowired
	public BookingController(JwtTokenHelper jwtHelper, BookingService service) {
		this.service = service;
		this.jwtHelper = jwtHelper;
	}


	@ApiOperation(value = "Add a booking into database", notes = "Resource used to add a booking on database")
	@ApiResponses({@ApiResponse(code = 201, message = "", response = Booking.class)})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingRepresentation> createBooking(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "BokingDTO", required = true, type = "BookingDTO")@RequestBody BookingDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Booking> createdBooking = service.createBooking(dto);
		
		BookingRepresentation representation = new BookingRepresentation(createdBooking.get(), request);
		
		return new ResponseEntity<BookingRepresentation>(representation, HttpStatus.CREATED);
		
	}
	
	@ApiOperation(value = "Register the pickup booking into database", notes = "Resource used to register a booking pickup on database")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Booking.class)})
	@PutMapping(value = "/checkOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingRepresentation> registerPickUpBooking(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "BokingDTO", required = true, type = "BookingDTO")@RequestBody BookingDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Booking> createdBooking = service.registerPickUp(dto);
		
		BookingRepresentation representation = new BookingRepresentation(createdBooking.get(), request);
		
		return new ResponseEntity<BookingRepresentation>(representation, HttpStatus.OK);		
	}
	
	@ApiOperation(value = "Register the pickup booking into database", notes = "Resource used to register a booking pickup on database")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Booking.class)})
	@PutMapping(value = "/devolution", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingRepresentation> registerDevolution(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "BokingDTO", required = true, type = "BookingDTO")@RequestBody BookingDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Booking> createdBooking = service.registerDevolution(dto);
		
		BookingRepresentation representation = new BookingRepresentation(createdBooking.get(), request);
		
		return new ResponseEntity<BookingRepresentation>(representation, HttpStatus.OK);		
	}
	
	
	@ApiOperation(value = "Get a booking by ID", notes = "Resource used to get a booking on database by ID")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Booking.class)})
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingRepresentation> getBookingById(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Booking> optionalBooking = service.getBookingById(id);
		
		BookingRepresentation representation = new BookingRepresentation(optionalBooking.get(), request);
		
		return new ResponseEntity<BookingRepresentation>(representation, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Update a booking", notes = "Resource used to update a booking on database")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Booking.class)})
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookingRepresentation> updateBooking(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "BokingDTO", required = true, type = "BookingDTO")@RequestBody BookingDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Booking> optionalBooking = service.updateBooking(dto);
		
		BookingRepresentation representation = new BookingRepresentation(optionalBooking.get(), request);
		
		return new ResponseEntity<BookingRepresentation>(representation, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Get a list of booking by userId", notes = "Resource used to get a list of booking from a user")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = List.class)})
	@GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookingRepresentation>> getBookingByUserId(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "userId", required = true, type = "String")@PathVariable(value = "userId") String userId, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		List<Booking> listBooking = service.getBookingsByUserId(userId);
		
		List<BookingRepresentation> representation = Representation.getListRepresentation(listBooking, request, BookingRepresentation.class);
		
		return new ResponseEntity<List<BookingRepresentation>>(representation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete booking", notes = "Resource used to delete a booking on database by ID")
	@ApiResponses({ 
			@ApiResponse(code = 204, message = "", response = ResponseEntity.class),
			@ApiResponse(code = 422, message = "User not found", response = ObjectNotFoundException.class), 
			@ApiResponse(code = 422, message = "Deletion is not available for this booking, check if all books have been returned", response = BookingUnprocessableException.class) 
	})
	@DeleteMapping(value="/delete/{id}")
	public ResponseEntity<Void> deleteBooking(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "id", required = true, type = "String")@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		service.deleteBooking(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookingRepresentation>> getAllbooking(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		List<Booking> allBookings = service.getAllBookings();
		
		List<BookingRepresentation> representation = Representation.getListRepresentation(allBookings, request, BookingRepresentation.class);
		
		return new ResponseEntity<List<BookingRepresentation>>(representation, HttpStatus.OK);
		
	}
	
}
