package tools.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;


/*
 * 处理参数字符集合：
 */
public class GetRequest extends HttpServletRequestWrapper{
	private HttpServletRequest request;
	
	//构造
	public GetRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}
	
	@Override
	public String getParameter(String name) {
		//获取参数
		String value = request.getParameter(name);
		if(value == null) return null;  //为null,直接返回null
		//不为空，则对参数进行编码处理后返回
		try { 
			return new String(value.getBytes("ISO-8859-1") , "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	@Override
	public Map<String, String[]> getParameterMap() {
		
		Map<String , String[]> map = request.getParameterMap();
		if(map == null) return map;
		for(String key : map.keySet()){
			String[] values = map.get(key);
			for(int i = 0 ; i < values.length ; i++){
				try{
					values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
					
				}catch(UnsupportedEncodingException e){
					throw new RuntimeException(e);
				} 
			}
		}
		//处理后返回
		return map;
	}
	
	
	
}
