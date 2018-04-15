package com.admin.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.xmlBean.Admin;

import tools.hibernate.daoImpl.hibernateImpl;

public class adminDAO {

	@SuppressWarnings({ "unchecked", "rawtypes"})
	private hibernateImpl<Admin> serviceForAdmin = new hibernateImpl(Admin.class);
//	private TxQueryRunner qerRun = new TxQueryRunner();
	
	//写死
	public Admin getAdmin(String account , String password){
		Admin admin = null;
		ArrayList<String> params = new ArrayList<String>();
		params.add("account");
		params.add("password");
		try {
			if(!("".equals(account)) && !("".equals(password))){
				admin = serviceForAdmin.findByAttrForMany(params, account , password);
				return admin;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return admin;
	}
	
	
	public Admin getAdmin2(List<String> attr , Object...value){
		
		Admin admin = serviceForAdmin.findByAttrForMany(attr, value);
		return admin;
	}
	@Test
	public void test(){
		
	}
	
}
