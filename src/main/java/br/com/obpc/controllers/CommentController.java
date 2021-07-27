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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.obpc.entities.Comment;
import br.com.obpc.representations.CommentRepresentation;
import br.com.obpc.representations.Representation;
import br.com.obpc.services.CommentService;
import br.com.obpc.services.UserService;
import br.com.obpc.token.JwtTokenHelper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	private final JwtTokenHelper jwtHelper;
	
	@Autowired
	public CommentController(CommentService commentService, UserService userService, JwtTokenHelper jwtHelper) {
		this.commentService = commentService;
		this.jwtHelper = jwtHelper;
	}

	
	@ApiOperation(value = "Add comment about a book", notes = "Resource used to save a book comment")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Comment.class)})
	@PostMapping(value="/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentRepresentation> addComment(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token, 
			@ApiParam(value = "CommentDTO", required = true, type = "Comment")@RequestBody Comment newComment, HttpServletRequest request) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		Optional<Comment> comment = commentService.saveComment(newComment);
		CommentRepresentation representation = new CommentRepresentation(comment.get(), request);
		return new ResponseEntity<CommentRepresentation>(representation, HttpStatus.OK);
		
	}
	
	@ApiOperation(value = "Get all comments by bookID", notes = "Resource used to get all comments on database by bookID")
	@ApiResponses({@ApiResponse(code = 200, message = "", response = Comment.class)})
	@GetMapping(value = "/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CommentRepresentation>> getCommentsByBookId(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token, 
			@ApiParam(value = "BookId", required = true, type = "String")@PathVariable(value = "bookId") String bookId, HttpServletRequest request)throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		List<Comment> commentList = commentService.getCommentByBookId(bookId);
		
		List<CommentRepresentation> representation = Representation.getListRepresentation(commentList, request, CommentRepresentation.class);		
		
		return new ResponseEntity<List<CommentRepresentation>>(representation, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete comment by ID", notes = "Resource used to delete a comment by ID")
	@ApiResponses({@ApiResponse(code = 204, message = "", response = Void.class)})
	@DeleteMapping(value="/{commentId}")
	public ResponseEntity<Void> deleteCommentById(
			@ApiParam(value = "RequesterId", required = true, type = "String") @RequestHeader(name = "HEADERS_REQUESTER") String requesterId,
			@ApiParam(value = "Token", required = true, type = "String") @RequestHeader(name = "HEADERS_TOKEN") String token,
			@ApiParam(value = "CommentId", required = true, type = "String")@PathVariable(value = "commentId") String commentId) throws Exception{
		
		jwtHelper.validateToken(token, requesterId);
		
		commentService.deleteComment(commentId);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	
	
	
}
