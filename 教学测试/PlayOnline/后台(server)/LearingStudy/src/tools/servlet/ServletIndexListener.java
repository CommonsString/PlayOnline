package tools.servlet;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/*
 * 监听器：程序退出，关闭所有线程
 */
public class ServletIndexListener implements ServletContextListener{

	 public static final List<String> MANUAL_DESTROY_THREAD_IDENTIFIERS = Arrays.asList("Abandoned connection cleanup thread", "pool-1-thread-1" 
			 , "AdminTaskTimer" , "HelperThread-#0" , "HelperThread-#1" , "HelperThread-#2" , "pool-1-thread-2"); 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		destroyJDBCDrivers(); //销毁JDBC
		destroySpecifyThreads(); //销毁Thread
	}

	//开启tomcat前执行线程
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

	
	//销毁指定线程
	@SuppressWarnings("deprecation")
	private void destroySpecifyThreads() {  
        final Set<Thread> threads = Thread.getAllStackTraces().keySet();  
        for (Thread thread : threads) {  
            if (needManualDestroy(thread)) {  
                synchronized (this) {  
                    try {  
                        thread.stop(); 
//                        System.out.println("线程销毁 成功!");
                    } catch (Exception e) {  
                    }  
                }  
            }  
        }  
    }
    
    //输入线程关键字
    private boolean needManualDestroy(Thread thread) {  
        final String threadName = thread.getName();  
        for (String manualDestroyThreadIdentifier : MANUAL_DESTROY_THREAD_IDENTIFIERS) {  
            if (threadName.contains(manualDestroyThreadIdentifier)) {  
                return true;  
            }  
        }  
        return false;  
    }
    
    
    private void destroyJDBCDrivers() {  
        final Enumeration<Driver> drivers = DriverManager.getDrivers();  
        Driver driver;  
        while (drivers.hasMoreElements()) {  
            driver = drivers.nextElement();  
            try {  
                DriverManager.deregisterDriver(driver);  
            } catch (SQLException e) {  
            }  
        }  
    }
}
