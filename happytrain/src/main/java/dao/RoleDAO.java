package dao;

import java.util.List;

import entities.Role;

public interface RoleDAO extends GenericDAO<Integer, Role> {
	List<Role> findAllRole();
}
