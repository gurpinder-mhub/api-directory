package com.directory.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.directory.api.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
	User findByEmail(String email);
}
