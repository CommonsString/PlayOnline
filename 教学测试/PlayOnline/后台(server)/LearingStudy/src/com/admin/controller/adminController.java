package com.admin.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.dao.adminDAO;
import com.xmlBean.Admin;

import net.sf.json.JSONObject;
import tools.common.CommonUtils;
import tools.common.closeTools;
import tools.common.jsonUtils;
import tools.servlet.newBaseServlet;

/*
 * 暂时不转发页面
 */
@SuppressWarnings("serial")
public class adminController extends newBaseServlet{
	
	private adminDAO dao = new adminDAO();
	
	//简单密码验证
	public void loginAdmin(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String account = request.getParameter("username");
			String password = request.getParameter("password");
			if(!("".equals(account)) && !("".equals(password)) && 
					account != null && password != null){ //验证
				String tempPswd = CommonUtils.md5TransForm(password);
				Admin admin = dao.getAdmin(account, tempPswd);
				if(account.equals(admin.getAccount()) 
				&& tempPswd.equals(admin.getPassword())){ //匹配
					jsonUtils.jsonTools(out, json, true, "登录成功！");
				}else{
					jsonUtils.jsonTools(out, json, false, "账号或密码错误，登录失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "密码或账号为空，登录失败！");
			}
		} catch (Exception e){
			jsonUtils.jsonTools(out, json, false, "系统错误，登录失败！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}				
		
	}
	
}