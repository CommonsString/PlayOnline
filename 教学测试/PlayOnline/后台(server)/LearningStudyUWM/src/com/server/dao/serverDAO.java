package com.server.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import tools.mybatisTools.MybatisTools;

public class serverDAO {
	
	/**
	 * @see 
	 * add block
	 */
	public int addBlock(Block block){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("addBlock", block);
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
	 * deleteBlock
	 */
	public int deleteBlock(Block block){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteBlockById", block);
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
	 * @param blockId
	 * @return
	 */
	public Block getBlockById(String blockId){
		SqlSession session = null;
		Block block = null;
		try {
			session = MybatisTools.acceptSession();
			block = session.selectOne("getBlockById" , blockId);
			session.commit(); //手动提交
			return block;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return block;		
	}
	
	
	/**
	 * getSomeFilesById
	 * @param fileId
	 * @return
	 */
	public SomeFiles getSomeFilesById(String fileId){
		SqlSession session = null;
		SomeFiles file = null;
		try {
			session = MybatisTools.acceptSession();
			file = session.selectOne("getSomeFilesById" , fileId);
			session.commit(); //手动提交
			return file;
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		return file;			
	}
	
	/**
	 * 
	 * @param blockId
	 * @return
	 */
	public List<SomeFiles> getSomeFilesByFileGroupId(String fileGroupId){
		SqlSession session = null;
		List<SomeFiles> list = null;
		try {
			session = MybatisTools.acceptSession();
			list = session.selectList("getSomeFilesByFileGroupId", fileGroupId);
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
	 * deleteSomeFiles
	 * @param file
	 * @return
	 */
	public int deleteSomeFiles(SomeFiles file){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteSomeFiles", file);
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
	 * addSomeFiles
	 * @param file
	 * @return
	 */
	public int addSomeFiles(SomeFiles file){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.insert("addSomeFiles", file);
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
	 * deleteSomeFiles
	 * @param fileId
	 * @return
	 */
	public int deleteSomeFiles(String fileId){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteSomeFilesByIdString", fileId);
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
	 * deleteFileGroup
	 * @param file
	 * @return
	 */
	public int deleteFileGroup(FileGroup group){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteFileGroupById", group);
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

	public int deleteFileGroupByIdString(String fileGroupId){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.delete("deleteFileGroupByIdString", fileGroupId);
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
	 * @param blockId
	 * @return
	 */
	public List<FileGroup> getFileGroupByBlockId(String blockId){
		SqlSession session = null;
		List<FileGroup> list = null;
		try {
			session = MybatisTools.acceptSession();
			list = session.selectList("getFileGroupByBlockId", blockId);
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
	 * addFileGroup
	 */
	public int addFileGroup(FileGroup group){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			group.setUpdateDate(new Date());
			result = session.insert("addFileGroup", group);
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
	 * @param fileGroupId
	 * @return
	 */
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
	
	
	/**
	 * alterBlock
	 */
	public int alertBlock(Map<String, Object> map){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.update("alertBlock", map);
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
	 * alertFileGroup
	 */
	public int alertFileGroup(FileGroup group){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.update("alertFileGroup", group);
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
	 * @param fileGroupId
	 * @return
	 */
	public FileGroup getFileGroupById(String fileGroupId){
		SqlSession session = null;
		FileGroup group = null;
		try {
			session = MybatisTools.acceptSession();
			group = session.selectOne("getFileGroupById", fileGroupId);
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
	 * 
	 * @param params
	 * @return
	 */
	public int alertFileGroupPic(HashMap<String, Object> params){
		SqlSession session = null;
		int result = 0;
		try {
			session = MybatisTools.acceptSession();
			result = session.update("alertFileGroupPic", params);
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
