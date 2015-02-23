
package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import util.HibernateUtil;
import entities.*;

public class GenericDAOImpl<K, E> implements GenericDAO<K, E> {
	
	private Class<E> entityClass;

	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	private Session currentSession;
	private Transaction currentTransaction;
	
	public Session openCurrentSession() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = HibernateUtil.getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	/*private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}*/

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	public void persist(E entity) {
		getCurrentSession().save(entity);
	}

	
	public void update(E entity) {
		getCurrentSession().update(entity);
	}

	
	public E findById(K id) {
		E entity = (E) getCurrentSession().get(entityClass, (Serializable) id);
		return entity; 
	}

	
	public void remove(E entity) {
		getCurrentSession().delete(entity);
	}

 
	public List<E> findAll() {
		//List<E> list = (List<E>) getCurrentSession().createQuery("from :entity").setParameter("entity", entityClass).list();
		List<E> list = (List<E>) getCurrentSession().createQuery("from "+entityClass.getName()).list();
		return list;
	}
}
