package br.com.obpc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.dto.BookingDTO;
import br.com.obpc.entities.Book;
import br.com.obpc.entities.Booking;
import br.com.obpc.exceptions.BookingUnprocessableException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.repository.BookingRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository repository;
	
	@Autowired
	private BookService bookService;
	
	
	public Optional<Booking> createBooking(BookingDTO dto) throws Exception{		
		
		if(dto.getBooksId() == null || dto.getBooksId().isEmpty()) {
			throw new BookingUnprocessableException("List books is not present");
		}
		
		if(dto.getUserId() == null) {
			throw new BookingUnprocessableException("User ID is not present");
		}
		
		List<Book> books = bookService.getBooksByListId(dto.getBooksId());
		Booking newBooking = new Booking(dto, books);
		return Optional.of(repository.save(newBooking));
		
	}
	
	public Optional<Booking> getBookingById(String id){
		return repository.findById(id);
	}
	
	public Optional<Booking> updateBooking(BookingDTO dto) throws Exception{
		
		Optional.ofNullable(getBookingById(dto.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Booking not found")));
		
		List<Book> books = bookService.getBooksByListId(dto.getBooksId());
		Booking booking = new Booking(dto, books);
		
		return Optional.of(repository.save(booking));
		
	}
	
	public List<Booking> getBookingsByUserId(String userId){
		return repository.findBookingByUserId(userId);
	}
	
	public void deleteBooking(String bookingId) throws Exception {
		
		Optional<Booking> optionalBooking = Optional.ofNullable(getBookingById(bookingId)
				.orElseThrow(() -> new ObjectNotFoundException("Booking not found")));
		
		Booking  bookingFound = optionalBooking.get();
		
		if(checkIfDeletionIsAvaliable(bookingFound)) {
			repository.delete(bookingFound);
		}else {
			throw new BookingUnprocessableException("Deletion is not available for this booking, check if all books have been returned");
		}
	}
	
	
	public boolean checkIfDeletionIsAvaliable(Booking booking) {
		
		boolean deletionAvaliable = false;
		
		if(booking.getPickupDate() == null) {
			deletionAvaliable = true;
		}else if(booking.getDevolutionDate() != null) {
			deletionAvaliable = true;
		}		
		
		return deletionAvaliable;
		
	}
	

}
