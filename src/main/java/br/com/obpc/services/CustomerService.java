package br.com.obpc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.entities.Booking;
import br.com.obpc.entities.Customer;
import br.com.obpc.exceptions.CustomerUnprocessableException;
import br.com.obpc.exceptions.ObjectNotFoundException;
import br.com.obpc.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private BookingService bookingService;
	
	public Optional<Customer> createCustomer(Customer customer) throws Exception{
		
		Optional<Customer> customerFound = repo.findCustomerByUserId(customer.getUserId());
		
		if(customerFound.isEmpty()) {
			return Optional.of(repo.save(customer));
		}else {
			throw new CustomerUnprocessableException("User ID informed is already resgistered to customer ID: "+customerFound.get().getId());
		}
		
	}
	
	
	public Optional<Customer> getCustomerByUserId(String userId) throws Exception {		
		return repo.findCustomerByUserId(userId);		 
	}
	
	public Optional<Customer> getCustomerById(String customerId) {	
		return repo.findById(customerId);
	}
	
	public Optional<Customer> updateCustomer(Customer newCustomerData) throws Exception{
		
		repo.findById(newCustomerData.getId())
			.orElseThrow(() -> new ObjectNotFoundException("Customer not found"));
		
		return Optional.of(repo.save(newCustomerData));
		
	}
	
	public void deleteCustomer(Customer customer) throws Exception {
		
		List<Booking> bookingList = bookingService.getBookingsByUserId(customer.getUserId());
		
		for (Booking booking : bookingList) {
			if(booking.getPickupDate() != null && booking.getDevolutionDate() == null){
				throw new CustomerUnprocessableException("Customer with booking not returned.");
			}
		}
		
		repo.delete(customer);
		
	}
	
}
