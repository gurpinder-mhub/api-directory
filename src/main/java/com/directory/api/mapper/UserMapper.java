package com.directory.api.mapper;

import java.util.HashMap;
import java.util.Map;

import com.directory.api.model.User;

public class UserMapper {

	public Map<String, Object> toModel(User entity) {
		Map<String, Object> model = new HashMap<>();
		model.put("email", entity.getEmail());
		model.put("username", entity.getUsername());
		return model;
	}
	
	public Map<String, Object> toLogin(User entity, String token) {
		Map<String, Object> model = new HashMap<>();
		model.put("email", entity.getEmail());
		model.put("username", entity.getUsername());
		model.put("token", token);
		return model;
	}

}
