package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import util.HibernateUtil;
import entities.Role;
import entities.User;

/**
 * Implementation of RoleDAO.
 *
 */
@Repository("roleDao")
public class RoleDAOImpl extends GenericDAOImpl<Integer, Role> implements RoleDAO {

	/**
	 * @see dao.RoleDAO#findAllRole()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findAllRole() {
		List<Role> roleList = getSessionFactory().getCurrentSession()
				.createQuery("FROM Role r")
				.list();
		if (roleList.isEmpty() || roleList == null){
			roleList = new ArrayList<Role>();
		}
		return roleList;
	}
	
	/**
	 * @see dao.RoleDAO#findByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findByName(String str) {
		
		String hql = "SELECT r FROM Role r WHERE r.name=:name";
		List<Role> roleList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("name", str)
				.list();
		if (roleList.isEmpty() || roleList == null){
			roleList = new ArrayList<Role>();
		}
		return roleList;
	}
}
