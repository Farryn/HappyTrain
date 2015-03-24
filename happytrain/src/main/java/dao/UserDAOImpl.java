package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import entities.User;

/**
 * Implementation of UserDAO.
 *
 */
@Repository("userDao")
public class UserDAOImpl extends GenericDAOImpl<Integer, User> implements UserDAO {

	/**
	 * @see dao.UserDAO#findUser(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUser(String login, String hash) {
		String hql = "SELECT u "
					+ "FROM User u "
					+ "WHERE u.login=:login AND u.password=:hash";
		List<User> userList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.setParameter("hash", hash)
				.list();
		if (userList.isEmpty() || userList == null){
			userList = new ArrayList<User>();
		}
		return userList;
	}

	/**
	 * @see dao.UserDAO#getPasswordForLogin(java.lang.String)
	 */
	@Override
	public String getPasswordForLogin(String login) {
		String hql = "SELECT u.password "
					+ "FROM User u "
					+ "WHERE u.login=:login";
		String hash = (String) getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.uniqueResult();
		if (hash == null) {
			hash="";
		}
		return hash;
		
	}
	
	

}
