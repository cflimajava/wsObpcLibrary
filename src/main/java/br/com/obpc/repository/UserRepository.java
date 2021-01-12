package br.com.obpc.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.obpc.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public Optional<User> findByUsernameAndPassword(String username, String password);
	
	public Optional<User> findByUsername(String username);
}
