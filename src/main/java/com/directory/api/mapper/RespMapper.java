package com.directory.api.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RespMapper {

	public ResponseEntity<Object> toError(String message) {
		Map<String, Object> response = new HashMap<>();
    	response.put("isSuccess", false);
    	response.put("message", message);
    	return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}
	
	public Map<String, Object> toValidationError(String message) {
		Map<String, Object> response = new HashMap<>();
    	response.put("isSuccess", false);
    	response.put("message", message);
    	return response;
	}
	
	public ResponseEntity<Object> toSuccess(Object entity) {
		Map<String, Object> response = new HashMap<>();
    	response.put("isSuccess", true);
    	response.put("data", entity);
    	return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
