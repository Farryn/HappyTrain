
package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import util.HibernateUtil;

/**
*
*
* @param <K> Primary Key type
* @param <E> Entity type
*/
public class GenericDAOImpl<K, E> implements GenericDAO<K, E> {
	
	/**
	 * Class object of Entity.
	 */
	private Class<E> entityClass;

	/**
	 * 
	 */
	private SessionFactory sessionFactory;

	/**
	 * Default constructor.
	 */
	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	
	
	/**
	 * @param sessionFactory
	 */
	@Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}



	/** 
	 * @see dao.GenericDAO#persist(java.lang.Object)
	 */
	@Override
	public void persist(E entity) {
		sessionFactory.getCurrentSession().save(entity);
	}

	
	/**
	 * @see dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(E entity) {
		sessionFactory.getCurrentSession().update(entity);
	}

	
	/**
	 * @see dao.GenericDAO#findById(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public E findById(K id) {
		E entity = (E) sessionFactory.getCurrentSession().get(entityClass, (Serializable) id);
		return entity; 
	}

	
	/**
	 * @see dao.GenericDAO#remove(java.lang.Object)
	 */
	@Override
	public void remove(E entity) {
		sessionFactory.getCurrentSession().delete(entity);
	}

 
	/**
	 * @see dao.GenericDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		Session session=null;
		List<E> list = new ArrayList<E>();
	    try 
	    {
	    session = sessionFactory.openSession();
	    list = (List<E>) session //sessionFactory.getCurrentSession()
				.createQuery("from " + entityClass.getName())
				.list();
	    
	    }
	    catch(Exception e)
	    {
	     //Logging
	    }
	    finally
	    {
	        if(session !=null && session.isOpen())
	        {
	          session.close();
	          session=null;
	        }
	    }
	    
		return list;
	}
}
