package tools.hibernate.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.xmlBean.FileGroup;

/*
 * 多条件查询
 */
public class SqlCondition {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void Tsst(){
		StringBuilder sql = new StringBuilder();
		Class clasz = FileGroup.class;
		System.out.println(clasz.getSimpleName());
		sql.append("from Student where 1=1");
		HashMap<String , Object> map = new HashMap();
		map.put("name", "%" + "Aname" + "%");
		map.put("age", "age");
		
		//遍历map,拼接SQL
		for(Entry<String , Object> entry : map.entrySet()){
			System.out.println("值: " + entry.getValue());
			if(!("".equals(entry.getValue())) && entry.getValue() != null){
				sql.append(" and " + entry.getKey() + " like " +  ":" + entry.getKey());
			}
		}
		
		
		System.out.println(sql.toString());
	}
	
	
	
	public static <T> Query SqlTransfrom(Class<T> className , Map<String , Object> map , Session session){
		StringBuilder sql = new StringBuilder();
		sql.append("from " + className.getSimpleName() + " where 1=1");
		
		//遍历map,拼接SQL
		for(Entry<String , Object> entry : map.entrySet()){
			System.out.println("值: " + entry.getValue());
			if(!("".equals(entry.getValue())) && entry.getValue() != null){
				sql.append(" and " + entry.getKey() + "=:" + entry.getKey());
			}
		}
System.out.println("SQL语句" + sql.toString());
		
		Query query = session.createQuery(sql.toString());
		
		//赋值
		for(Entry<String , Object> entry : map.entrySet()){
System.out.println("值: " + entry.getValue());
			if(!("".equals(entry.getValue())) && entry.getValue() != null){
				query.setParameter(entry.getKey() , entry.getValue());
			}
		}
		return query;
	}

}
