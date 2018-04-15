package tools.mybatisTools;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
/*
 * 可以考虑添加线程池
 */
public class MybatisTools {
	
	private static SqlSessionFactory sqlSessionFactory;
	
	private MybatisTools() {}
	
	static{
		try {
			String resource = "SqlMapperConfig.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//methods
	public static SqlSession acceptSession(){
		return sqlSessionFactory.openSession();
	}
	
	public static void closeSqlSession(SqlSession session){
		if(session != null){
			session.close();
		}
	}
}
