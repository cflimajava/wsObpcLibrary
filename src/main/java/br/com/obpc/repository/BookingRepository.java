package br.com.obpc.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.obpc.entities.Booking;

public interface BookingRepository extends MongoRepository<Booking, String>{
	
	public List<Booking> findBookingByUserId(String userId);
	
	public List<Booking> findBookingByStatusIn(List<String> statusFilter);

}
