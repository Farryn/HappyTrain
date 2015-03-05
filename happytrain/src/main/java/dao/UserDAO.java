package dao;

import entities.User;

/**
 * DAO for User Entity.
 *
 */
public interface UserDAO extends GenericDAO<Integer, User> {
	/**Get User by login and password.
	 * @param login Login
	 * @param password Password
	 * @return User entity
	 */
	User findUser(String login, String password);
	

}
