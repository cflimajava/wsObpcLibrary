package br.com.obpc.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import br.com.obpc.dto.BookingDTO;

@Document
public class Booking implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NonNull
	private Date dateCreation;
	
	private Date pickupDate;
	
	private Date previewDevolutionDate;
	
	private Date devolutionDate;

	@NonNull
	private String status;
	
	@NonNull
	private String userId;
	
	@NonNull
	private String userName;
	
	@NonNull
	private List<Book> books;
	
	private String notes;

	public Booking() {
	}

	public Booking(String id, Date dateCreation, Date pickupDate, Date previewDevolutionDate, Date devolutionDate, String status,
			String userId, String userName, List<Book> books, String notes) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.pickupDate = pickupDate;
		this.previewDevolutionDate = previewDevolutionDate;
		this.devolutionDate = devolutionDate;
		this.status = status;
		this.userId = userId;
		this.userName = userName;
		this.books = books;
		this.notes = notes;
	}

	public Booking(BookingDTO dto, List<Book> books) {
		this.id = dto.getId() != null ? dto.getId() : null;
		this.dateCreation =dto.getDateCreation();
		this.pickupDate = dto.getPickupDate();
		this.previewDevolutionDate = dto.getPreviewDevolutionDate();
		this.devolutionDate = dto.getDevolutionDate();
		this.status = dto.getStatus();
		this.userId = dto.getUserId();
		this.userName = dto.getUserName();
		this.books = books;
		this.notes = dto.getNotes();
	}
	
	public Booking(BookingDTO dto) {
		this.id = dto.getId();
		this.dateCreation = dto.getDateCreation();
		this.pickupDate = dto.getPickupDate();
		this.previewDevolutionDate = dto.getPreviewDevolutionDate();
		this.devolutionDate = dto.getDevolutionDate();
		this.userId = dto.getUserId();
		this.userName = dto.getUserName();
		this.notes = dto.getNotes();
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

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Date getPreviewDevolutionDate() {
		return previewDevolutionDate;
	}

	public void setPreviewDevolutionDate(Date previewDevolutionDate) {
		this.previewDevolutionDate = previewDevolutionDate;
	}

	public Date getDevolutionDate() {
		return devolutionDate;
	}

	public void setDevolutionDate(Date devolutionDate) {
		this.devolutionDate = devolutionDate;
	}

	@NonNull
	public String getStatus() {
		return status;
	}

	public void setStatus(@NonNull String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
