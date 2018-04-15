package com.client.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xmlbean.Admin;
import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import tools.mybatisTools.MybatisTools;

public class clientDAO {
	
	
	/**
	 * @see return the list of Block
	 * @param none
	 * @return list<Block>
	 */
	public List<Block> getAllBlocks(){
		SqlSession session = null;
		List<Block> list = null;
		try {
			session = MybatisTools.acceptSession();
			list = session.selectList("getAllBlock");
			session.commit(); //手动提交
			return list;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;		
	}

	
	/**
	 * @see return the list of fileGroup consists of admin information
	 * @param map
	 * @return list<FileGroup>
	 */
	public List<FileGroup> getFileGroups(Map<String , Object> map){
		SqlSession session = null;
		List<FileGroup> list = null;
		try {
			session = MybatisTools.acceptSession();
			list = session.selectList("findByListAttribute" , map);
			session.commit(); //手动提交
			return list;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return list;			
	}

	/**
	 * @see return 
	 */
	public Admin getAdmin(String adminId){
		SqlSession session = null;
		Admin admin = null;
		try {
//			admin = new Admin();
			session = MybatisTools.acceptSession();
			admin = session.selectOne("getBlockById" , adminId);
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
	
	/**
	 * retun total count
	 */
	public Integer getTotalForFileGroup(String blockId){
		SqlSession session = null;
		int total = 0;
		try {
			session = MybatisTools.acceptSession();
			total = session.selectOne("getTotal" , blockId);
			session.commit(); //手动提交
			return total;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return total;	
	}
	
	
	/**
	 * @see return all fileGroup
	 */
	public List<FileGroup> getAllFileGroup(Map<String , Object> map){
		SqlSession session = null;
		List<FileGroup> group = null;
		try {
			session = MybatisTools.acceptSession();
			group = session.selectList("getAllFileGroup", map);
			session.commit(); //手动提交
			return group;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return group;		
	}
	
	/**
	 * @see return main the  total of filegroup
	 */
	public Integer getTotalForMain(){
		SqlSession session = null;
		int total = 0;
		try {
			session = MybatisTools.acceptSession();
			total = session.selectOne("getTotalForMain");
			session.commit(); //手动提交
			return total;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return total;		
	}
	
	public FileGroup getSingleFileGroup(String fileGroupId){
		SqlSession session = null;
//		int total = 0;
		FileGroup group = null;
		try {
			session = MybatisTools.acceptSession();
			group = session.selectOne("getFileGroupById" , fileGroupId);
			session.commit(); //手动提交
			return group;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return group;		
	}
	
	public List<SomeFiles> getListForSomeFiles(String fileGroupId){
		SqlSession session = null;
//		int total = 0;
		List<SomeFiles> group = null;
		try {
			session = MybatisTools.acceptSession();
			group = session.selectList("getListBySomeFiles" , fileGroupId);
			session.commit(); //手动提交
			return group;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return group;		
	}
}
