package com.example.ImageEncryption.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ImageEncryption.dto.EncryptionUtil;
import com.example.ImageEncryption.dto.RequestHandling;
import com.example.ImageEncryption.model.DataOwner;
import com.example.ImageEncryption.model.FileUpload;
import com.example.ImageEncryption.repo.FileUploadRepo;
import com.example.ImageEncryption.repo.OwnerRepo;
import com.example.ImageEncryption.repo.RequestHandlinRepo;

@Service
public class OwnerService {
	@Autowired
	private FileUploadRepo file_repo;
	
	@Autowired
	private OwnerRepo owner_repo;
	@Autowired
	private RequestHandlinRepo request_repo;

	public boolean storeFile(MultipartFile file,String hash,DataOwner owner) {
		FileUpload exit_file=file_repo.findByHashAndEmail(hash,owner.getEmail());
		
		if(exit_file==null) {
		
		try {
			
			SecretKey key=EncryptionUtil.generateAESKey();
			
			String str_key=EncryptionUtil.secretKeyToString(key);
			
			byte [] encrypt_file=EncryptionUtil.encrypt(file.getBytes(), key);
			
			FileUpload file_upload=new FileUpload();
			file_upload.setImage(encrypt_file);
			file_upload.setSecretkey(str_key);
			file_upload.setHash(hash);
			file_upload.setFilename(file.getOriginalFilename());
			file_upload.setDataownerid(owner.getId());
			file_upload.setName(owner.getName());
			file_upload.setEmail(owner.getEmail());
			file_repo.save(file_upload);
			encrypt_file=	EncryptionUtil.decrypt(encrypt_file, key);
			
			return true;
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		}
		return false;
		
	}

	public boolean registerOwner(DataOwner owner) {
		
		DataOwner owner2 = owner_repo.findByEmail(owner.getEmail());
		if (owner2 == null) {
			try {
			owner_repo.save(owner);
			return true;
			}catch(Exception e) {
				return false;
			}
			
		}
		return false;
	}

	public DataOwner login(DataOwner own) {

	DataOwner own2=	owner_repo.findByEmailAndPassword(own.getEmail(),own.getPassword());
	if(own2!=null) {
		return own2;
	}
	else {
		return null;
	}
		
	}

	public List<RequestHandling> getRequestList(DataOwner own2) {
		
		List<RequestHandling> req=request_repo.findByOwnerId(own2.getId());
	req=req.stream().filter(d->{
			if(d.getStatus().equals("0"))
				return true;
			return false;
		}).collect(Collectors.toList());
	System.err.println(" req "+req);
	
		Collections.reverse(req);
		
		return req;
		
	}

	public void reqestAccept(DataOwner own2, int id) {
		Optional<RequestHandling> req = request_repo.findById(id);
		req.get().setStatus("1");
		request_repo.save(req.get());
	}

	public void reject(DataOwner own2, int id) {
		Optional<RequestHandling> req = request_repo.findById(id);
		req.get().setStatus("2");
		request_repo.save(req.get());
		
	}

	public List<FileUpload>  uploadList(DataOwner own2) {
		List<FileUpload> file=	file_repo.findByDataownerid(own2.getId());
		Collections.reverse(file);
		return file;
	}
	
	

}
