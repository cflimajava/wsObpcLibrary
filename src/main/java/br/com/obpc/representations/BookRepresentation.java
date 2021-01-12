package br.com.obpc.representations;

import static br.com.obpc.utils.ObpcConstants.BASE_URI_BOOK_CRTL;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.obpc.entities.Book;

public class BookRepresentation extends Representation{
	
	
	private String id;

	private String title;

	private String author;

	private String summary;

	private Integer year;

	private String image;

	private String publisher;
	
	private Integer availables;

	public BookRepresentation() {}

	public BookRepresentation(Book entity, HttpServletRequest request) {
		super(request, BASE_URI_BOOK_CRTL+entity.getId());
		this.id = entity.getId();
		this.title = entity.getTitle();
		this.author = entity.getAuthor();
		this.summary = entity.getSummary();
		this.year = entity.getYear();
		this.image = entity.getImage();
		this.publisher = entity.getPublisher();
		this.availables = entity.getAvailables();
	}

	public BookRepresentation(String id, String title, String author, String summary, Integer year, String image,
			String publisher, Integer availables) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.summary = summary;
		this.year = year;
		this.image = image;
		this.publisher = publisher;
		this.availables = availables;
	}

	
	public static List<BookRepresentation> getListRepresentation(List<Book> books, HttpServletRequest request){
		List<BookRepresentation> list = new ArrayList<>();
		
		books.forEach(book ->{
			list.add(new BookRepresentation(book, request));
		});
		
		return list;
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

}
