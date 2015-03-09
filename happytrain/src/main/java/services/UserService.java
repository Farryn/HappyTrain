package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import dao.RoleDAO;
import dao.RoleDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import entities.Role;
import entities.User;
import util.HibernateUtil;
import util.MyException;
import valueobjects.UserVO;



/**
 * @author Damir Tuktamyshev
 *	Service for User.
 */
public class UserService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	/**
	 * DAO for User.
	 */
	private UserDAO userDao = new UserDAOImpl();
	/**
	 * DAO for Role.
	 */
	private RoleDAO roleDao = new RoleDAOImpl();
	
	

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param roleDao the roleDao to set
	 */
	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	/**Get User by given login and password.
	 * @param login Login
	 * @param password Password
	 * @return UserVO
	 * @throws MyException if input was incorrect
	 * @throws NullPointerException
	 */
	public UserVO findUserByLoginAndPass(String login, String password) throws MyException, NullPointerException {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		UserVO userVO = null;
		try {
			User user = userDao.findUser(login, password);
			if (user == null) {
				throw new MyException("¬ведены неверные данные");
			}
			userVO = new UserVO(user);
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException | MyException  | NullPointerException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		return userVO;
	}

	/**Check whether User is authenticated to browse given page.
	 * @param userVO User
	 * @param servletPath Page 
	 * @param urlRoleMap Available Pages
	 * @return true if User is authenticated
	 */
	public boolean isUserAuth(UserVO userVO, String servletPath, Map<String, String> urlRoleMap) {
		String currentUserRole = userVO.getRole().getName();
		String availableURLs = urlRoleMap.get(currentUserRole);
		StringTokenizer st = new StringTokenizer(availableURLs, ",");
		List<String> availableURLsList =  new ArrayList<String>();
	    while (st.hasMoreTokens()) {
	    	availableURLsList.add(st.nextToken().trim());
	    }
	    if (availableURLsList.contains(servletPath)) {
	    	return true;
	    }
		return false;
	}

	/**Adds User into DB.
	 * @param firstName First name
	 * @param lastName Last name
	 * @param birthDate Date of birth
	 * @param login Login
	 * @param password Password
	 */
	public void addUser(String firstName, String lastName, Date birthDate,
			String login, String password)  {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating new User and adding it to DB");
			Role role = roleDao.findByName("client");
			User user = new User(firstName, lastName, login, password, birthDate, role);
			userDao.persist(user);
			
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
	}

	

}
