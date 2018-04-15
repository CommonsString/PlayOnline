package tools.common;
import java.io.PrintWriter;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class closeTools {
	
	/**
	 * 关闭工具
	 */
	public static void closeAll(Object...object){
		//遍历
		for(Object obj : object){
			if(obj instanceof SessionFactory) close((SessionFactory) obj);
			if(obj instanceof Session) close((Session) obj);
			if(obj instanceof PrintWriter) close((PrintWriter) obj);
		}
	}
	
	private static void close(SessionFactory factory){
		if(factory != null){
			factory.close();
		}
	}
	
	private static void close(Session session){
		if(session != null){
			session.close();
		}
	}
	
	private static void close(PrintWriter out){
		if(out != null){
			out.flush();
			out.close();
		}
	}
	

}
