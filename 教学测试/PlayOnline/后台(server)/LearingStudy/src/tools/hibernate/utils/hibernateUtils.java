package tools.hibernate.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class hibernateUtils {

	public static void main(String[] args) {
		System.out.println("执行");
	}
	
	private hibernateUtils() {
	}
	
	private static final SessionFactory sessionFactory;
	
	static{	
        try {
			Configuration config = new Configuration().configure(); //加载SRC下的配置文件
			sessionFactory = config.buildSessionFactory(); //根据配置文件创建表
		} catch (Throwable  e) {
		    System.err.println("Initial SessionFactory creation failed." + e);  
            throw new ExceptionInInitializerError(e);  
		}
	}
	
	//隔离数据共享
	public static final ThreadLocal<Session> session = new ThreadLocal<Session>();
	
	
	
	/**
	 * 
	 * @return factory
	 */ 
	public static SessionFactory acceptFactory(){
        return sessionFactory;
	}
	
	/**
	 * 
	 * @return session
	 */ 
	public static Session acceptLocalSession() throws HibernateException{
//		return sessionFactory.getCurrentSession();
        //通过线程对象.get()方法安全创建Session  
        Session s = session.get();
System.out.println("session:" + s == null);
        // 如果该线程还没有Session,则创建一个新的Session  
        if (s == null)  
        {  
            s = sessionFactory.openSession();  
            // 将获得的Session变量存储在ThreadLocal变量session里  
            session.set(s);  
        }  
        return s;		
	}
	
    //关闭Session  
    public static void closeSession() throws HibernateException  {  
        Session s = session.get();  
        if (s != null){
        	s.close();  
        	System.out.println("关闭资源session！");
        }  
        session.set(null);  
    }	
	
}
