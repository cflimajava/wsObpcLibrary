package br.com.obpc.representations;

import static br.com.obpc.utils.ObpcConstants.BASE_URI_BOOK_CRTL;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.com.obpc.dto.UserDTO;
import br.com.obpc.entities.Comment;

public class CommentRepresentation  extends Representation{
		
	private String id;
	
	private String text;
	
	private Date dateCreation;
	
	private String bookId;
	
	private UserDTO user;
		
	public CommentRepresentation() {}
	
	public CommentRepresentation(Comment entity, HttpServletRequest request) {
		super(request, BASE_URI_BOOK_CRTL+entity.getId());
		this.id = entity.getId();
		this.dateCreation = entity.getDateCreation();
		this.text = entity.getText();
		this.bookId = entity.getBookId();
		this.user = entity.getUser();
	}

	public CommentRepresentation(String id, Date dateCreation, String text, String bookId) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.text = text;
		this.bookId = bookId;
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}	

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
	
	

}
