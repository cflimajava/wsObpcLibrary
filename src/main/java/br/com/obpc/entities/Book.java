package br.com.obpc.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.obpc.dto.BookDTO;

@Document
public class Book implements Serializable {

	private static final long serialVersionUID = -8016496235512694238L;

	@Id
	private String id;

	private String title;

	private String author;

	private String summary;

	private Integer year;

	private String image;

	private String publisher;
	
	private Integer availables;

	public Book() {
	}	

	public Book(BookDTO dto) {
		this.id = dto.getId() != null ? dto.getId() : null;
		this.title = dto.getTitle();
		this.author = dto.getAuthor();
		this.summary = dto.getSummary();
		this.year = dto.getYear();
		this.image = dto.getImage();
		this.publisher = dto.getPublisher();
		this.availables = dto.getAvailables();
	}

	public Book(String id, String title, String author, String summary, Integer year, String image, String publisher, Integer availables) {
		this.id = id != null ? id : null;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.year = year;
		this.image = image;
		this.publisher = publisher;
		this.availables = availables;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Integer getAvailables() {
		return availables;
	}

	public void setAvailables(Integer availables) {
		this.availables = availables;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	

}
