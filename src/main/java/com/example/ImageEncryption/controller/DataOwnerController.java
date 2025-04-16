package com.example.ImageEncryption.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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
import com.example.ImageEncryption.model.DataOwner;
import com.example.ImageEncryption.model.FileUpload;
import com.example.ImageEncryption.service.OwnerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DataOwnerController {

	@Autowired
	private OwnerService owner_ser;

	@GetMapping("/ownerlogin")
	public String ownerLogin() {
		return "DataOwnerLogin";
	}

	@GetMapping("/ownersignup")
	public String ownerSignup() {
		return "DataOwnerRegister";
	}

	@PostMapping("/ownerregister")
	public String register(DataOwner owner, Model mod) {
		if (owner_ser.registerOwner(owner)) {
			return "DataOwnerLogin";
		}
		mod.addAttribute("fail", "fail");
		return "DataOwnerRegister";
	}

	@PostMapping("/ownerlogin")

	public String ownerLogin(DataOwner own, String tabId, HttpSession session, Model mod) {
		DataOwner own2 = owner_ser.login(own);
		if (own2 != null) {
			session.setAttribute(tabId, own2);
			mod.addAttribute("homecontent", "Welcome " + own2.getName());
			return "DataOwnerHomePage";
		}
		return "homepage";
	}

	public static String hashBytes(byte[] data, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		byte[] hashBytes = digest.digest(data);

		// Convert byte array into a hexadecimal string
		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	@GetMapping("/")
	public String uploadPage() {
		return "homepage";
	}

	@PostMapping("/uploadpage")
	public String uploadPage(String tabId, HttpSession session, Model mod) {
		DataOwner own = (DataOwner) session.getAttribute(tabId);
		if (own != null) {

			return "UploadFileForm1";
		} else {
			return "homepage";
		}
	}

	@PostMapping("/upload")

	public String upload(MultipartFile file, Model mod, String tabId, HttpSession session) throws IOException {

		DataOwner owner = (DataOwner) session.getAttribute(tabId);

		if (owner != null) {

			String contentType = file.getContentType();
			if (contentType == null || !contentType.startsWith("image/")) {
				mod.addAttribute("support", 3);
				mod.addAttribute("unsupportcontent", "Only image files (PNG, JPEG, JPG) are allowed.");
				return "UploadFileForm1";
			}

			String hash = "";
			byte[] data_arr = null;
			try {
				hash = hashBytes(file.getBytes(), "SHA-256");
				boolean file_store = owner_ser.storeFile(file, hash, owner);
				if (file_store) {
					mod.addAttribute("support", 1);
					return "UploadFileForm1";
				} else {
					mod.addAttribute("support", 2);
					return "UploadFileForm1";
				}
			} catch (NoSuchAlgorithmException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ByteArrayResource resource = new ByteArrayResource(data_arr);

			// Get the file name (or any other metadata, if available)
			// String fileName = "jac.docx"; // assuming you have a 'fileName' field in
			// FileEntity

			// Set headers to trigger file download

		}
		return "homepage";
	}

	@PostMapping("/uploadbackss")
	public String uploadbacks(String tabId, HttpSession session, Model mod) {
		DataOwner own2 = (DataOwner) session.getAttribute(tabId);
		if (own2 != null) {
			session.setAttribute(tabId, own2);
			mod.addAttribute("homecontent", "Welcome " + own2.getName());
			return "DataOwnerHomePage";
		}
		return "homepage";
	}

	@PostMapping("/requestlist")
	public String requsetList(String tabId, HttpSession session, Model mod) {
		DataOwner own2 = (DataOwner) session.getAttribute(tabId);
		if (own2 != null) {
			List<RequestHandling> req = owner_ser.getRequestList(own2);
			mod.addAttribute("users", req);
			return "RequestedList";
		}

		return "homepage";
	}

	@PostMapping("/requestaccept")
	public String requestAccept(String tabId, int id, HttpSession session, Model mod) {
		DataOwner own2 = (DataOwner) session.getAttribute(tabId);
		if (own2 != null) {
			owner_ser.reqestAccept(own2, id);
			List<RequestHandling> req = owner_ser.getRequestList(own2);
			mod.addAttribute("users", req);
			return "RequestedList";
		}
		return "homepage";
	}

	@PostMapping("/reject")
	public String rejectRequest(String tabId, int id, HttpSession session, Model mod) {
		DataOwner own2 = (DataOwner) session.getAttribute(tabId);
		if (own2 != null) {
			owner_ser.reject(own2, id);
			List<RequestHandling> req = owner_ser.getRequestList(own2);
			mod.addAttribute("users", req);
			return "RequestedList";
		}
		return "homepage";

	}

	@PostMapping("/requestback")
	public String requestBack(String tabId, HttpSession session, Model mod) {
		DataOwner own2 = (DataOwner) session.getAttribute(tabId);
		if (own2 != null) {

			mod.addAttribute("homecontent", "Welcome " + own2.getName());
			return "DataOwnerHomePage";
		}
		return "homepage";
	}

	@PostMapping("/uploadlist")
	public String uploadList(String tabId,HttpSession session,Model mod) {
		DataOwner own2=(DataOwner)	session.getAttribute(tabId);
		
		if(own2!=null) {
			List<FileUpload>  file=owner_ser.uploadList(own2);
			mod.addAttribute("owners",file);
			return "uploadList";
		}
		return "homepage";
	}

	@PostMapping("/ownerlogout")
	public String logout(String tabId, HttpSession session) {
		session.removeAttribute(tabId);
		return "homepage";
	}

	// download image
//	@ResponseBody
//	public ResponseEntity<ByteArrayResource> upload(MultipartFile file,Model mod,) throws IOException {
//		String hash="";
//		byte [] data_arr=null;
//		try {
//			 hash = hashBytes(file.getBytes(), "SHA-256");
//			data_arr= owner_ser.storeFile(file,hash);
//		} catch (NoSuchAlgorithmException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		ByteArrayResource resource = new ByteArrayResource(data_arr);
//
//		// Get the file name (or any other metadata, if available)
//		// String fileName = "jac.docx"; // assuming you have a 'fileName' field in
//		// FileEntity
//
//		// Set headers to trigger file download
//		HttpHeaders headers = new HttpHeaders();
//		// headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
//		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalFilename());
//		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); // You can specify the MIME type here if
//																			// known
//
//		// Return the file as a response entity
//		return ResponseEntity.ok().headers(headers).contentLength(resource.contentLength()).body(resource);
//	}

}
