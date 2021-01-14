package br.com.obpc.representations;

import static br.com.obpc.utils.ObpcConstants.BASE_URI_CUSTOMER_CRTL;

import javax.servlet.http.HttpServletRequest;

import br.com.obpc.entities.Customer;

public class CustomerRepresentation extends Representation{

	private String id;
	
	private String name;
	
	private String dateBirth;
	
	private String generalRegister;
	
	private String cpf;
	
	private String street;
	
	private Integer number;
	
	private String complement;
	
	private String neighborhood;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private String cellPhoneNumber;
	
	private String homePhoneNumber;
	
	private String extraPhoneNumber;
	
	private String userId;

	public CustomerRepresentation() {}
	
	public CustomerRepresentation(Customer entity, HttpServletRequest request) {
		super(request, BASE_URI_CUSTOMER_CRTL+entity.getId());
		this.id = entity.getId();
		this.name = entity.getName();
		this.dateBirth = entity.getDateBirth();
		this.generalRegister = entity.getGeneralRegister();
		this.cpf = entity.getCpf();
		this.street = entity.getStreet();
		this.number = entity.getNumber();
		this.complement = entity.getComplement();
		this.neighborhood = entity.getNeighborhood();
		this.city = entity.getCity();
		this.state = entity.getState();
		this.zipCode = entity.getZipCode();
		this.cellPhoneNumber = entity.getCellPhoneNumber();
		this.homePhoneNumber = entity.getHomePhoneNumber();
		this.extraPhoneNumber = entity.getExtraPhoneNumber();
		this.userId = entity.getUserId();
	}

	public CustomerRepresentation(String id, String name, String dateBirth, String generalRegister, String cpf,
			String street, Integer number, String complement, String neighborhood, String city, String state,
			String zipCode, String cellPhoneNumber, String homePhoneNumber, String extraPhoneNumber, String userId) {
		this.id = id;
		this.name = name;
		this.dateBirth = dateBirth;
		this.generalRegister = generalRegister;
		this.cpf = cpf;
		this.street = street;
		this.number = number;
		this.complement = complement;
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

	public String getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(String dateBirth) {
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}
	
}
