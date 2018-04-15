package com.admin.service;

import java.util.Map;

import com.admin.dao.adminDAO;
import com.xmlbean.Admin;

public class adminService {
	
	adminDAO dao = new adminDAO();
	//登录
	public Admin loginAdmin(Map<String , String> map){
		return dao.verfifyAccount(map);
	}
	
}
