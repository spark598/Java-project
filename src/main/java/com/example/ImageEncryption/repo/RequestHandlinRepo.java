package com.example.ImageEncryption.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ImageEncryption.dto.RequestHandling;

public interface RequestHandlinRepo extends JpaRepository<RequestHandling, Integer>{

	List<RequestHandling> findByUserId(int id);

	List<RequestHandling> findByOwnerId(int id);

	List<RequestHandling> findByUserIdAndStatus(int id,String status);

}
