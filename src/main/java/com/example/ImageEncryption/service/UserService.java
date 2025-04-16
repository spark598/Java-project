package com.example.ImageEncryption.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ImageEncryption.dto.EncryptionUtil;
import com.example.ImageEncryption.dto.RequestHandling;
import com.example.ImageEncryption.model.FileUpload;
import com.example.ImageEncryption.model.User;
import com.example.ImageEncryption.repo.FileUploadRepo;
import com.example.ImageEncryption.repo.RequestHandlinRepo;
import com.example.ImageEncryption.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo user_repo;
	@Autowired
	private FileUploadRepo file_repo;
	@Autowired
	private RequestHandlinRepo request_repo;
	
	
	public User registerUser(User user) {
		User user2=user_repo.findByEmail(user.getEmail());
		if(user2==null) {
			try {
			user_repo.save(user);
			return user;
			}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
		
	}


	public User userLogin(User user) {
		
	User user2=	user_repo.findByEmailAndPassword(user.getEmail(),user.getPassword());
	if(user2!=null) {
		return user2;
	}
	return null;
		
	}

	public List<FileUpload> getImageFile(User user) {
		List<RequestHandling> req=request_repo.findByUserId(user.getId());
	List<Integer> ids=	req.stream().map(d->{
			return d.getFileId();
		}).collect(Collectors.toList());
	
	List<FileUpload> files;
	if(ids.size()>0) {
		files = file_repo.findAllByIdNotIn(ids);
	}else {
		files = file_repo.findAll();
	}
		
		
		return files;
	}


	public void requestImage(User user,int id) {
	Optional<FileUpload> file=	file_repo.findById(id);
	RequestHandling req=new RequestHandling();
	req.setFileId(id);
	req.setUserId(user.getId());
	req.setOwnerEmail(file.get().getEmail());
	req.setFileName(file.get().getFilename());
	req.setStatus("0");
	req.setOwnerName(file.get().getName());
	req.setUserName(user.getName());
	req.setUserEmail(user.getEmail());
	req.setOwnerId(file.get().getDataownerid());
	request_repo.save(req);
	
		
	}


	public List<RequestHandling> decryptImageList(User user) {
		List<RequestHandling> req = request_repo.findByUserIdAndStatus(user.getId(),"1");
		Collections.reverse(req);
		return req;
	}


	public byte[] getImageData(User user, int fileid) {
		Optional<FileUpload> file = file_repo.findById(fileid);

		SecretKey secret_key = EncryptionUtil.stringToSecretKey(file.get().getSecretkey(), "AES");
		try {
			byte[] decrypt_data = EncryptionUtil.decrypt(file.get().getImage(), secret_key);
			return decrypt_data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public Optional<FileUpload> getSecretKey(int fileid) {
		// TODO Auto-generated method stub
		Optional<FileUpload> file = file_repo.findById(fileid);
		return file;
		
	}


	public byte[] getImage(User user, int fileid, String secretkey) {
		Optional<FileUpload> file = file_repo.findById(fileid);
		System.out.println(file.get().getFilename());
		if(secretkey.equals(file.get().getSecretkey())){
			System.out.println(" sss ");
			SecretKey secret_key = EncryptionUtil.stringToSecretKey(file.get().getSecretkey(), "AES");
			try {
				byte[] decrypt_data = EncryptionUtil.decrypt(file.get().getImage(), secret_key);
				return decrypt_data;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return file.get().getImage();
			}
			
		}
		else {
			return file.get().getImage();
		}
		
	}

	
}
