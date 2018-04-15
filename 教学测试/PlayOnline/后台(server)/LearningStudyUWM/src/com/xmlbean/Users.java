package com.xmlbean;

public class Users {
    private String userId;
    
    private String account;
     
    private String password;
    
    private String name;
    
    private String fileIdArr;
    
    private String email;
    
    private int state;
    
    private String activeCode;
    

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileIdArr() {
		return fileIdArr;
	}

	public void setFileIdArr(String fileIdArr) {
		this.fileIdArr = fileIdArr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}