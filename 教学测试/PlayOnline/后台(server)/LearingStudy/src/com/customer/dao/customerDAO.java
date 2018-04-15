package com.customer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.xmlBean.Admin;
import com.xmlBean.Block;
import com.xmlBean.CommonObject;
import com.xmlBean.FileGroup;
import com.xmlBean.SomeFiles;

import cn.itcast.jdbc.TxQueryRunner;
import tools.hibernate.daoImpl.hibernateImpl;



//警告，打印，处理
public class customerDAO {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<FileGroup> serviceForGroups = new hibernateImpl(FileGroup.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<Block> serviceForBlock = new hibernateImpl(Block.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<Admin> serviceForAdmin = new hibernateImpl(Admin.class);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private hibernateImpl<SomeFiles> serviceForSomeFiles = new hibernateImpl(SomeFiles.class);
	private TxQueryRunner qerRun = new TxQueryRunner();
	
	
	//分页(主界面,包含作者信息)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<FileGroup> getAllFileGroup(int beginIndex , int endIndex) throws Exception{
		List<FileGroup> groups = null;
		CommonObject comObj = null;
//		Admin admin = null; //暂时作者为固定
		List list = new ArrayList(); //返回值
		groups = serviceForGroups.separatePage(beginIndex, endIndex);
//		admin = serviceForAdmin.findByID("123");
		//处理
		if(!groups.isEmpty() && groups.size() != 0){
			for(FileGroup fileGroup : groups){
				comObj = new CommonObject();
				//查询对应作者信息
				Admin author = getAdminQueryRunner(fileGroup.getAdminId());
//				Admin author = serviceForAdmin.findByID("123");
				comObj.setFileGroup(fileGroup);
				comObj.setAdmin(author);
				//填充
				list.add(comObj);
			}
			return list;
		}
		return list;		
	}
	
	//获取表总记录(无属性)
	public int getTotalForMain(){
		Integer total = 0;
		try {
			String sql = "select COUNT(*) from fileGroup";
			long tempTotal = qerRun.query(sql, new ScalarHandler<>());
			total = (int)tempTotal;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return total;
	}
	
	//获取视频组，单纯
	public FileGroup getFileGroupFlush(String fileId){
		FileGroup group = null;
		group = serviceForGroups.findByID(fileId);
		return group;
	}
	
	//获取板块列表
	public List<Block> getBolockList() throws Exception{
		List<Block> list = null;
		String hql = "from Block";
//		String hql = "from Block where blockId = '123456' order by subject desc";
		list = serviceForBlock.getList(hql);
		//处理代码....
		return list;
	}
	
	//获取视频组(包含作者信息)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getFileGroup(String attrName , String param, int beginIndex , int endIndex) throws Exception{
		List<FileGroup> groups = null;
		CommonObject comObj = null;
//		Admin admin = null; //暂时作者为固定
		List list = new ArrayList(); //返回值
		groups = serviceForGroups.findByListAttribute(attrName, param , true , "updateDate" , beginIndex , endIndex);
//		admin = serviceForAdmin.findByID("123");
		//处理
		if(!groups.isEmpty() && groups.size() != 0){
			for(FileGroup fileGroup : groups){
				comObj = new CommonObject();
				//查询对应作者信息
				Admin author = getAdminQueryRunner(fileGroup.getAdminId());
//				Admin author = serviceForAdmin.findByID(fileGroup.getAdminId());
				comObj.setFileGroup(fileGroup);
				comObj.setAdmin(author);
				//填充
				list.add(comObj);
			}
			return list;
		}
		return list;
	}
	
	//获取视频组作者
	public Admin getAdmin(String attrName , String param) throws Exception{
		Admin admin = null;
		admin = serviceForAdmin.findByAttribute(attrName, param);
		return admin;
	}
	
	//queryRun
	public Admin getAdminQueryRunner(String attrId){
		Admin admin = null;
		try {
			String sql = "select * from admin where adminId = ?";
			admin = qerRun.query(sql, new BeanHandler<>(Admin.class), attrId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return admin;
	}

	//获取表总记录(带属性)
	public Integer getTotal(String attr){
		Integer total = 0;
		try {
			total = serviceForGroups.getCountTotalForAttr(attr);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return total;
	}
	
	//获取子文件
	public List<SomeFiles> getFileList(String fileGroupId) throws Exception{
		List<SomeFiles> groups = null;
		String hql = "from SomeFiles where fileGroupId = " + "'"+ fileGroupId +"'" + " order by uploadTime asc";
		groups = serviceForSomeFiles.getList(hql);
		return groups;
	}
	
	//获取视频组（notUse）
	public FileGroup getFileGroupList(String fileId) throws Exception{
		FileGroup obj = null;
		obj = serviceForGroups.findByID(fileId);
		//处理代码....
		return obj;	
	}
	
	//查询视频组
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<FileGroup> searchFileGroup(Map<String , Object> map , int beginIndex , int endIndex){
		List<FileGroup> groups = null;
		List list = new ArrayList(); //返回值
		try {
			groups = serviceForGroups.findHQLByListPage(map, beginIndex, endIndex);
			//处理
			if(!groups.isEmpty()){
				for(FileGroup fileGroup : groups){
					//查询对应作者信息
					Admin author = getAdmin("adminId" , fileGroup.getAdminId());
//					Admin author = getAdminQueryRunner(fileGroup.getAdminId());
					//填充
					list.add(fileGroup);
					list.add(author);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;	
	}
	
	
	//测试
	@Test
	public void test(){
		
	}
	
}
