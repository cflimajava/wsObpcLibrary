package br.com.obpc.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.dto.BookingDTO;
import br.com.obpc.dto.BookingFilterDTO;
import br.com.obpc.entities.Book;
import br.com.obpc.entities.Booking;
import br.com.obpc.enums.StatusBooking;
import br.com.obpc.exceptions.BookingUnprocessableException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.repository.BookingRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository repository;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CustomerService customerService;
	
	private final int SIZE_LOAN_DEFAULT = 10;
	
	
	public Optional<Booking> createBooking(BookingDTO dto) throws Exception{		
		
		if(dto.getBooksId() == null || dto.getBooksId().isEmpty()) {
			throw new BookingUnprocessableException("List books is not present");
		}
		
		if(dto.getUserId() == null) {
			throw new BookingUnprocessableException("User ID is not present");
		}
		
		List<Book> books = bookService.getBooksByListId(dto.getBooksId());		
		dto.setDateCreation(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		dto.setStatus(StatusBooking.PENDENTE.getDescricao());
		Booking newBooking = new Booking(dto, books);
		return Optional.of(repository.save(newBooking));
		
	}
	
	
	public Optional<Booking>  registerPickUp(BookingDTO dto) throws Exception{
		
		checkPersonalDataIsRegistred(dto.getUserId());
			
		LocalDateTime now = LocalDateTime.now();
		Date pickUpDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());		
		Date previewDevolutionDate = Date.from(
				now.plusDays(dto.getSizeLoan() != null ? dto.getSizeLoan() : SIZE_LOAN_DEFAULT)
				.atZone(ZoneId.systemDefault()).toInstant());

		dto.setPickupDate(pickUpDate);
		dto.setPreviewDevolutionDate(previewDevolutionDate);
		dto.setStatus(StatusBooking.ATIVO.getDescricao());
		
		return this.updateBooking(dto);
	}
	
	private void checkPersonalDataIsRegistred(String userId) throws Exception {		
		customerService.getCustomerByUserId(userId)
			.orElseThrow( () ->  new ObjectNotFoundException("Customer data not present for user informed"));		
	}
	
	public Optional<Booking> registerDevolution(BookingDTO dto) throws Exception{
		
		getBookingById(dto.getId()).orElseThrow(() -> new ObjectNotFoundException("Booking not found"));		
		dto.setDevolutionDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		dto.setStatus(StatusBooking.FINALIZADO.getDescricao());
		
		return this.updateBooking(dto);		
	}
	
	
	
	public Optional<Booking> getBookingById(String id){
		return repository.findById(id);
	}
	
	public Optional<Booking> updateBooking(BookingDTO dto) throws Exception{
		
		getBookingById(dto.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Booking not found"));
		
		List<Book> books = bookService.getBooksByListId(dto.getBooksId());
		Booking booking = new Booking(dto, books);
		
		return Optional.of(repository.save(booking));
		
	}
	
	public Optional<Booking> updateBookingStatus(String bookingId, String newStatus) throws Exception{
		
		Booking booking = getBookingById(bookingId)
				.orElseThrow(() -> new ObjectNotFoundException("Booking not found"));
		
		booking.setStatus(newStatus);
		
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
	
	public List<Booking> getBookingByFilter(BookingFilterDTO filter) {		
		return repository.findBookingByStatusIn(filter.getStatusList());		
	}
	
	public List<Booking> getAllBookings(){
		return repository.findAll();
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
