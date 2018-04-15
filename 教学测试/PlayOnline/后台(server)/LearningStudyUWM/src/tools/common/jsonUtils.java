package tools.common;

import java.io.PrintWriter;

import net.sf.json.JSONObject;

public class jsonUtils {
	
	/**
	 * JSON 工具类
	 * param : 流 ， json , state , message
	 */
	public static void jsonTools(PrintWriter out , JSONObject json , boolean state , String message){
		if(json != null && out != null){
			json.put("status" , state);
			json.put("info", message);
			out.write(json.toString());
		}
	}

}
