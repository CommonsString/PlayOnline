package com.admin.controller;


import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admin.service.adminService;
import com.xmlbean.Admin;

import net.sf.json.JSONObject;
import tools.common.CommonUtils;
import tools.common.jsonUtils;
import tools.servlet.BaseServlet;

public class adminController extends BaseServlet{

	private static final long serialVersionUID = 1L;
	
	//驱动
	private adminService service = new adminService();
	
	//管理员登录
	public void loginAdmin(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		HashMap<String , String> params = new HashMap<String , String>();
		try {
			out = response.getWriter();
			String account = request.getParameter("username");
			String password = request.getParameter("password");
			if(!("".equals(account)) && !("".equals(password)) && 
					account != null && password != null){ //验证
				String tempPswd = CommonUtils.md5TransForm(password);
				params.put("account", account);
				params.put("password", tempPswd);
				Admin admin = service.loginAdmin(params);
				
				if(admin == null){ //不存在账号
					jsonUtils.jsonTools(out, json, false, "账号不存在，请输入正确的账号密码！");
					return;
				}
				
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
			out.close();
		}		
	}
	
	
	
}
