package com.example.ImageEncryption.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RequestHandling {

	@Id
	@GeneratedValue
	private int id;
	private int fileId;
	private int userId;
	private String ownerName;
	private String ownerEmail;
	private int ownerId;
	private String status;
	private String fileName;
	private String userName;
	private String userEmail;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public int getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@Override
	public String toString() {
		return "RequestHandling [id=" + id + ", fileId=" + fileId + ", userId=" + userId + ", ownerName=" + ownerName
				+ ", ownerEmail=" + ownerEmail + ", ownerId=" + ownerId + ", status=" + status + ", fileName="
				+ fileName + ", userName=" + userName + ", userEmail=" + userEmail + "]";
	}
	
	
	
	
	
	
	
}
