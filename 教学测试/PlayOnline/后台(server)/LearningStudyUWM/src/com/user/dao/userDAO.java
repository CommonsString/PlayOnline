package com.user.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import com.xmlbean.Comment;
import com.xmlbean.Recommend;
import com.xmlbean.RectifyInfo;
import com.xmlbean.Users;

import tools.mybatisTools.MybatisTools;

public class userDAO {
	
	@Test
	public void test(){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("password", "dsadas");
		map.put("email", "1149674138@qq.com");
		int i = changePassword(map);
		System.out.println(i);
	}	
	
	/**
	 * @see getUserByActiveCode 
	 */
	public RectifyInfo getRectifyByEmail(String email){
		SqlSession session = null;
		RectifyInfo info = null;
		try {
			session = MybatisTools.acceptSession();
			info = session.selectOne("getRectifyByEmail" , email);
			session.commit(); //手动提交
			return info;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return info;
	}	
	
	
	/**
	 * registeredUser 注册
	 * @param Users
	 * @return int
	 */
	public int addUsers(Users user){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("registeredUser", user);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;		
	}
	
	/**
	 * registeredUser 注册
	 * @param Users
	 * @return int
	 */
	public int addRectifyInfo(RectifyInfo info){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("addRectifyInfo", info);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;		
	}
	
	//更换密码 
	public int changePassword(HashMap<String, Object> map){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.update("changePassword", map);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;			
	}
	
	/**
	 * @see getUserByActiveCode 
	 */
	public Users getUserByActiveCode(String code){
		SqlSession session = null;
		Users users = null;
		try {
			session = MybatisTools.acceptSession();
			users = session.selectOne("getUserByActiveCode" , code);
			session.commit(); //手动提交
			return users;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return users;
	}
	
	
	/**
	 * @param isLoginUser
	 * @return
	 */
	public Users isloginUser(HashMap<String, Object> map){
		SqlSession session = null;
		Users users = null;
		try {
			session = MybatisTools.acceptSession();
			users = session.selectOne("loginUser" , map);
			session.commit(); //手动提交
			return users;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return users;		
	}
	
	
	/**
	 * @see isRecommend
	 */
	public Recommend isRecommend(HashMap<String, Object> map){
		SqlSession session = null;
		Recommend recommend = null;
		try {
			session = MybatisTools.acceptSession();
			recommend = session.selectOne("isRecommend" , map);
			session.commit(); //手动提交
			return recommend;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return recommend;		
	}
	
	/**
	 * 
	 * @param addRecommend
	 * @return
	 */
	public int addRecommend(HashMap<String, Object> map){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("addRecommend", map);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;		
	}
	

	/**
	 * 
	 * @param recommend
	 * @return
	 */
	public int deleteRecommend(HashMap<String, Object> map){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteRecommend", map);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;			
	}
	
	
	/**
	 * 
	 * @param addcomment
	 * @return
	 */
	public int addcomment(Comment comment){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("addComment", comment);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;		
	}
	
	
	/**
	 * 
	 * @param map
	 * @return
	 */
	public Users getUserById(String userId){
		SqlSession session = null;
		Users user = null;
		try {
			session = MybatisTools.acceptSession();
			user = session.selectOne("getUserById" , userId);
			session.commit(); //手动提交
			return user;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return user;		
	}
	
	public List<HashMap<String, Object>> getComments(String fileId){
		SqlSession session = null;
		List<HashMap<String, Object>> map = null;
		try {
			session = MybatisTools.acceptSession();
			map = session.selectList("getComments" , fileId);
			session.commit(); //手动提交
			return map;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return map;		
	}
	
	public int updateUser(Users user){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.update("updateUser", user);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;			
	}
	
	/**
	 * 判断用户是否重复
	 */
	
	public String getUserisCopy(String email){
		SqlSession session = null;
		String result = null;
		try {
			session = MybatisTools.acceptSession();
			result = session.selectOne("getUserisCopy", email);
			session.commit(); //手动提交
			return result;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return result;		
	}	
	
}
