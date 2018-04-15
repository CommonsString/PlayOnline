package com.server.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.server.dao.serverDAO;
import com.xmlBean.Block;
import com.xmlBean.FileGroup;
import com.xmlBean.SomeFiles;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.common.CommonUtils;
import tools.common.closeTools;
import tools.common.jsonUtils;
import tools.servlet.newBaseServlet;

/**
 * 
 * 	包含Servlet ADN reflect	
 *  augFileGroup dao已经测试
 *  
 */
@SuppressWarnings("serial")
public class serverController extends newBaseServlet{
	
	private serverDAO dao = new serverDAO();
	
	
	//获取视频组(单纯)
	public void getFileGroupInfo(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();//预设
		FileGroup group = null;
		PrintWriter out = null;
		String fileGroupId = request.getParameter("fileGroupId"); 	//获取参数
		
		try {
			out = response.getWriter();
			group = dao.getFileGroupFlush(fileGroupId);
			if(group != null){ //非空
				json.put("fileGroup", group);
				out.write(json.toString());
//					jsonUtils.jsonTools(out, json, true, "成功");
			}else{
				//返回空视频组
				json.put("fileGroup", "");
				out.write(json.toString());
//					jsonUtils.jsonTools(out, json, false, "失败！");
			}
		} catch (Exception e) {
			json.put("fileGroup", "");
			out.write(json.toString());
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out); //关闭流
			json = null;
			group = null;
			out = null;
			fileGroupId = null;
		}
				
	}
	
	//修改板块名
	public void alertBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String blockId = request.getParameter("blockId");
			String subject = request.getParameter("subject");
			if(!("".equals(blockId)) && blockId != null){
				String sql = "update block set subject = ? where blockId = ?";
				int flag = dao.SQLBlock(sql, subject , blockId);
				if(flag != 0){
					jsonUtils.jsonTools(out, json, true, "修改成功!");
				}else{
					jsonUtils.jsonTools(out, json, false, "模块不存在，修改失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "数据错误，修改失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}		
	}
	
	//删除板块
	public void deleteBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String blockId = request.getParameter("blockId");
			if(!("".equals(blockId)) && blockId != null){
				boolean flag = dao.deleteBlock(blockId);
				if(flag){
					jsonUtils.jsonTools(out, json, true, "删除成功!");
				}else{
					jsonUtils.jsonTools(out, json, false, "模块不存在，删除失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "数据错误，添加失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}			
	}	
	
	//增加板块
	public void augBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Block block = CommonUtils.toBean(request.getParameterMap(), Block.class);
			if(block != null){
				boolean flag = dao.addBlock(block);
				if(flag){
					jsonUtils.jsonTools(out, json, true, "添加成功!");
				}else{
					jsonUtils.jsonTools(out, json, false, "添加失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "数据错误，添加失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}		
	}
	
	//删除文件(子视频)
	@SuppressWarnings("resource")
	public void removeFiles(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		ArrayList<String> list = new ArrayList<String>(); //批量参数
		JSONArray tempJson = null; //临时对象
		PrintWriter out = null;
		SomeFiles file = null; //文件
		
		String jsonArrParam = request.getParameter("files");
		tempJson = JSONArray.fromObject(jsonArrParam); //接受json数组
		
		for(int j = 0 , len = tempJson.length() ; j < len ; j++){ //参数处理
			String param = (String) tempJson.get(j);
			list.add(param);
		}
		
		try {
			out = response.getWriter();
			String sql = "delete from file where fileId = ?";
			boolean batch = false;  //处理标记
			if(list.size() != 0 && list != null){
				for(int i = 0 ; i < list.size() ; i++){
					file = dao.findByidForFile(list.get(i)); //查询视频组
					if(file != null){ //删除磁盘
						String fileName = CommonUtils.getFileName(file.getFileSrc()); //获取文件名
						if(!"".equals(fileName) && fileName != null){
							String dir = CommonUtils.getProperties("config.properties", "deleteVideoDir"); //配置文件
							CommonUtils.isDeleteFile(dir, fileName); //删除磁盘
							batch = dao.SQLBatch(sql, file.getFileId()); //删除记录，有缺陷
						}else{
							batch = dao.SQLBatch(sql, file.getFileId()); //单纯删除记录
						}
					}else{
						throw new RuntimeException(); //视频不存在
					}
				}
				
				if(batch){
					jsonUtils.jsonTools(out, json, true, "删除成功！");
				}else{
					jsonUtils.jsonTools(out, json, false, "视频不存在，删除失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "网络错误！");
			}
			
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误，请重试！");
			e.printStackTrace();
		}finally{
			list = null;
			out.flush();
			closeTools.closeAll(out);
		}					
	}
	
	//添加视频组,(数据库操作)
	public void augFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;		
		
		try {
			out = response.getWriter();
			//获取参数
			FileGroup group = CommonUtils.toBean(request.getParameterMap(), FileGroup.class);
			if(group != null){
				group.setUpdateDate(new Date()); //添加时间
				boolean flag = dao.saveFileGroup(group);
				if(flag){
					jsonUtils.jsonTools(out, json, true, "添加成功!");
				}else{
					jsonUtils.jsonTools(out, json, false, "添加失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "获取异常，添加失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);			
		}
	}
	
	//修改视频组(数据库操作)
	@SuppressWarnings("unused")
	public void alertFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter(); 
			FileGroup group = CommonUtils.toBean(request.getParameterMap(), FileGroup.class); //获取参数
			int tempFlag = Integer.parseInt(request.getParameter("flag")); //处理价格
			group.setFlag(tempFlag); //注入
			if(group != null){
				String sql = "update filegroup set title = ? , discribe = ? , flag = ? where fileGroupId = ?";
				int result = dao.SQLFileGroupSrc(sql, group.getTitle() , group.getDiscribe() , group.getFlag() ,  group.getFileGroupId());
System.out.println(result);
				if(result != 0){
					jsonUtils.jsonTools(out, json, true, "修改成功!");
				}else{
					jsonUtils.jsonTools(out, json, false, "修改失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "视频组不存在，修改失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}				
	}
	
	//删除视频组(批量，数据库操作) 查询，在删除
	@SuppressWarnings({ "resource", "unused" })
	public void deleteFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		ArrayList<String> list = new ArrayList<String>(); //批量参数
//		ArrayList<FileGroup> listGroup = new ArrayList<FileGroup>(); //封装数据
		JSONArray tempJson = null; //临时对象
		PrintWriter out = null;
		FileGroup fileGroup = null; //文件
		
		String jsonArrParam = request.getParameter("fileGroupIds");
		tempJson = JSONArray.fromObject(jsonArrParam); //接受json数组
		
		for(int j = 0 , len = tempJson.length() ; j < len ; j++){
			String param = (String) tempJson.get(j);
			list.add(param);
		}
		
		try {
			out = response.getWriter();
			String sql = "delete from fileGroup where fileGroupId = ?";
			boolean batch = false;  //处理标记
			if(list.size() != 0 && list != null){
				for(int i = 0 ; i < list.size() ; i++){
					FileGroup group = dao.findByIdFileGroup(list.get(i)); //查询视频组
					if(group != null){ //删除磁盘
						String fileName = CommonUtils.getFileName(group.getImgSrc()); //获取文件名
						if(!"".equals(fileName) && fileName != null){
							String dir = CommonUtils.getProperties("config.properties", "deletePicDir"); //配置文件
							CommonUtils.isDeleteFile(dir, fileName); //删除磁盘
							batch = dao.SQLBatch(sql, group.getFileGroupId()); //删除记录，有缺陷
						}else{
							batch = dao.SQLBatch(sql, group.getFileGroupId()); //单纯删除记录
						}
					}else{
						throw new RuntimeException(); //视频组不存在
					}
				}
				
				if(batch){
					jsonUtils.jsonTools(out, json, true, "删除成功！");
				}else{
					jsonUtils.jsonTools(out, json, false, "视频组不存在，删除失败！");
				}
			}else{
				jsonUtils.jsonTools(out, json, false, "网络错误！");
			}
			
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "系统错误，请重试！");
			e.printStackTrace();
		}finally{
			list = null;
			out.flush();
			closeTools.closeAll(out);
		}			
	}
	
}
