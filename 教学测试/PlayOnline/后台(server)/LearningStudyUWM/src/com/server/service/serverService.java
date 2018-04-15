package com.server.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.server.dao.serverDAO;
import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import tools.common.CommonUtils;

public class serverService {

	private serverDAO dao = new serverDAO();
	
	@Test
	public void test(){
	}
	
	/**
	 * addBlock
	 * @param block
	 * @return flag
	 */
	public boolean addBlock(Block block){
		boolean flag = false;
		try {
			int result = dao.addBlock(block);
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
	 * deleteBlock
	 */
	public boolean deleteBlock(String blockId){
		boolean flag = false;
		List<FileGroup> groups = null;
		List<SomeFiles> fileList = null;
		Block block = null;
		boolean temp = false;
		String imageSrc = CommonUtils.getProperties("config.properties", "deletePicDir");
		String videoSrc = CommonUtils.getProperties("config.properties", "deleteVideoDir");
		try {
			if(!"".equals(blockId) && blockId != null){
				block = dao.getBlockById(blockId);
				groups = dao.getFileGroupByBlockId(blockId);
				if(groups.size() != 0 && groups != null){
					for(int i = 0 , len = groups.size() ; i < len ; i++){ //获取子文件
						fileList = dao.getSomeFilesByFileGroupId(groups.get(i).getFileGroupId());
						if(fileList.size() != 0 && fileList != null){//删除子文件操作
							for(int j = 0 ; j < fileList.size() ; j++){
								String tempFileName = CommonUtils.getFileName(fileList.get(j).getFileSrc());
								CommonUtils.isDeleteFile(videoSrc, tempFileName);
								dao.deleteSomeFiles(fileList.get(j));
							}
						}//删除视频组图片
						String tempVideo = CommonUtils.getFileName(groups.get(i).getImgSrc());
						CommonUtils.isDeleteFile(imageSrc, tempVideo);
						dao.deleteFileGroup(groups.get(i));
					}
					dao.deleteBlock(block);
					flag = true;
				}else{ 
					dao.deleteBlock(block);
					flag = true;
					temp = true;
					throw new RuntimeException();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(temp == true){
				flag = true;
			}else{
				flag = false;
			}
		}finally{
			imageSrc = null;
			videoSrc = null;
		}
		return flag;		
	}
	
	
	/**
	 * addFileGroup
	 * @param group
	 * @return flag
	 */
	public boolean addFileGroup(FileGroup group){
		boolean flag = false;
		try {
			int result = dao.addFileGroup(group);
			if(result != 0){
				return true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * alterBlock
	 */
	public int alertBlock(Map<String, Object> map){
		return dao.alertBlock(map);
	}
	
	
	/**
	 * alertFilGroup
	 */
	public int alertFileGroup(FileGroup group){
		return dao.alertFileGroup(group);
	}
	
	/**
	 * getFileGroupById
	 * @param fileGroupId
	 * @return
	 */
	public FileGroup getFileGroupById(String fileGroupId){
		return dao.getFileGroupById(fileGroupId);
	}
	
	/**
	 * alertFileGroupPic
	 * @param params
	 * @return
	 */
	public int alertFileGroupPic(HashMap<String, Object> params){
		return dao.alertFileGroupPic(params);
	}
	
	/**
	 * addSomeFiles
	 * @param file
	 * @return
	 */
	public boolean addSomeFiles(SomeFiles file){
		boolean flag = false;
		try {
			int result = dao.addSomeFiles(file);
			if(result != 0){
				return true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;		
	}
	
	/**
	 * deleteSomeFiles
	 * @param fileId
	 * @return
	 */
	public boolean deleteSomeFiles(String fileId){
		boolean flag = false;
		try {
			int result = dao.deleteSomeFiles(fileId);
			if(result != 0){
				return true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;			
	}
	
	/**
	 * @param fileGroupId
	 * @return
	 */
	public boolean deleteFileGroupByIdString(String fileGroupId){
		boolean flag = false;
		try {
			int result = dao.deleteFileGroupByIdString(fileGroupId);
			if(result != 0){
				return true;
			}
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * @param fileGroupId
	 * @return FileGroup
	 */
	public FileGroup getFileGroupFlush(String fileGroupId){
		return dao.getSingleFileGroup(fileGroupId);
	}
	
	/**
	 * getSomeFilesById
	 * @param fileId
	 * @return
	 */
	public SomeFiles getSomeFilesById(String fileId){
		return dao.getSomeFilesById(fileId);
	}
	
}
