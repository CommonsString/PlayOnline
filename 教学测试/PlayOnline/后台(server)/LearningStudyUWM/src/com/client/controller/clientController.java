package com.client.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.client.service.clientService;
import com.xmlbean.Block;
import com.xmlbean.FileGroup;
import com.xmlbean.SomeFiles;

import net.sf.json.JSONObject;
import tools.common.jsonUtils;
import tools.servlet.BaseServlet;

public class clientController extends BaseServlet{
	
	private static final long serialVersionUID = 1L;
	
	private clientService service = new clientService();
	
	/**
	 * @see obtain all block
	 */
	public void getBlocks(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		List<Block> blockList = null;

		try {
			out = response.getWriter();
			blockList = service.getBlockAllList();
			if(!(blockList.isEmpty())){ //不为空
				json.put("blocks", blockList);
				out.write(json.toString());
			}else{
				json.put("blocks", blockList);
				out.write(json.toString());
			}
		} catch (Exception e){
			json.put("blocks", blockList);
			out.write(json.toString());
			jsonUtils.jsonTools(out, json, false, "失败！");
			e.printStackTrace();
		}finally{
			out.flush();
			out.close();
		}		
	}
	
	
	/**
	 * @see obtain all fileGroup orderBy time
	 */
	
	@SuppressWarnings("rawtypes")
	public void getFileGroups(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		//预设
		JSONObject json = new JSONObject();
		HashMap<String , Object> map = new HashMap<String , Object>(); //参数
		List list = null; //集合
		PrintWriter out = null;
		//分页参数
		int pageSize = 20;
		int total = 0;
		//获取参数
		String blockId = request.getParameter("blockId");
		String tempPage = request.getParameter("page");
		int page = Integer.parseInt(tempPage);
		
		//填充参数
		map.put("blockId", blockId);
		map.put("beginIndex", (page - 1) * pageSize);
		map.put("endIndex", pageSize);
		try {
			out = response.getWriter();
			list = service.getFileGroups(map);
			total = service.getTotal(blockId);
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
			out.close();
		}		
	}
	
	
	/**
	 * @response all fileGroup
	 */
	public void getAllFileGroups(HttpServletRequest request , HttpServletResponse response){
		response.setCharacterEncoding("utf-8"); //设置编码
		JSONObject json = new JSONObject();
		PrintWriter out = null;
		List<FileGroup> groupList = null;
		HashMap<String , Object> params = new HashMap<String , Object>();
		//分页参数
		int pageSize = 20;
		int total = 0;
		String tempPage = request.getParameter("page"); //分页处理
		int page = Integer.parseInt(tempPage);
		params.put("beginIndex", (page - 1) * pageSize);
		params.put("endIndex", pageSize);
		try {
			out = response.getWriter();
			groupList = service.getAllFileGroup(params);
			total = service.getTotalforMain();
			if(!(groupList.isEmpty())){ //不为空
				json.put("fileGroups", groupList);
				json.put("total", total);
				out.write(json.toString());
			}else{
				json.put("blocks", ""); //空字符串
				json.put("total", total);
				out.write(json.toString());
			}
		} catch (Exception e) {
			json.put("blocks", "");
			out.write(json.toString());
			jsonUtils.jsonTools(out, json, false, "失败！");
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
	 * @see return childe node of fileGroup
	 */
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
				list = service.getListBySomeFiles(fileGroupId);
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
				out.close();
			}		
		}	
	
	
}
