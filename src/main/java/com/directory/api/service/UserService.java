package com.directory.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.directory.api.model.User;
import com.directory.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;
	
	public String hash(String password,int row) {
        return BCrypt.hashpw(password, BCrypt.gensalt(row));
    }

	public User register(User user) {
		user.setPassword(hash(user.getPassword(), 10));
		return repo.save(user);
	}
	
	public User login(User user, String ip) {
		if(user.getUsername() != null) {
			User entity = repo.findByUsername(user.getUsername());
			entity.setIp(ip);
			repo.save(entity);
			return entity;
		} else {
			User entity = repo.findByEmail(user.getEmail());
			entity.setIp(ip);
			repo.save(entity);
			return entity;
		}
	}

}
