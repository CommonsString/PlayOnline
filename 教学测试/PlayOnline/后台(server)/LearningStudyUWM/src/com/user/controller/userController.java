package com.user.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.service.userService;
import com.xmlbean.Comment;
import com.xmlbean.RectifyInfo;
import com.xmlbean.Users;

import net.sf.json.JSONObject;
import tools.common.Comments;
import tools.common.CommonUtils;
import tools.common.MailTools;
import tools.common.UserParams;
import tools.common.jsonUtils;
import tools.servlet.BaseServlet;

@SuppressWarnings("serial")
/**
 * 切换要清除信息
 * @author MyPC
 *
 */
public class userController extends BaseServlet{
	
	private userService service = new userService();
	
	/**
	 * 密码输入框无法隐藏
	 * @see 注册 
	 */
	public void enroll(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		HashMap<String, Object> params = new HashMap<String, Object>();
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String email = request.getParameter("email").trim();
			String password = request.getParameter("password").trim();
			String name = request.getParameter("name").trim();
			
			//密保部分
			String question1 = request.getParameter("question1");
			String question2 = request.getParameter("question2");
			String answer1 = request.getParameter("answer1");
			String answer2 = request.getParameter("answer2");
			
			params.put("email", email.toLowerCase());
			params.put("password", CommonUtils.md5TransForm(password));
			params.put("name", name);
			
			params.put("question1", question1);
			params.put("question2", question2);
			params.put("answer1", answer1);
			params.put("answer2", answer2);
			
			
			
			Users user = CommonUtils.toBean(params, Users.class);
			RectifyInfo rectify = CommonUtils.toBean(params, RectifyInfo.class); //密保问题
			
			if(user != null){
				//查重
				String isCopy = service.getUserisCopy(email); //注册账号查重
				if(isCopy != null){ //存在
					jsonUtils.jsonTools(out, json, false, "该账号已存在，请勿重复注册！");
					return;
				}else{ //不存在
					/*
					 *验证邮箱，后才会注册 
					 */
					//设置激活状态
					user.setState(UserParams.USER_NOT_ACTIVE);
					//设置激活码
					user.setActiveCode(CommonUtils.uuid());
					
					String url = CommonUtils.getProperties("config.properties", "activeCodeUrl");
					StringBuffer content = MailTools.sendFormat(url, user.getActiveCode());
					//发送激活码
					MailTools.sendMail(email, content.toString());
					boolean flag = service.registeredUser(user); //注册账号
					boolean serFlag = service.registeredUserInfo(rectify); //密保
					//设置基本信息
					if(flag && serFlag){
						jsonUtils.jsonTools(out, json, true, "注册成功，需要激活账号后登陆");
					}else{
						jsonUtils.jsonTools(out, json, false, "注册失败");
					}
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "数据错误，修改失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}	
	}
	
	
	
	/**
	 * 用户激活
	 */
	public void active(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		String activeCode = request.getParameter("activeCode");
		
		if(!"".equals(activeCode) && activeCode != null){
			//根据激活码查找用户
			Users user = service.getUserByActiveCode(activeCode);
			if(user != null){
				//操作dao，更改用户状态，清空激活码
				//更改用户状态
				user.setState(UserParams.USER_IS_ACTIVE);
				user.setActiveCode("");
				//更新
				int result = service.updateUser(user);
				if(result != 0){
					jsonUtils.jsonTools(out, json, true, "激活成功，可以登陆");
					 try {
						request.getRequestDispatcher("/msg.jsp").forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}else{
				try {
					request.getRequestDispatcher("/soso.jsp").forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @see 登录 
	 * 待定
	 */
	public void loginUser(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		HashMap<String, Object> params = new HashMap<String, Object>();
		PrintWriter out = null;
		Users user = null;
		
		try {
			out = response.getWriter();
			String email = request.getParameter("email").trim();
			String password = request.getParameter("password").trim();
			params.put("email", email.toLowerCase());
			params.put("password", CommonUtils.md5TransForm(password));
			
			if(!("".equals(email)) && email != null
					&& !("".equals(password)) && password != null){
				user = service.loginUser(params); //验证账号密码是同时匹配
				String isCopy = service.getUserisCopy(email);
				
				if(isCopy == null){  //不存在
					jsonUtils.jsonTools(out, json, false, "账号不存在，请注册后登陆！");
					return;
				}
				
				if(user != null){
					json.put("user", user);
					jsonUtils.jsonTools(out, json, true, "登录成功!");
				}else{
					json.put("user", user);
					jsonUtils.jsonTools(out, json, false, "账号或密码错误，请重试！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "数据错误，登录失败！");
			}
		} catch (Exception e) {
			json.put("user", user);
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}	
	}
	
	
	/**
	 * @see 评论 recommend
	 */
	public void recommend(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		HashMap<String, Object> params = new HashMap<String, Object>();
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
			String fileGroupId = request.getParameter("fileGroupId");
			String userId = request.getParameter("userId");
			String change = request.getParameter("change");
			params.put("fileGroupId", fileGroupId);
			params.put("userId", userId);
			params.put("change", change);
			boolean tem = Boolean.valueOf(change);
			if(tem){ //true 修改
				//存在删除，不存在添加
				boolean flag = service.isRecommended(params);
				if(!flag){ //不存在 -- 添加
					int result = service.addRecommend(params);
					if(result != 0){ //操作后
						json.put("recommended", true);
						out.write(json.toString());
					}else{
						json.put("recommended", false);
						out.write(json.toString());
					}
				}else{ //存在 ---> 改为不存在
					int result = service.deleteRecommend(params);
					if(result != 0){ //操作后
						json.put("recommended", false);
						out.write(json.toString());
					}else{
						json.put("recommended", true);
						out.write(json.toString());
					}					
				}
			}else{ // false 查询该视频组是否被用户推荐
				boolean flag = service.isRecommended(params);
				if(flag){
					json.put("recommended", true);
					out.write(json.toString());
				}else{
					json.put("recommended", false);
					out.write(json.toString());
				}
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}			
	}
	
	
	@SuppressWarnings("unused")
	public void comment(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		
		
		try {
			out = response.getWriter();
			Comment comment = CommonUtils.toBean(request.getParameterMap(), Comment.class);
			comment.setDate(new Date());
			if(comment != null){
				int result = service.addcomment(comment);
				if(result != 0){
					jsonUtils.jsonTools(out, json, true, "评论成功！");
				}else{
					jsonUtils.jsonTools(out, json, false, "评论失败，请重试！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "网络错误，请重试！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}			
	}
	
	
	public void getComments(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		List<Comments> comments = null;
		
		String fileId = request.getParameter("fileId");
		try {
			out = response.getWriter();
			if(!"".equals(fileId) && fileId != null){
				comments =  service.getComments(fileId);

				if(!comments.isEmpty()){
					json.put("comments", comments);
					out.write(json.toString());
				}else{
					json.put("comments", comments);
					out.write(json.toString());
				}
			}else{
				json.put("comments", comments);
				out.write(json.toString());
			}
		} catch (Exception e) {
			json.put("comments", comments);
			out.write(json.toString());
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}			
	}
	
	/*
	 * 重置密码
	 */
	public void resetPassword(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		String email = request.getParameter("email");	
		String password = request.getParameter("password");
		map.put("email", email);
		map.put("password", CommonUtils.md5TransForm(password));
		
		try {
			out = response.getWriter();
			if(!("".equals(email)) && email != null
					&& !("".equals(password)) && password != null){
				int result = service.resetPassword(map);
				if(result != 0){
					jsonUtils.jsonTools(out, json, true, "更改成功！");
				}else{
					jsonUtils.jsonTools(out, json, false, "更改失败，请重试！");
				}
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误，请重试！");
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 获取密保
	 */
	public void getRectify(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		
		String email = request.getParameter("email");
		try {
			out = response.getWriter();
			RectifyInfo info = null;
			if(!"".equals(email) && email != null){
				info = service.getRectify(email);
				
				if(info != null){
					json.put("rectifyQueAndAns", info);
					jsonUtils.jsonTools(out, json, true, "");
				}else{
					json.put("rectifyQueAndAns", info);
					jsonUtils.jsonTools(out, json, true, "没有密保");
				}
			}else{
				json.put("rectifyQueAndAns", "");
				jsonUtils.jsonTools(out, json, false, "网络错误，请重试！");
			}
		} catch (Exception e) {
			json.put("rectifyQueAndAns", "");
			jsonUtils.jsonTools(out, json, false, "系统错误，请重试！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}		
		
	}
	
	
}
