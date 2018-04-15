package tools.common;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;

import com.ndktools.javamd5.Mademd5;

public class CommonUtils {
	
	//构造器私有
	private CommonUtils() {
	}

	
	/**
	 * MD5加密
	 * params : password
	 */
	public static String md5TransForm(String value){
		Mademd5 md = new Mademd5();
		String temp = md.toMd5(value);
		if("".equals(temp) || temp == null) return null;
		return temp;
	}
	
	/**
	 * map封装成对象
	 * params : map , class
	 * return : Bean
	 * tools : beanUtils
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T toBean(Map map , Class<T> clazz){
		T bean = null;
		try {
			bean = clazz.newInstance();
			BeanUtils.populate(bean, map);
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	/**
	 * 读取配置文件
	 * param : 类，配置文件名，属性名 
	 */
	public static String getProperties(String configName ,  String name){
		Properties config = null;
		String temp = null;
		try {
			if(!("".equals(name)) && name != null){
				config = new Properties();
//				config.load(CommonUtils.class.getResourceAsStream(configName));
				config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(configName));
//				config.load(new FileInputStream(configName));
				temp = config.getProperty(name);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 删除文件
	 * 目录和文件名字
	 */
	public static boolean isDeleteFile(String dir , String fileName){
		boolean flag = false;
		if(!dir.endsWith(File.separator)){
			dir = dir + File.separator; //文件目录形式，添加分割复
		}
		File dirFile = new File(dir);
		//文件不存在，不是一个目录
		if(!(dirFile.exists()) || !(dirFile.isDirectory())) return false;
		
		File[] files = dirFile.listFiles();
		for(File file : files){
			if(file.isFile() && !(file.isDirectory())){ //是文件，不是目录
				if(file.getName().equals(fileName)){ //文件
					file.delete(); 
					flag = true;
					break;
				}
			}
		}
		return flag;
	}	
	
	/**
	 * 返回文件名
	 */
	public static String getFileName(String url){
		String result = null;
		if(!("".equals(url)) && url != null){
			String[] arr = url.split("/");
			result = arr[arr.length - 1];
			return result;
		}
		return result;
	}

	/**
	 * 获取后缀名
	 * 
	 */
	public static String getByFileLastName (String fileName){
		if(!("".equals(fileName)) && fileName != null){
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return null;		
	}	
	
	
	/**
	 * 跟换文件名
	 */
	public static String TransFormName (String fileName){
		String[] eth = fileName.split("\\.");	
		if(!("".equals(fileName)) && fileName != null){
			eth[0] = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			return eth[0] + "." +  eth[1];
		}
		return null;		
	}
	
	/**
	 * 返回一个不重复的字符串
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
