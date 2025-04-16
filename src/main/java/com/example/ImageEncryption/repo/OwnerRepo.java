package com.example.ImageEncryption.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ImageEncryption.model.DataOwner;

public interface OwnerRepo extends JpaRepository<DataOwner,Integer>{

	DataOwner findByEmail(String email);

	DataOwner findByEmailAndPassword(String email, String password);

}
