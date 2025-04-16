package com.example.ImageEncryption.model;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class FileUpload {

	@Id
	@GeneratedValue
	private int id;
	@Lob
	private byte[] image;
	private int dataownerid;
	private String Secretkey;
	private String hash;
	private String filename;
	private String name;
	private String email;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getDataownerid() {
		return dataownerid;
	}
	public void setDataownerid(int dataownerid) {
		this.dataownerid = dataownerid;
	}
	public String getSecretkey() {
		return Secretkey;
	}
	public void setSecretkey(String secretkey) {
		Secretkey = secretkey;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return "FileUpload [id=" + id + ", image=" + Arrays.toString(image) + ", dataownerid=" + dataownerid
				+ ", Secretkey=" + Secretkey + ", hash=" + hash + ", filename=" + filename + ", name=" + name
				+ ", email=" + email + "]";
	}
	
	
	
	
	
	

	
}
