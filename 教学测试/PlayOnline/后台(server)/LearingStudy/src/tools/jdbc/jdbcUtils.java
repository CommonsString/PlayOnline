package tools.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 *  饿汉式 单例模式
 *  
 *  dataSource 
 *  为null ，表示没有事务
 *  不为null，表示没有事务
 *  开启事务，需要赋值
 *  关闭事务，赋值为null
 */
public class jdbcUtils {
	
	//创建连接池
	private static DataSource dataSource = new ComboPooledDataSource();
	
	//创建连接线程局部变量
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	
	/*
	 * 如果有事务，返回当前事务连接
	 * 没有，则从连接池从获取一个
	 */
	public static Connection getConnection() throws SQLException{
		Connection connection = tl.get(); //获取当前线程事务连接
		if(connection != null) return connection;
		return dataSource.getConnection(); 
	}
	
	
	/*
	 * 开启事务
	 */
	public static void startTransaction() throws SQLException{
		Connection connection = tl.get(); //获取当前连接
		if(connection != null) throw new SQLException("已经开启事务，不需要重复开启！");
		//null
		connection = dataSource.getConnection(); //赋值，开启事务
		connection.setAutoCommit(false); //取消自动提交，改为手动提交
		tl.set(connection); //把当前事务连接放入 tl中
	}
	
	/*
	 * 提交事务
	 */
	public static void commitTransaction() throws SQLException{
		Connection connection = tl.get();
		if(connection == null) throw new SQLException("没有事务，无法提交！");
		
		//提交操作
		connection.commit();
		connection.close();
		connection = null;
		tl.remove(); //移除当前事务连接
	}
	
	/*
	 * 回滚事务
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection connection = tl.get();
		if(connection == null) throw new SQLException("没有事务，无法回滚！");
		
		//回滚操作
		connection.rollback();
		connection.close();
		connection = null;
		tl.remove();
	}
	
	/*
	 * 释放连接
	 * 当前连接存在，说明还在工作，所以不能直接释放掉
	 * 只能释放那些没有工作的连接
	 */
	public static void realseConnection(Connection con) throws SQLException{
		Connection connection = tl.get();
		//当前连接和传入连接，不匹配，说明传入连接不是当前连接，可以关闭
		if(con != connection){
			if(con != null && con.isClosed()){
				con.close();
			}
		}
	}
}
