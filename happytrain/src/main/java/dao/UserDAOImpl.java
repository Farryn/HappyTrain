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
	@Override
	public User findUser(String login, String hash) {
		String hql = "SELECT u FROM User u WHERE u.login=:login AND u.password=:hash";
		User user = (User) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.setParameter("hash", hash)
				.uniqueResult();
		return user;
	}

	/**
	 * @see dao.UserDAO#getPasswordForLogin(java.lang.String)
	 */
	@Override
	public String getPasswordForLogin(String login) {
		String hql = "SELECT u.password FROM User u WHERE u.login=:login";
		String hash = (String) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.uniqueResult();
		return hash;
		
	}
	
	

}
