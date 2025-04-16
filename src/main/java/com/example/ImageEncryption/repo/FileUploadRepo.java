package com.example.ImageEncryption.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ImageEncryption.model.FileUpload;

public interface FileUploadRepo extends JpaRepository<FileUpload, Integer> {

	FileUpload findByHash(String hash);

	FileUpload findByHashAndEmail(String hash, String email);

	List<FileUpload> findAllByIdNotIn(List<Integer> ids);

	List<FileUpload> findByDataownerid(int id);

	
	

}
