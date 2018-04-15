package com.server.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.server.service.serverService;
import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tools.common.CommonUtils;
import tools.common.jsonUtils;
import tools.servlet.BaseServlet;

public class serverController extends BaseServlet{
	private static final long serialVersionUID = 1L;
	
	private serverService service = new serverService();
	
	/**
	 * add Block
	 */
	public void augBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			Block block = CommonUtils.toBean(request.getParameterMap(), Block.class);
			if(block != null){
				boolean flag = service.addBlock(block);
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
			out.close();
		}		
	}
	
	/**
	 * delete block
	 */
	public void deleteBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String blockId = request.getParameter("blockId");
			if(!("".equals(blockId)) && blockId != null){
				boolean flag = service.deleteBlock(blockId);
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
			out.close();
		}			
		
	}
	
	
	
	/**
	 * @see addFlieGroup
	 */
	public void augFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;		
		
		try {
			out = response.getWriter();
			FileGroup group = CommonUtils.toBean(request.getParameterMap(), FileGroup.class);
			if(group != null){
				group.setUpdateDate(new Date()); //添加时间
				boolean flag = service.addFileGroup(group);
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
			out.close();
		}
	}	
	

	/**
	 * alertBlock for Name
	 */
	public void alertBlock(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		HashMap<String, Object> params = new HashMap<String, Object>();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			String blockId = request.getParameter("blockId");
			String subject = request.getParameter("subject");
			
			params.put("blockId", blockId);
			params.put("subject", subject);
			if(!("".equals(blockId)) && blockId != null){
				int flag = service.alertBlock(params);
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
			out.close();
		}		
	}	
	
	
	/**
	 * 修改视频组(数据库操作)
	 * alertFileGroup
	 */
	
	@SuppressWarnings("unused")
	public void alertFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		try {
			out = response.getWriter(); 
			FileGroup group = CommonUtils.toBean(request.getParameterMap(), FileGroup.class);
			int tempFlag = Integer.parseInt(request.getParameter("flag")); //处理价格
			group.setFlag(tempFlag); //注入
			if(group != null){
				int result = service.alertFileGroup(group);
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
			out.close();
		}				
	}	
	
	/**
	 * 删除视频组(批量，数据库操作) 查询，在删除
	 */
	@SuppressWarnings({"unused" })
	public void deleteFileGroup(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		ArrayList<String> list = new ArrayList<String>(); //批量参数
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
			boolean batch = false;  //处理标记
			if(list.size() != 0 && list != null){
				for(int i = 0 ; i < list.size() ; i++){
					FileGroup group = service.getFileGroupById(list.get(i));
					if(group != null){ //删除磁盘
						String fileName = CommonUtils.getFileName(group.getImgSrc()); //获取文件名
						if(!"".equals(fileName) && fileName != null){
							String dir = CommonUtils.getProperties("config.properties", "deletePicDir"); //配置文件
							CommonUtils.isDeleteFile(dir, fileName); //删除磁盘
							batch = service.deleteFileGroupByIdString(group.getFileGroupId());
						}else{
							batch = service.deleteFileGroupByIdString(group.getFileGroupId());
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
			out.flush();
			out.close();
		}			
	}	
	
	
	/**
	 * @param get the single information fileGroup
	 */
	public void getFileGroupInfo(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();//预设
		FileGroup group = null;
		PrintWriter out = null;
		String fileGroupId = request.getParameter("fileGroupId"); 	//获取参数
		
		try {
			out = response.getWriter();
			group = service.getFileGroupFlush(fileGroupId);
			if(group != null){ //非空
				json.put("fileGroup", group);
				out.write(json.toString());
			}else{
				//返回空视频组
				json.put("fileGroup", "");
				out.write(json.toString());
			}
		} catch (Exception e) {
			json.put("fileGroup", "");
			out.write(json.toString());
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}		
	}	
	
	/**
	 * //删除文件(子视频)
	 * @param request
	 * @param response
	 */
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
			boolean batch = false;  //处理标记
			if(list.size() != 0 && list != null){
				for(int i = 0 ; i < list.size() ; i++){
					file = service.getSomeFilesById(list.get(i));
					if(file != null){ //删除磁盘
						String fileName = CommonUtils.getFileName(file.getFileSrc()); //获取文件名
						if(!"".equals(fileName) && fileName != null){
							String dir = CommonUtils.getProperties("config.properties", "deleteVideoDir"); //配置文件
							CommonUtils.isDeleteFile(dir, fileName); //删除磁盘
							batch = service.deleteSomeFiles(file.getFileId());
						}else{
							batch = service.deleteSomeFiles(file.getFileId());
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
			out.flush();
			out.close();
		}					
	}	
	
	
	
	
}
