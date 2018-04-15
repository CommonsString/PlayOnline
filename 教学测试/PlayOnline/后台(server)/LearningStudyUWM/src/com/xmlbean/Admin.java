package com.xmlbean;


public class Admin {
	
	private String adminId;
	private String account;
	private String password;
	private String name; //昵称
	private Integer level; //权限
	
	@Override
	public String toString() {
		return "账号：" + this.account + "昵称：" + this.name;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

}
