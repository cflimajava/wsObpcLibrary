package br.com.obpc.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.obpc.entities.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String>{

	public Optional<Customer> findCustomerByUserId(String userId);
}
