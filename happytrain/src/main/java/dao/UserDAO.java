package dao;

import java.util.List;

import entities.User;

/**
 * DAO for User Entity.
 *
 */
public interface UserDAO extends GenericDAO<Integer, User> {
	/**Get User by login and password.
	 * @param login Login
	 * @return User List
	 */
	List<User> findByUserName(String login);

	/**Get password hash from DB.
	 * @param login Login
	 * @return password hash
	 */
	String getPasswordForLogin(String login);
	

}
