package com.admin.dao;


import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.xmlbean.Admin;

import tools.mybatisTools.MybatisTools;

public class adminDAO {
	
	@Test
	public void Test(){
	}
	
	/**
	 * map装参数
	 * params : map
	 * return :admin
	 */
	public Admin verfifyAccount(Map<String , String> map){
		SqlSession session = null;
		Admin admin = null;
		try {
			if(map.isEmpty() == true){
				return null;
			}
			session = MybatisTools.acceptSession();
			admin = session.selectOne("loginAdmin", map);

			session.commit(); //手动提交
			return admin;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return admin;
	}
	
}
