package com.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.user.dao.userDAO;
import com.xmlbean.Comment;
import com.xmlbean.Recommend;
import com.xmlbean.RectifyInfo;
import com.xmlbean.Users;

import tools.common.Comments;

public class userService {
	
	private userDAO dao = new userDAO();
	
	/**
	 * @see 注册用户
	 * dao : addUsers()
	 */
	public boolean registeredUser(Users user){
		boolean flag = false;
		try {
			int result = dao.addUsers(user);
			if(result != 0){
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	public boolean registeredUserInfo(RectifyInfo info){
		boolean flag = false;
		try {
			int result = dao.addRectifyInfo(info);
			if(result != 0){
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取密保
	 */
	public RectifyInfo getRectify(String email){
		return dao.getRectifyByEmail(email);
	}
	
	/**
	 * @param map
	 * @return 登录
	 */
	public Users loginUser(HashMap<String, Object> map){
		return dao.isloginUser(map);
	}
	
	/**
	 * @see isRecommend
	 */
	
	public boolean isRecommended(HashMap<String, Object> map){
		boolean flag = false;
		Recommend recommended = null;
		try {
			recommended = dao.isRecommend(map);
			if(recommended != null){
				flag = true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * @see addRecomend
	 */
	
	public int addRecommend(HashMap<String, Object> map){
		return dao.addRecommend(map);
	}
	
	
	/**
	 * @see deleteRecommend
	 */
	
	public int deleteRecommend(HashMap<String, Object> map){
		return dao.deleteRecommend(map);
	}
	
	
	public int addcomment(Comment comment){
		return dao.addcomment(comment);
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Comments> getComments(String fileId){
		ArrayList obj = new ArrayList();
		List<HashMap<String, Object>> list = dao.getComments(fileId);
		
		for(HashMap<String, Object> params : list){
			if(!params.isEmpty()){
				Users user = dao.getUserById((String)params.get("userId"));
				if(user != null){
					Comments comments = new Comments();
					comments.setUser(user);
					comments.setContent((String)params.get("content"));
					comments.setDate((Date)params.get("date"));	
					obj.add(comments);
				}
			}		
		}
		return obj;
	}
	
	
	public Users getUserByActiveCode(String code){
		return dao.getUserByActiveCode(code);
	}
	
	public int updateUser(Users user){
		return dao.updateUser(user);
	}
	
	
	public String getUserisCopy(String email){
		return dao.getUserisCopy(email);
	}
	
	public int resetPassword(HashMap<String, Object> map){
		return dao.changePassword(map);
	}
	
}
