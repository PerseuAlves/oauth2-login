package io.github.PerseuAlves.oauth2login.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.PerseuAlves.oauth2login.exception.IllegalArgumentException;
import io.github.PerseuAlves.oauth2login.model.UserLogin;
import io.github.PerseuAlves.oauth2login.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<UserLogin> findAll() {
		return repository.findAll();
	}
	
	public Optional<UserLogin> findById(String name) {
		return repository.findById(name);
	}
	
	public void save(UserLogin user) throws IllegalArgumentException {
		repository.save(user);
	}
	
	public void delete(UserLogin user) throws IllegalArgumentException {
		repository.delete(user);
	}
}
