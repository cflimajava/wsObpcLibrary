package br.com.obpc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaTests {

	public static void main(String[] args) {

		List<Customer> asList = Arrays.asList(new Customer(1, "customer um"), new Customer(2, "customer um"), new Customer(3, "customer tres"));
		
		filtrarLista(asList).stream().forEach(id -> System.out.println("ID: "+id));

	}
	
	public static List<Integer> filtrarLista(List<Customer> customers) {
		
		return customers.stream().filter(c -> c.getName().equals("customer tres"))
			.map(c -> c.getId() )
			.collect(Collectors.toList());
	}

	
	
}



