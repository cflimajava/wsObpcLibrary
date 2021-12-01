package br.com.obpc.aspect.monitoring;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.obpc.dto.BookingFilterDTO;
import br.com.obpc.entities.Booking;
import br.com.obpc.enums.StatusBooking;
import br.com.obpc.services.BookingService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BookingMonitorStatus {
	
	private final BookingService bookingService;

	@Autowired
	public BookingMonitorStatus(BookingService bookingService) {
		this.bookingService = bookingService;
	}




	@Scheduled(cron = "59 0 0 * * *")
	public void checkAllActiveBooking() {
		log.info("Checking delay booking: "+ LocalDateTime.now().toString());
		
		List<Booking> activeBooking = bookingService.getBookingByFilter(new BookingFilterDTO(Arrays.asList(StatusBooking.ATIVO.getDescricao())));
		
		log.info("Number of active booking: "+activeBooking.size());
		
		activeBooking.stream()
			.filter(b -> 	b.getPreviewDevolutionDate().before(new Date()))
				.forEach(b ->{
					try {
						bookingService.updateBookingStatus(b.getId(), StatusBooking.ATRASADO.getDescricao());
						log.info("Updating booking status to "+StatusBooking.ATRASADO.getDescricao()+" ID: "+b.getId());
					} catch (Exception e) {
						log.error(e.getMessage());
						e.printStackTrace();
					}
				});
		
		log.info("Devolution delays checked");
		
	}
	
}
