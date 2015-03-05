package dao;

import java.util.List;

import entities.Role;


/**
 * DAO for Role Entity.
 *
 */
public interface RoleDAO extends GenericDAO<Integer, Role> {
	/**Get all Roles from role table on DB.
	 * @return Role list
	 */
	List<Role> findAllRole();
	
	/**Get Role from role table with given name.
	 * @param str Role name
	 * @return Role Entity
	 */
	Role findByName(String str);
}
