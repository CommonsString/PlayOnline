package com.server.dao;


import java.util.List;

import org.junit.Test;

import com.xmlBean.Block;
import com.xmlBean.FileGroup;
import com.xmlBean.SomeFiles;

import tools.common.CommonUtils;
import tools.hibernate.daoImpl.hibernateImpl;

public class serverDAO {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<Block> serviceForBlock = new hibernateImpl(Block.class);
	@SuppressWarnings({ "unchecked", "rawtypes"})
	private hibernateImpl<FileGroup> serviceForGroups = new hibernateImpl(FileGroup.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<SomeFiles> serviceForSomeFiles = new hibernateImpl(SomeFiles.class);
	
	//获取视频组，单纯
	public FileGroup getFileGroupFlush(String fileId){
		FileGroup group = null;
		group = serviceForGroups.findByID(fileId);
		return group;
	}
	
	//根据Id查询视频组，在删除
	public boolean deleteFileGroup(String fileGroupId) throws Exception{
		boolean flag = false;
		try {
			if(!"".equals(fileGroupId) && fileGroupId != null){
				FileGroup fileGroup = serviceForGroups.findByID(fileGroupId);
				if(fileGroupId != null){
					serviceForGroups.delete(fileGroup);
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;		
	}
	
	//fileGroup批处理
	public boolean SQLBatch(String sql , Object... attr){
		boolean flag = false;
		try {
			if(!"".equals(sql) && sql != null){
				serviceForGroups.updateSql(sql, attr);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;		
	}
	
	//修改模块姓名
	public int SQLBlock(String sql , Object...attr) throws Exception{
		int results = 0;
		if(!"".equals(sql) && sql != null){
			results = serviceForBlock.updateSql(sql, attr);
		}
		return results;
	}
	
	//修改视频组基本信息
	public int SQLFileGroupSrc(String sql , Object...attr) throws Exception{
		int results = 0;
		if(!"".equals(sql) && sql != null && attr != null){
			results = serviceForGroups.updateSql(sql, attr);
		}
		return results;
	}
	
	
	//根据Id，查询视频组
	public FileGroup findByIdFileGroup(String fileId) throws Exception{
		FileGroup group = null;
		if(!"".equals(fileId) && fileId != null){
			group = serviceForGroups.findByID(fileId);
			if(group != null){
				return group;
			}else{ 
				return group; //返回空
			}
		}
		return group;
	}
	
	//根据id查询，在删除模块(删除全部资源的操作)
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
				block = serviceForBlock.findByID(blockId); //获取板块
				groups = serviceForGroups.ListByAttrNotAttr("blockId", blockId);
				if(groups.size() != 0 && groups != null){
					for(int i = 0 , len = groups.size() ; i < len ; i++){ //获取子文件
						fileList = serviceForSomeFiles.ListByAttrNotAttr("fileGroupId", groups.get(i).getFileGroupId());
						if(fileList.size() != 0 && fileList != null){//删除子文件操作
							for(int j = 0 ; j < fileList.size() ; j++){
								String tempFileName = CommonUtils.getFileName(fileList.get(j).getFileSrc());
								CommonUtils.isDeleteFile(videoSrc, tempFileName);
								serviceForSomeFiles.delete(fileList.get(j)); //操作数据库
							}
						}//删除视频组图片
						String tempVideo = CommonUtils.getFileName(groups.get(i).getImgSrc());
						CommonUtils.isDeleteFile(imageSrc, tempVideo);
						serviceForGroups.delete(groups.get(i));
					}
					serviceForBlock.delete(block); //删除板块
					flag = true;
				}else{ 
					serviceForBlock.delete(block); //删除板块 , 不规范
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
	
	
	//增加模块
	public boolean addBlock(Block block){
		boolean flag = false;
		try {
			if(block != null){
				serviceForBlock.save(block);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//更新部分字段，更改视频组图片
	public int alertFileGroupPic(String sql , Object...field) throws Exception{
		int results = 0;
		if(!("".equals(sql)) && sql != null){
			results = serviceForGroups.updateSql(sql, field);
		}
		return results;
	}
	
	//存储视频组
	public boolean saveFileGroup(FileGroup file){
		boolean flag = false;
		try {
			if(file != null){
				serviceForGroups.save(file);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//存储视频文件
	public boolean saveFile(SomeFiles file){
		boolean flag = false;
		try {
			if(file != null){
				serviceForSomeFiles.save(file);
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	//根据id,获取文件
	public SomeFiles findByidForFile(String fileId) throws Exception{
		SomeFiles file = null;
		if(!"".equals(fileId) && fileId != null){
			file = serviceForSomeFiles.findByID(fileId);
			return file;
		}
		return file;
	}
	
	//id查询文件，再删除
	public boolean deleteFile(String fileId){
		boolean flag = false;
		try {
			if(!"".equals(fileId) && fileId != null){
//			SomeFiles file = findByidForFile(fileId);
				SomeFiles file = serviceForSomeFiles.findByID(fileId);
				System.out.println(file.toString());
				if(fileId != null){
					serviceForSomeFiles.delete(file);
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}
	
	public String tsest(){
		String sql = null;
		try {
			sql  = "try:";
			return sql;
		} catch (Exception e) {
		}finally{
			System.out.println("finally");
			sql = null;
		}
		return sql;
	}
	//测试
	@Test
	public void test(){
		System.out.println(tsest());
	}
	
	
	
}
