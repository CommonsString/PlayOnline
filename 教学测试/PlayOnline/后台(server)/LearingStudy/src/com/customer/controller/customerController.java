package com.customer.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.customer.dao.customerDAO;
import com.xmlBean.Block;
import com.xmlBean.FileGroup;
import com.xmlBean.SomeFiles;

import net.sf.json.JSONObject;
import tools.common.closeTools;
import tools.common.jsonUtils;
import tools.servlet.newBaseServlet;

/**
 * 
 * 客户端
 * getBlocks : right
 * getFileGroups : right  , 返回值可能需要调整
 * getFileGroup ： right
 * searchFileGroups： right , 不包含模糊查询
 *
 */
@SuppressWarnings("serial")
public class customerController extends newBaseServlet{

	private customerDAO dao = new customerDAO();
	
	//主界面
	public void getAllFileGroups(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		List<FileGroup> groupList = null;
		//分页参数
		int pageSize = 20;
		int total = 0;
		String tempPage = request.getParameter("page"); //分页处理
		int page = Integer.parseInt(tempPage);
		try {
			out = response.getWriter();
			groupList = dao.getAllFileGroup((page - 1) * pageSize  , pageSize);
			total = dao.getTotalForMain();
			if(!(groupList.isEmpty())){ //不为空
				json.put("fileGroups", groupList);
				json.put("total", total);
				out.write(json.toString());
//				jsonUtils.jsonTools(out, json, true, "成功");
			}else{
				json.put("blocks", ""); //空字符串
				json.put("total", total);
				out.write(json.toString());
//				jsonUtils.jsonTools(out, json, false, "失败！");
			}
		} catch (Exception e) {
			json.put("blocks", "");
			out.write(json.toString());
			jsonUtils.jsonTools(out, json, false, "失败！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}		
	}
	
	//获取板块
	public void getBlocks(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		List<Block> blockList = null;
		

		try {
			out = response.getWriter();
			blockList = dao.getBolockList(); //数据返回
			if(!(blockList.isEmpty())){ //不为空
				json.put("blocks", blockList);
				out.write(json.toString());
//				jsonUtils.jsonTools(out, json, true, "成功");
			}else{
				json.put("blocks", blockList);
				out.write(json.toString());
//				jsonUtils.jsonTools(out, json, false, "失败！");
			}
		} catch (Exception e) {
			json.put("blocks", blockList);
			out.write(json.toString());
			jsonUtils.jsonTools(out, json, false, "失败！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out);
		}
	}
	
	//获取视频组(包含作者信息)
	@SuppressWarnings("rawtypes")
	public void getFileGroups(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		
		//预设
		JSONObject json = new JSONObject();
		List list = null; //集合
		PrintWriter out = null;
		//分页参数
		int pageSize = 20;
		int total = 0;
		//获取参数
		String blockId = request.getParameter("blockId");
		String tempPage = request.getParameter("page");
		int page = Integer.parseInt(tempPage);
		try {
			out = response.getWriter();
			list = dao.getFileGroup("blockId", blockId , (page - 1) * pageSize  , pageSize);
			total = dao.getTotal(blockId);
			if(!(list.isEmpty()) && total != 0){ //非空
				json.put("fileGroups", list);
				json.put("total", total);
				json.put("blockId", blockId);
				out.write(json.toString());
			}else{
				//返回空视频组
				json.put("fileGroups", list);
				json.put("total", total);
				json.put("blockId", blockId);
				out.write(json.toString());
			}
		} catch (Exception e) {
			json.put("fileGroups", list);
			json.put("total", total);
			json.put("blockId", blockId);
			out.write(json.toString());
			e.printStackTrace();
		}finally{
			out.flush();
			json = null;
			out = null;
			blockId = null;
			tempPage = null;
			list = null;
			closeTools.closeAll(out); //关闭流
		}
		
	}
	
	//获取视频组(根基Id,获取子文件(SomeFiles)
	public void getFileGroup(HttpServletRequest request , HttpServletResponse response){
	response.setCharacterEncoding("utf-8"); //设置编码
		
		//预设
		JSONObject json = new JSONObject();
		List<SomeFiles> list = null;
		PrintWriter out = null;
		//获取参数
		String fileGroupId = request.getParameter("fileGroupId");
		
		try {
			out = response.getWriter();
			list = dao.getFileList(fileGroupId);
			if(!(list.isEmpty())){ //非空
				json.put("fileGroup", list);
				jsonUtils.jsonTools(out, json, true, "成功");
			}else{
				json.put("fileGroup", ""); //返回空字符串
				jsonUtils.jsonTools(out, json, false, "剧集列表空！");
			}
		} catch (Exception e) {
			json.put("fileGroup", "");
			jsonUtils.jsonTools(out, json, false, "系统错误！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out); //关闭流
		}		
	}
	
	//获取视频组(单纯)(弃用)
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
			closeTools.closeAll(out); //关闭流
		}
				
	}
	
	//根据信息查询
	public void searchFileGroups(HttpServletRequest request , HttpServletResponse response){
		//根据标题查询
		response.setCharacterEncoding("utf-8"); //设置编码
		
		//预设
		JSONObject json = new JSONObject();
		List<FileGroup> list = null; //集合
		PrintWriter out = null;
		HashMap<String , Object> map = new HashMap<String , Object>();
		int pageSize = 20;
		
		//获取参数
		String information = request.getParameter("info");
		String tempPage = request.getParameter("page");
		int page = Integer.parseInt(tempPage);
		try {
			out = response.getWriter();
			//填充参数
			map.put("title", information);
			list = dao.searchFileGroup(map, (page - 1) * pageSize  , pageSize);
			if(list != null){ //非空
				json.put("fileGroup", list);
				jsonUtils.jsonTools(out, json, true, "成功");
			}else{
				jsonUtils.jsonTools(out, json, false, "失败！");
			}
		} catch (Exception e) {
			jsonUtils.jsonTools(out, json, false, "失败！");
			e.printStackTrace();
		}finally{
			out.flush();
			closeTools.closeAll(out); //关闭流
		}				
	}
	
}
