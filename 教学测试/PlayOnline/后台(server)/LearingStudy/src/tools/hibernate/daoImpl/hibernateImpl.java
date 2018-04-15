package tools.hibernate.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.jdbc.Work;


import tools.hibernate.utils.SqlCondition;
import tools.hibernate.utils.hibernateUtils;

public class hibernateImpl<E> implements hibernateDAO<E>{
	
	@SuppressWarnings({ "rawtypes"})
	private Class classType= null; //类型名
	private String className = null;
	
	public hibernateImpl() {
	}
	
	@SuppressWarnings("rawtypes")
	public hibernateImpl(Class classType) {
		this.className = classType.getSimpleName();
		this.classType = classType;
	}
	
	//条件查询(多属性)
	
	@SuppressWarnings({ "unchecked" })
	public E findByAttrForMany(List<String> attr , Object...value){
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Criteria criteria = null;
		E obj = null;
		try{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(classType);
			for(int i = 0 ; i < attr.size() ; i++){
				criteria.add(Restrictions.eq(attr.get(i), value[i]));
			}
			obj = (E) criteria.uniqueResult();
			session.flush();
			transaction.commit(); //提交事务
			return obj;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
			return null;
		}finally{
			transaction = null;
			obj = null;
			criteria = null;
			hibernateUtils.closeSession();
		}
	}
	
	//存储实体
	@Override
	public void save(E entity) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		try{
			if(!className.equals(entity.getClass().getSimpleName())){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction(); //开启事务
			session.save(entity);
			session.flush(); //不能放在finally,commit后session就关闭了
			transaction.commit(); //提交事务
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			hibernateUtils.closeSession();
		}		
	}
	
	//删除实体
	@Override
	public void delete(E entity) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		try{
			if(!className.equals(entity.getClass().getSimpleName())){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction(); //开启事务
			session.delete(entity);
			session.flush(); //不能放在finally,commit后session就关闭了
			transaction.commit(); //提交事务
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			hibernateUtils.closeSession();
		}			
	}
	
	//sql原生操作
	@Override
	public int updateSql(String sql , Object...field) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		int records = 0;
		try{
			if(("".equals(sql)) && sql == null){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			query = session.createSQLQuery(sql);
			for(int i = 0 , len = field.length ; i < len ; i++){
				query.setParameter(i, field[i]);
			}
			records = query.executeUpdate();
			session.flush();
			transaction.commit(); //提交事务
			return records;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			query = null;
			records = 0;
			hibernateUtils.closeSession();
		}
		return records;
	}
	
	//批量更新SQL(待定)
	@Override
	public void batchSqlUpdate(String sql , List<String> attr) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		
		try{
			if(("".equals(sql)) && sql == null){
				throw new RuntimeException("空");
			}
			transaction = session.beginTransaction();
			//原生work接口
			session.doWork(
				new Work(){
					@Override
					public void execute(Connection con) throws SQLException {
						PreparedStatement pre = con.prepareStatement(sql);
						//批量操作
						for(int i = 0 , len = attr.size() ; i <  len; i ++){
							pre.setObject(1, attr.get(i));
							pre.addBatch();
						}
						pre.executeBatch();
						pre = null;
					}
				});
			session.flush();
			transaction.commit(); //提交事务
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			hibernateUtils.closeSession();
		}
	}
	
	//根据ID查询实体
	@SuppressWarnings({ "unchecked"})
	@Override
	public E findByID(String fileId){
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		E obj = null;
		try{
			if(("".equals(fileId)) && fileId == null){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			obj = (E) session.get(classType, fileId);
			session.flush();
			transaction.commit(); //提交事务
			return obj;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
//			e.printStackTrace();
		}finally{
			transaction = null;
			obj = null;
			hibernateUtils.closeSession();
		}
		return obj;
	}
	
	
	//返回List
	@SuppressWarnings("unchecked")
	@Override
	public List<E> getList(String hql) throws Exception {
/*		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
		Session session = sessionFactory.getCurrentSession();*/
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		List<E> list = null;
		try{
			if(("".equals(hql)) && hql == null){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			query = session.createQuery(hql);
			list = query.list();
			session.flush();
			transaction.commit(); //提交事务
			return list;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			list = null;
			transaction = null;
			query = null;
			hibernateUtils.closeSession();
//			closeTools.closeAll(sessionFactory);
		}
		return list;
	}
	
	
	//根据属性查询对象
	@SuppressWarnings("unchecked")
	@Override
	public E findByAttribute(String attrName , String param) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		String hql = null;
		Query query = null;
		E obj = null;
		try{
			if(("".equals(param)) && param == null){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			hql = "from " + className + " where " + attrName + "= '" +  param + "'";
			query = session.createQuery(hql);
			obj = (E) query.uniqueResult();
			session.flush();
			transaction.commit(); //提交事务
			return obj;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			hql = null;
			query = null;
			obj = null;
			hibernateUtils.closeSession();
		}
		return obj;		
	}
	
	//分页查询
	@SuppressWarnings("unchecked")
	@Override
	public List<E> separatePage(int beginIndex, int endIndex) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		String hql = null;
		List<E> list = null;
		try{
			if(beginIndex == 0 && endIndex == 0){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			hql = "from " + className;
			query = session.createQuery(hql);
			query.setFirstResult(beginIndex);  //分页核心
			query.setMaxResults(endIndex);
			list = query.list();
			session.flush();
			transaction.commit(); //提交事务
			return list;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			query = null;
			list = null;
			hql = null;
			hibernateUtils.closeSession();
		}
		return list;		
	}
	
	//获取表总记录数
	@Override
	public Integer getCountTotalForAttr(String attr) throws Exception {
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Criteria criteria = null;
		int total = 0;
		long tempTotal = 0;
		try{
			transaction = session.beginTransaction();
			criteria = session.createCriteria(classType);
			criteria.add(Restrictions.eq("blockId", attr));
			tempTotal = (long)criteria.setProjection(Projections.rowCount()).uniqueResult();
			total = (int)tempTotal;
			
			session.flush();
			transaction.commit(); //提交事务
			return total;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			criteria = null;
			total = 0;
			tempTotal = 0;
			hibernateUtils.closeSession();
		}
		return total;		
	}
	
	//根据属性，返回列表，无分页
	@SuppressWarnings("unchecked")
	@Override
	public List<E> ListByAttrNotAttr(String attrName, String param) throws Exception{
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		List<E> list = null;
		String hql = null;
		try{
			if("".equals(attrName) || "".equals(param)){
					throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			hql = "from " + className + " where " + attrName + "= '" +  param + "'";
			query = session.createQuery(hql);
			
			list = query.list();
			session.flush();
			transaction.commit(); //提交事务
			return list;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			query = null;
			hql = null;
			list = null;
			hibernateUtils.closeSession();
		}
		return list;	
	}
	
	//根据属性返回列表，有缺陷（排序写死，待定）
	/**
	 * params : 条件属性名，条件值，是否排序，排序属性，分页条件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findByListAttribute(String attrName, String param, boolean trun , String orderBy ,  int beginIndex , int endIndex) 
			throws Exception{
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		String hql = null;
		List<E> list = null;
		try{
			if("".equals(attrName) || "".equals(param)){
				if(beginIndex == 0 && endIndex == 0){
					throw new RuntimeException("不匹配");
				}
			}
			transaction = session.beginTransaction();
			hql = "from " + className + " where " + attrName + "='" +  param + "'";
			if(trun){
				hql = hql + " order by " + orderBy + " desc";
			}
			query = session.createQuery(hql);
			query.setFirstResult(beginIndex); //分页
			query.setMaxResults(endIndex);
			
			list = query.list();
			session.flush();
			transaction.commit(); //提交事务
			return list;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			query = null;
			hql = null;
			query = null;
			list = null;
			hibernateUtils.closeSession();
		}
		return list;			
	}
	
	//条件查询返回列表
	@SuppressWarnings("unchecked")
	@Override
	public List<E> findHQLByListPage(Map<String , Object> map , int beginIndex , int endIndex)
			throws Exception{
//		SessionFactory sessionFactory = hibernateUtils.acceptFactory();
//		Session session = sessionFactory.getCurrentSession();
		Session session = hibernateUtils.acceptLocalSession();
		Transaction transaction = null;
		Query query = null;
		List<E> list = null;
		try{
			if(beginIndex == 0 && endIndex == 0){
				throw new RuntimeException("不匹配");
			}
			transaction = session.beginTransaction();
			query = SqlCondition.SqlTransfrom(classType, map, session);
			query.setFirstResult(beginIndex);
			query.setMaxResults(endIndex);
			list = query.list();
			session.flush();
			transaction.commit(); //提交事务
			return list;
		}catch(Exception e){
			transaction.rollback(); //回滚事务
			e.printStackTrace();
		}finally{
			transaction = null;
			query = null;
			list = null;
			hibernateUtils.closeSession();
		}
		return list;
	}

}
