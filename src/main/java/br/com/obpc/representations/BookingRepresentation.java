package br.com.obpc.representations;

import static br.com.obpc.utils.ObpcConstants.BASE_URI_BOOKING_CRTL;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.obpc.entities.Book;
import br.com.obpc.entities.Booking;

public class BookingRepresentation extends Representation {
	
	private String id;
	
	private Date dateCreation;
	
	private Date pickupDate;
	
	private Date previewDevolutionDate;
	
	private Date devolutionDate;

	private String status;
	
	private String userId;
	
	private List<Book> books;
	
	public BookingRepresentation() {}
	

	public BookingRepresentation(Booking entity, HttpServletRequest request) {
		super(request, BASE_URI_BOOKING_CRTL+entity.getId());
		this.id = entity.getId();
		this.dateCreation = entity.getDateCreation();
		this.pickupDate = entity.getPickupDate();
		this.previewDevolutionDate = entity.getPreviewDevolutionDate();
		this.devolutionDate = entity.getDevolutionDate();
		this.status = entity.getStatus();
		this.userId = entity.getUserId();
		this.books = entity.getBooks();
	}

	public BookingRepresentation(String id, Date dateCreation, Date pickupDate, Date previewDevolutionDate,
			Date devolutionDate, String status, String userId, List<Book> books) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.pickupDate = pickupDate;
		this.previewDevolutionDate = previewDevolutionDate;
		this.devolutionDate = devolutionDate;
		this.status = status;
		this.userId = userId;
		this.books = books;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
	
}
