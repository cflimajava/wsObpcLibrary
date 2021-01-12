package br.com.obpc.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Customer implements Serializable{

	private static final long serialVersionUID = -8368938301821156016L;
	
	@Id
	private String id;
	
	private String name;
	
	private Date dateBirth;
	
	private String generalRegister;
	
	private String cpf;
	
	private String street;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private String cellPhoneNumber;
	
	private String homePhoneNumber;
	
	private String extraPhoneNumber;
	
	private String userId;

	public Customer() {
	}


	public Customer(String id, String name, Date dateBirth, String generalRegister, String cpf, String street,
			String neighborhood, String city, String state, String zipCode, String cellPhoneNumber,
			String homePhoneNumber, String extraPhoneNumber, String userId) {
		super();
		this.id = id;
		this.name = name;
		this.dateBirth = dateBirth;
		this.generalRegister = generalRegister;
		this.cpf = cpf;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.cellPhoneNumber = cellPhoneNumber;
		this.homePhoneNumber = homePhoneNumber;
		this.extraPhoneNumber = extraPhoneNumber;
		this.userId = userId;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	public String getGeneralRegister() {
		return generalRegister;
	}

	public void setGeneralRegister(String generalRegister) {
		this.generalRegister = generalRegister;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getExtraPhoneNumber() {
		return extraPhoneNumber;
	}

	public void setExtraPhoneNumber(String extraPhoneNumber) {
		this.extraPhoneNumber = extraPhoneNumber;
	}

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
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
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
}
