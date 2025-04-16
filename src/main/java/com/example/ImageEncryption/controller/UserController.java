package com.example.ImageEncryption.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.ImageEncryption.dto.RequestHandling;
import com.example.ImageEncryption.model.FileUpload;
import com.example.ImageEncryption.model.User;
import com.example.ImageEncryption.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	@Autowired
	private UserService user_ser;
	
	@GetMapping("/userloginpage")
	public String userloginpage() {
		return "userLogin";
	}
	
	@GetMapping("/usersignuppage")
	public String usersignuppage() {
		return "userRegister";
	}
	
	@PostMapping("/userregister")
	public String userRegister(User user,Model mod)
	{
		User user2=user_ser.registerUser(user);
		if(user2!=null) {
			return "userLogin";
		}
		mod.addAttribute("fail", "fail");
		return "userRegister";
	}
	
	@PostMapping("/userlogin")
	public String userlogin(User user,HttpSession session,Model mod,String tabId) {
		User user2=user_ser.userLogin(user);
		if(user2!=null) {
			session.setAttribute(tabId, user2);
			mod.addAttribute("homecontent", "Welcome "+user2.getName());
			return "UserHomePage";
		}
		mod.addAttribute("fail", "fail");
		return "userLogin";
	}
	
	@PostMapping("/imagefiles")
	
	public String imageFiles(String tabId,HttpSession session,Model mod) {
		User user =(User)session.getAttribute(tabId);
		if(user!=null) {
			List<FileUpload > files=user_ser.getImageFile(user);
			Collections.reverse(files);
			mod.addAttribute("owners", files);
			return "ImageFileList";
		}
		return "homepage";
		
	}
	
	@PostMapping("/requestforimage")
	public String requestForImage(String tabId,int id,HttpSession session,Model mod) {
		User user =(User)session.getAttribute(tabId);
		
		if(user!=null) {
			user_ser.requestImage(user,id);
			List<FileUpload > files=user_ser.getImageFile(user);
			mod.addAttribute("owners", files);
			return "ImageFileList";
		}
		return "homepage";
		
		
	}
	
	@PostMapping("/decryptimagelist")
	public String decryptImage(String tabId,HttpSession session,Model mod) {
		User user=(User) session.getAttribute(tabId);
		if(user!=null) {
			List<RequestHandling> req=user_ser.decryptImageList(user);
			mod.addAttribute("owners", req);
			return "DecryptImageFileList";
		}
		return  "homepage";
	}
	
	
	
	@PostMapping("/prevback")
	public String prevBack(String tabId,HttpSession session,Model mod) {
		User user =(User)session.getAttribute(tabId);
		if(user!=null) {
			mod.addAttribute("homecontent", "Welcome "+user.getName());
			return "UserHomePage";
		}
		return "homepage";
	}
	
	@PostMapping("/decryptpage")
	public String decryptionPage(String fileName,Model mod,String tabId,HttpSession session,int fileid) {
		User user =(User)session.getAttribute(tabId);
		if(user!=null) {
			Optional<FileUpload> file=user_ser.getSecretKey(fileid);
			if(file.get()!=null) {
				mod.addAttribute("secretKey", file.get().getSecretkey());
				mod.addAttribute("fileName", file.get().getFilename());
				mod.addAttribute("fileId", file.get().getId());
				return "DecryptionImage";
			}
			else {
				return "homepage";
			}
		}
		return "homepage";

	}
	
	@PostMapping("/decryptimages")
	public ResponseEntity<ByteArrayResource> download(String fileName,Model mod,String tabId,HttpSession session,String secretkey,int fileid){
		User user =(User)session.getAttribute(tabId);
		System.out.println(" hi");
		if(user!=null) {
			System.out.println(" hello "+fileid+ " sc "+secretkey);
			byte [] data_arr=null;
			data_arr=user_ser.getImage(user, fileid,secretkey);
			ByteArrayResource resource = new ByteArrayResource(data_arr);

			// Get the file name (or any other metadata, if available)
			// String fileName = "jac.docx"; // assuming you have a 'fileName' field in
			// FileEntity

			// Set headers to trigger file download
			HttpHeaders headers = new HttpHeaders();
			// headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); // You can specify the MIME type here if
																				// known

			// Return the file as a response entity
			return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).body(resource);
			
		}
		return ResponseEntity.internalServerError().body(null);

		
		
	}
	
	@PostMapping("/decryptimage")
	@ResponseBody
	public ResponseEntity<ByteArrayResource> upload(String fileName,Model mod,String tabId,HttpSession session,int fileid) throws IOException {
		User user =(User)session.getAttribute(tabId);
		if(user!=null) {
			byte [] data_arr=null;
		data_arr=user_ser.getImageData(user,fileid);
			ByteArrayResource resource = new ByteArrayResource(data_arr);

			// Get the file name (or any other metadata, if available)
			// String fileName = "jac.docx"; // assuming you have a 'fileName' field in
			// FileEntity

			// Set headers to trigger file download
			HttpHeaders headers = new HttpHeaders();
			// headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
			headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); // You can specify the MIME type here if
																				// known

			// Return the file as a response entity
			return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).body(resource);
		}
		String hash="";
		return ResponseEntity.internalServerError().body(null);
		
		
	}
	
	
	
}
