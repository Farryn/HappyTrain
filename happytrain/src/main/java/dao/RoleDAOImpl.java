package dao;

import java.util.List;

import entities.Role;

public class RoleDAOImpl extends GenericDAOImpl<Integer, Role> implements RoleDAO {

	public List<Role> findAllRole() {
		List<Role> roleList=getCurrentSession().createQuery("FROM Role r").list();
		return roleList;
	}
	
}
