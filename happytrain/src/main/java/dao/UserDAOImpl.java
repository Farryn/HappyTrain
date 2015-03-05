package dao;

import util.HibernateUtil;
import entities.User;

/**
 * Implementation of UserDAO.
 *
 */
public class UserDAOImpl extends GenericDAOImpl<Integer, User> implements UserDAO {

	/**
	 * @see dao.UserDAO#findUser(java.lang.String, java.lang.String)
	 */
	public User findUser(String login, String password) {
		String hql = "SELECT u FROM User u WHERE u.login=:login AND u.password=:password";
		User user = (User) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.setParameter("password", password)
				.uniqueResult();
		return user;
	}

	
	
	

}
