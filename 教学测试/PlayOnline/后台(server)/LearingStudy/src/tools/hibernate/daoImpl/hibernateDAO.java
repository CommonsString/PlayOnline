package tools.hibernate.daoImpl;

import java.util.List;
import java.util.Map;

public interface hibernateDAO<E> {
//	E findBySQLId();
	public E findByAttrForMany(List<String> attr , Object...value);//多属性查找
	List<E> ListByAttrNotAttr(String attrName, String param) throws Exception;
	void batchSqlUpdate(String sql , List<String> attr) throws Exception;
	void delete(E entity) throws Exception;
	void save(E entity) throws Exception;
	int updateSql(String sql , Object...field) throws Exception; 
	E findByID(String fileId); //Id
	List<E> getList(String hql) throws Exception; //获取List
	E findByAttribute(String attrName , String param) throws Exception; //属性查询
	List<E> findByListAttribute(String attrName, String param, boolean trun , String orderBy ,  int beginIndex , int endIndex) throws Exception; //属性查询列表
	List<E> separatePage(int beginIndex , int endIndex) throws Exception; //所有查询
	Integer getCountTotalForAttr(String attr) throws Exception;
	List<E> findHQLByListPage(Map<String , Object> map , int beginIndex , int endIndex) throws Exception;
}
