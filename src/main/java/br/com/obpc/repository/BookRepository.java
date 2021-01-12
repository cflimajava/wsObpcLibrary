package br.com.obpc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.obpc.entities.Book;

public interface BookRepository extends MongoRepository<Book, String> {

	public List<Book> findBookByTitleContainsIgnoringCaseAndAvailablesGreaterThan(String title, Integer moreThan);
	
	public List<Book> findBookByPublisherStartingWithIgnoringCaseOrAuthorStartingWithIgnoringCaseAndAvailablesGreaterThan(String publisher, String author, Integer moreThan);
	
}
