package br.com.obpc.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.obpc.dto.UserDTO;

@Document
public class Comment implements Serializable{

	private static final long serialVersionUID = -995264280302189751L;

	@Id
	private String id;
	
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:sss.sss")
	private Date dateCreation;
	
	private String text;
	
	private String bookId;
	
	private UserDTO user;
	
	
	public Comment() {	}
	
	public Comment(String id, Date dateCreation, String text, String bookId, UserDTO user) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.text = text;
		this.bookId = bookId;
		this.user = user;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}	
	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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
