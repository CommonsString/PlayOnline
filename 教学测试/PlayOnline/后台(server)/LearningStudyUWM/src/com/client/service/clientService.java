package com.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.client.dao.clientDAO;
import com.xmlbean.Admin;
import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import tools.common.CommonObject;

public class clientService {

	private clientDAO dao = new clientDAO();
	
	@Test
	public void test(){
	}
	
	/**
	 * @param none
	 * @return List<Block>
	 */
	public List<Block> getBlockAllList(){
		return dao.getAllBlocks();
	}
	
	
	/**
	 * @param blockId
	 */
	public int getTotal(String blockId){
		return dao.getTotalForFileGroup(blockId);
	}
	
	public int getTotalforMain(){
		return dao.getTotalForMain();
	}
	
	/**
	 * @param map
	 * @return
	 */
	@SuppressWarnings({  "rawtypes", "unchecked" })
	public List<FileGroup> getFileGroups(Map<String, Object> map){
		List<FileGroup> groups = null;
		CommonObject comObj = null;
		List list = new ArrayList();
		groups = dao.getFileGroups(map);
		if(!groups.isEmpty() && groups.size() != 0){
			for(FileGroup fileGroup : groups){
				comObj = new CommonObject();
				Admin author = dao.getAdmin(fileGroup.getAdminId());
				comObj.setFileGroup(fileGroup);
				comObj.setAdmin(author);
				list.add(comObj);
			}
			return list;
		}
		return list;
	}
	
	/**
	 * 
	 * @param map
	 * @return main fileGroup
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<FileGroup> getAllFileGroup(Map<String , Object> map){
		List<FileGroup> groups = null;
		CommonObject comObj = null;
		List list = new ArrayList();
		groups = dao.getAllFileGroup(map);
		if(!groups.isEmpty() && groups.size() != 0){
			for(FileGroup fileGroup : groups){
				comObj = new CommonObject();
				Admin author = dao.getAdmin(fileGroup.getAdminId());
				comObj.setFileGroup(fileGroup);
				comObj.setAdmin(author);
				list.add(comObj);
			}
			return list;
		}
		return list;
	}
	
	
	/**
	 * @param fileGroupId
	 * @return FileGroup
	 */
	public FileGroup getFileGroupFlush(String fileGroupId){
		return dao.getSingleFileGroup(fileGroupId);
	}
	
	/**
	 * 
	 * @param fileGroupId
	 * @return
	 */
	public List<SomeFiles> getListBySomeFiles(String fileGroupId){
		return dao.getListForSomeFiles(fileGroupId);
	}
}
