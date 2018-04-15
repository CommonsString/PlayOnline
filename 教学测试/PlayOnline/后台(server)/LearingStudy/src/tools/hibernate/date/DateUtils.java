package tools.hibernate.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

/**
 * 时间转换器  启用
 */
public class DateUtils{
	
	public static void main(String[] args) {
		String sql = DateToString(new Date());
		System.out.println(sql);
		
		Date date = StringToDate(sql);
		System.out.println(date);
	}
	
	
	@Test
	public void test(){
		
	
		
	}
	
	private static final String dateFormat = "yyyy年MM月dd日 HH:mm:ss";
	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();
	
	//获取format
	public static DateFormat getDateFormat(){
		DateFormat format = threadLocal.get();
		if(format == null){
			format = new SimpleDateFormat(dateFormat);
			threadLocal.set(format);
		}
		return format;
	}
	
	//时间格式化
	public static String DateToString(Date date){
		String temp = null;
		if(date != null){
			temp = getDateFormat().format(date);
			return temp;
		}
		return temp;
	}
	
	//字符转时间
	public static Date StringToDate(String date){
		Date temp = null;
		try {
			temp = getDateFormat().parse(date);
			return temp;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	public static Date getDate(){
		return new Date();
	}
	
	
	
}
