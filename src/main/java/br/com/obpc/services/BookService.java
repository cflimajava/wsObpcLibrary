package br.com.obpc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.dto.BookDTO;
import br.com.obpc.entities.Book;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;
	
	
	public Optional<Book> getBookById(String id){
		return repository.findById(id);
	}
	
	public Optional<Book> updateBook(BookDTO dto) throws ObjectNotFoundException{	
		if(dto.getId() != null) {
			getBookById(dto.getId()).orElseThrow(() -> new ObjectNotFoundException("Book not found"));		
			Book book = new Book(dto);
			return Optional.of(repository.save(book));
		}
		
		throw new ObjectNotFoundException("Book ID is not present, check if book already resgistered");		
	}
	
	public void deleteBook(String id) throws ObjectNotFoundException {
		getBookById(id).orElseThrow(() -> new ObjectNotFoundException("Book not found"));	
		repository.deleteById(id);
	}
	
	public Optional<Book> saveBook(BookDTO dto){
		Book book = new Book(dto);
		return Optional.of(repository.save(book));
	}
	
	public List<Book> getBookByTitle(String title){
		return repository.findBookByTitleContainsIgnoringCaseAndAvailablesGreaterThan(title, 0);
	}
	
	public List<Book> getAllBooks(){
		return repository.findAll();
	}
	
	
	public List<Book> getBookByFilter(BookDTO filter){
		
		String publisher = (filter.getPublisher() != null && !filter.getPublisher().isBlank())? filter.getPublisher() : " ";
		String author = (filter.getAuthor() != null && !filter.getAuthor().isBlank()) ? filter.getAuthor() : " ";
		
		return repository.findBookByPublisherStartingWithIgnoringCaseOrAuthorStartingWithIgnoringCaseAndAvailablesGreaterThan(publisher, author, 0);
	}
	
	
	public List<Book> getBooksByListId(List<String> ids){
		List<Book> books = new ArrayList<>();
		
		ids.forEach(id -> {	
			Book book = repository.findById(id).get();
			books.add(book);
		});
		
		return books;
		
	}
	
	
}
