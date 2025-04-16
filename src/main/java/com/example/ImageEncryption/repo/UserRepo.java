package com.example.ImageEncryption.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ImageEncryption.model.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	User findByEmailAndPassword(String email, String password);

}
