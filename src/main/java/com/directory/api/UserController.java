package com.directory.api;

import java.util.Date;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import com.directory.api.mapper.RespMapper;
import com.directory.api.mapper.UserMapper;
import com.directory.api.model.User;
import com.directory.api.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

	@Autowired
	private UserService service;

	@PostMapping("register")
	public ResponseEntity<Object> register(@RequestBody User user, HttpServletRequest request) {
		RespMapper respMapper = new RespMapper();
		if (user.getEmail() == null || user.getUsername() == null) {
			return respMapper.toError("Username or email is required");
		}
		if (user.getPassword() == null) {
			return respMapper.toError("Password is required");
		}
		user.setIp(request.getRemoteAddr());
		User entity = service.register(user);
		if (entity == null) {
			return respMapper.toError("Unable to create user");
		} else {
			UserMapper mapper = new UserMapper();
			return respMapper.toSuccess(mapper.toModel(entity));
		}
	}

	@PostMapping("login")
	public ResponseEntity<Object> login(@RequestBody User user, HttpServletRequest request) {
		User entity = service.login(user, request.getRemoteAddr());
		RespMapper respMapper = new RespMapper();
		if (entity == null) {
			return respMapper.toError("User not found");
		}
		if (BCrypt.checkpw(user.getPassword(), entity.getPassword())) {
			UserMapper mapper = new UserMapper();
			String token = getJWTToken(user.getUsername());
			return respMapper.toSuccess(mapper.toLogin(entity, token));
		} else {
			return respMapper.toError("Invalid Password");
		}
	}
	
	private String getJWTToken(String username) {
		String secretKey = "17-05-2021";
		
		String token = Jwts
				.builder()
				.setId(username)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 3000000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}
	
}
