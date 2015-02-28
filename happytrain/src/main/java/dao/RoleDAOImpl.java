package dao;

import java.util.List;

import util.HibernateUtil;
import entities.Role;
import entities.Station;

public class RoleDAOImpl extends GenericDAOImpl<Integer, Role> implements RoleDAO {

	public List<Role> findAllRole() {
		List<Role> roleList = HibernateUtil.getCurrentSession()
				.createQuery("FROM Role r").list();
		return roleList;
	}
	
	public Role findByName(String str) {
		
		String hql = "SELECT r FROM Role r WHERE r.name=:name";
		Role role = (Role) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("name", str)
				.uniqueResult();
		return role;
	}
}
