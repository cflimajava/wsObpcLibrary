package br.com.obpc.dto;

import java.util.List;

public class BookingFilterDTO {	
	
	private List<String> statusList;	
	

	public BookingFilterDTO() {
	}

	public BookingFilterDTO(List<String> statusList) {
		this.statusList = statusList;
	}

	public List<String> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<String> statusList) {
		this.statusList = statusList;
	}	

}
