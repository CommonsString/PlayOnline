package tools.hibernate.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class temp {

	public static void main(String[] args) {
		System.out.println("执行");
	}
	private static SessionFactory sessionFactory;
	
	static{	
        try {
			Configuration config = new Configuration().configure(); //加载SRC下的配置文件
			sessionFactory = config.buildSessionFactory(); //根据配置文件创建表
		} catch (Throwable  e) {
		    System.err.println("Initial SessionFactory creation failed." + e);  
            throw new ExceptionInInitializerError(e);  
		}
	}
	
	
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
	public static Session acceptLocalSession(){
		return sessionFactory.getCurrentSession();
	}
}
