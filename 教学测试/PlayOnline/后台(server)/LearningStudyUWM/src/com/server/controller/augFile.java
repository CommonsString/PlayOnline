package com.server.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.server.service.serverService;
import com.xmlbean.SomeFiles;

import net.sf.json.JSONObject;
import tools.common.CommonParams;
import tools.common.CommonUtils;
import tools.common.jsonUtils;


/**
 * 
 * @author MyPC
 * 上传文件
 *
 */
@SuppressWarnings("serial")
public class augFile extends HttpServlet{

	private serverService service = new serverService();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8"); //设置编码集
    	JSONObject json =new JSONObject();
    	PrintWriter out = null;
        //判断是否为多媒体上传
            try { //表单判断
            	out = response.getWriter(); //流
            	
				if(!ServletFileUpload.isMultipartContent(request)){
				    jsonUtils.jsonTools(out, json, false, "上传类型出错");
				    return ;
				}
				
				//配置上传参数
				DiskFileItemFactory factory = new DiskFileItemFactory(); //临时文件
				factory.setSizeThreshold(CommonParams.MEMORY_THRESHOLD); // 设置内存临界值  超过后将产生临时文件并存储于临时目录中
				factory.setRepository(new File(System.getProperty("java.io.tmpdir"))); //设置临时存储目录 C:\Users\MyPC\AppData\Local\Temp\
				ServletFileUpload upLoad = new ServletFileUpload(factory);

				
				upLoad.setFileSizeMax(CommonParams.MAX_FILE_SIZE); //设置最大文件上传值
				upLoad.setSizeMax(CommonParams.MAX_REQUEST_SIZE); //设置最大请求值，包含文件和表单数据
				upLoad.setHeaderEncoding("utf-8"); //编码处理
//				String upLoadPath = System.getProperty("catalina.home")  + File.separator  +  CommonParams.UPLOAD_DIRECTORY; //构造临时文件，存储上传文件
				String upLoadPath = CommonUtils.getProperties("config.properties", "uploadFilePath") + File.separator  +  CommonParams.UPLOAD_DIRECTORY;
				File upLoadDir = new File(upLoadPath);
				if(!upLoadDir.exists()){ //目录不存在，创建
				    upLoadDir.mkdirs();
				}
				
				
				HashMap<String , String> map = new HashMap<String , String> (); //取得参数
				
				//url
				String filePath = null; //存取路径
				String fileName = null;
				
				//解析请求内容，提取文件数据
				List<FileItem> formItems = upLoad.parseRequest(request);
				if(formItems != null && formItems.size() > 0){
				    for(FileItem item : formItems){
				        if(!item.isFormField()){ //处理不存在表单的字段
				        	String tempFileName = item.getName(); //处理文件名(跟换)
				        	tempFileName = tempFileName.substring(tempFileName.lastIndexOf("\\") + 1); //二次处理
				        	fileName = CommonUtils.TransFormName(new File(item.getName()).getName()); //跟换唯一名字
				            //核心路径
				            if(!("".equals(fileName)) 
				            		&& fileName != null 
				            		&& !("".equals(upLoadPath)) && upLoadPath != null){
				            	String fileStuff = CommonUtils.getByFileLastName(fileName); //获取文件后缀名
				            	for(String suff : CommonParams.TYPE_VIDEO){ //处理视频后缀名
				            		
				            		if(suff.equals(fileStuff)){ //预处理
			            			String dirPath = upLoadPath + File.separator + CommonParams.UPLOAD_VIDEO; //图片路径
        							File picDir = new File(dirPath);
        							if(!picDir.exists()){ //目录不存在，创建
        								picDir.mkdirs();
        							}
        							filePath = dirPath + File.separator + fileName; //图片存储路径
	            					File storePic = new File(filePath); //构造路径
                                    item.write(storePic); //存储
									break;  //终止遍历
				            		}
				            	}
				            }
				        }else{ //处理普通参数
				        	String name = item.getFieldName(); 
				        	String value = item.getString("utf-8"); //转码
				        	map.put(name, value);
				        }
				    }   
				}
				
				//数据库操作
				SomeFiles file = CommonUtils.toBean(map, SomeFiles.class);
				String proUrl = CommonUtils.getProperties("config.properties", "videoUrl"); //目录
				String httpUrl = proUrl +  fileName;  //完整http路径
				file.setFileSrc(httpUrl);
				file.setUploadTime(new Date());
				
				boolean flag = service.addSomeFiles(file);
				
				if(flag){
					jsonUtils.jsonTools(out, json, true, "添加成功");
				}else{
					jsonUtils.jsonTools(out, json, false, "添加失败");
				}
            }catch(Exception e){
            	jsonUtils.jsonTools(out, json, false, "系统错误,添加失败!");
            	e.printStackTrace();
            }finally{
            	out.flush();
            	out.close();
            }
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request , response);
	}	
	
}
