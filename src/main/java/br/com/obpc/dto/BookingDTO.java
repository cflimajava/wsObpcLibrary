package br.com.obpc.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BookingDTO implements Serializable{
	
	private static final long serialVersionUID = -8004415402626742057L;

	private String id;
	
	private Date dateCreation;
	
	private Date pickupDate;
	
	private Date previewDevolutionDate;
	
	private Date devolutionDate;
	
	private String userId;
	
	private List<String> booksId;
	

	public BookingDTO() {
	}
	
	public BookingDTO(Date dateCreation, Date pickupDate, Date previewDevolutionDate, Date devolutionDate, String userId,
			List<String> booksId) {
		this.dateCreation = dateCreation;
		this.pickupDate = pickupDate;
		this.previewDevolutionDate = previewDevolutionDate;
		this.devolutionDate = devolutionDate;
		this.userId = userId;
		this.booksId = booksId;
	}
	
	public BookingDTO(String id, Date dateCreation, Date pickupDate, Date previewDevolutionDate, Date devolutionDate,
			String userId, List<String> booksId) {
		this.id = id;
		this.dateCreation = dateCreation;
		this.pickupDate = pickupDate;
		this.previewDevolutionDate = previewDevolutionDate;
		this.devolutionDate = devolutionDate;
		this.userId = userId;
		this.booksId = booksId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getBooksId() {
		return booksId;
	}

	public void setBooksId(List<String> booksId) {
		this.booksId = booksId;
	}
	
	
}
