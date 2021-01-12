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

import br.com.obpc.dto.BookDTO;
import br.com.obpc.entities.Book;
import br.com.obpc.representations.BookRepresentation;
import br.com.obpc.services.BookService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/book")
public class BookController {
	
	
	private final BookService bookService;
	private final JwtTokenHelper jwtHelper;
	
	@Autowired
	public BookController(BookService bookService, JwtTokenHelper jwtHelper) {
		this.bookService = bookService;
		this.jwtHelper = jwtHelper;
	}

	@ApiOperation(value = "Add a book into database", notes = "Resource used to add a book on database")
	@ApiResponses({@ApiResponse(code = 201, message = "", response = Book.class)})
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookRepresentation> addBook(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "Just Id and image can be empty", required = true, type = "BookDTO")@RequestBody BookDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Book> optionalBook = bookService.saveBook(dto);
		
		BookRepresentation representation = new BookRepresentation(optionalBook.get(), request);
		
		return new ResponseEntity<BookRepresentation>(representation, HttpStatus.CREATED);
		
	}
	
	@ApiOperation(value = "Update a book data", notes = "Resource used to update a book data on database")
	@ApiResponses({@ApiResponse(code = 204, message = "", response = Book.class)})
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> updateBook(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "Just image field can be empty", required = true, type = "BookDTO")@RequestBody BookDTO dto, HttpServletRequest request) throws Exception{
				
		jwtHelper.validateToken(token, requesterId);
		
		bookService.updateBook(dto);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	@ApiOperation(value = "Delete a book" , notes = "Resource used to delete a book on database")
	@ApiResponses({@ApiResponse(code = 204, message = "", response = Book.class)})
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> DeleteBook(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@PathVariable(value = "id") String bookId, HttpServletRequest request) throws Exception{
				
		jwtHelper.validateToken(token, requesterId);
		
		bookService.deleteBook(bookId);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
	
	
	@ApiOperation(value = "Get book by Id", notes = "Resource used to find a book by Id")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
	@GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookRepresentation> getBookById(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@PathVariable(value = "id") String id, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);		
		Optional<Book> optionalBook = bookService.getBookById(id);
		BookRepresentation representation = new BookRepresentation(optionalBook.get(), request);
		
		return new ResponseEntity<BookRepresentation>(representation, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Get book by title", notes = "Resource used to find a book by title")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
	@GetMapping(value="/title/{title}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookRepresentation>> getBookByTitle(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@PathVariable(value = "title") String title, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);		
		List<Book> listBooks = bookService.getBookByTitle(title);
		List<BookRepresentation> representationList = BookRepresentation.getListRepresentation(listBooks, request);
		
		return new ResponseEntity<List<BookRepresentation>>(representationList, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Get book by title", notes = "Resource used to find a book by title")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
	@GetMapping(value="/findAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookRepresentation>> getAllBooks(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);		
		List<Book> listBooks = bookService.getAllBooks();
		List<BookRepresentation> representationList = BookRepresentation.getListRepresentation(listBooks, request);
		
		return new ResponseEntity<List<BookRepresentation>>(representationList, HttpStatus.OK);
		
	}
	
	
	@ApiOperation(value = "Get book by filter", notes = "Resource used to find a book by more than one data")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Book.class)})
	@PostMapping(value="/filter",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BookRepresentation>> getBookByFilter(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "Just Id and image can be empty", required = true, type = "BookDTO")@RequestBody BookDTO dto, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);		
		List<Book> listBooks = bookService.getBookByFilter(dto);
		List<BookRepresentation> representationList = BookRepresentation.getListRepresentation(listBooks, request);
		
		return new ResponseEntity<List<BookRepresentation>>(representationList, HttpStatus.OK);
		
	}
	

}
