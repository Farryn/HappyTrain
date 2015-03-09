
package dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

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
	 * Default constructor.
	 */
	public GenericDAOImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}
	
	
	
	/** 
	 * @see dao.GenericDAO#persist(java.lang.Object)
	 */
	@Override
	public void persist(E entity) {
		HibernateUtil.getCurrentSession().save(entity);
	}

	
	/**
	 * @see dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(E entity) {
		HibernateUtil.getCurrentSession().update(entity);
	}

	
	/**
	 * @see dao.GenericDAO#findById(java.lang.Object)
	 */
	@Override
	public E findById(K id) {
		E entity = (E) HibernateUtil.getCurrentSession().get(entityClass, (Serializable) id);
		return entity; 
	}

	
	/**
	 * @see dao.GenericDAO#remove(java.lang.Object)
	 */
	@Override
	public void remove(E entity) {
		HibernateUtil.getCurrentSession().delete(entity);
	}

 
	/**
	 * @see dao.GenericDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		List<E> list = (List<E>) HibernateUtil.getCurrentSession().createQuery("from "+entityClass.getName()).list();
		return list;
	}
}
