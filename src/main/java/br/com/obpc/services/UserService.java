package br.com.obpc.services;

import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import br.com.obpc.dto.UserDTO;
import br.com.obpc.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.obpc.entities.User;
import br.com.obpc.repository.UserRepository;
import br.com.obpc.token.JwtTokenHelper;
import br.com.obpc.utils.GenerateHashPasswordUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private MailService emailService;
	
	@Autowired
	private JwtTokenHelper jwtHelper;
	
	public User getLoggin(String username, String password) throws Exception {
		
		checkEmailFormat(username);
		
		Optional<User> user = Optional
				.ofNullable(repo.findByUsernameAndPassword(username, GenerateHashPasswordUtil.encrypt(password))
						.orElseThrow(()->new ForbiddenException()));
		
		User userAuthorized = user.get();	
		
		if(userAuthorized.getActive()) {
			userAuthorized.setToken(jwtHelper.getAccessToken(userAuthorized));		
			updateUser(userAuthorized);
			return userAuthorized;
		}
		
		throw new InactiveUserException();
		
	}
	
	public void doLogout(String userId) throws Exception {
		Optional<User> optinalUser = Optional
				.ofNullable(getUser(userId).orElseThrow(() -> new ObjectNotFoundException("User not found")));
		
		User userFound = optinalUser.get();
		userFound.setToken(null);

		updateUser(userFound);
	}

	public Optional<User> getUser(String id) {
		return repo.findById(id);
	}

	public Optional<User> updatePassword(String id, String newPassword, String oldPassword) throws Exception {
		Optional<User> user = Optional
				.ofNullable(getUser(id).orElseThrow(() -> new ObjectNotFoundException("User not found")));
		
		User userFound = user.get();
		
		if(userFound.getPassword().equals(oldPassword)) {
			userFound.setPassword(GenerateHashPasswordUtil.encrypt(newPassword));
			userFound = repo.save(userFound);
			return Optional.of(userFound);
		}
		
		throw new ForbiddenException("Password is not matching");

	}

	public User createUser(UserDTO userDTO) throws Exception {
		Optional<User> user = repo.findByUsername(userDTO.getUsername());
		
		if(userDTO.getPassword() == null || userDTO.getPassword().isEmpty())
			throw new PasswordNotPresentException();
		
		if (user.isEmpty()) {
			User userSaved = repo.save(new User(userDTO.getUsername(), GenerateHashPasswordUtil.encrypt(userDTO.getPassword()), userDTO.getRole(), userDTO.getActive()));
			userSaved.setToken(jwtHelper.getAccessToken(userSaved));
			userSaved = updateUser(userSaved);

			try {
				emailService.sendTo(userSaved);
			}catch (Exception e){
				System.out.println("Erro ao enviar email, //TODO : criar exception para esse problema");
			}
			return userSaved ;
		}

		throw new UsernameExistingException();
	}

	public User updateUser(User user) throws Exception {
		
		checkEmailFormat(user.getUsername());
		
		Optional<User> userFounded = repo.findByUsername(user.getUsername());

		if (userFounded.isEmpty() || (userFounded.isPresent() && userFounded.get().getId().equals(user.getId())))
			return repo.save(user);

		throw new UsernameExistingException();
	}
	
	public User getUserById(String id) throws Exception {

		Optional<User> user = repo.findById(id); 
		
		if(user.isPresent())
			return user.get();
		
		throw new ObjectNotFoundException("User not found");
	}
	
	
	public void activateUserById(String id) throws Exception{
		User user = getUserById(id);		
		user.setActive(true);
		user.setToken(null);
		repo.save(user);
	}
	
	
	private void checkEmailFormat(String email) throws Exception{		
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException e) {
			throw new InvalidUsernameException();
		}
	}

}
