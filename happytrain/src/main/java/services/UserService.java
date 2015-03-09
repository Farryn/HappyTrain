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



public class UserService {

	private static final Logger LOG = Logger.getLogger(UserService.class);
	
	private UserDAO udao = new UserDAOImpl();
	private RoleDAO rdao = new RoleDAOImpl();
	
	public void setRoleDao(RoleDAO rdao) {
		this.rdao = rdao;
	}

	public void setUserDao(UserDAO udao) {
		this.udao = udao;
	}

	public UserVO findUserByLoginAndPass(String login, String password) throws MyException, NullPointerException {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		UserVO userVO = null;
		try {
			User user = udao.findUser(login, password);
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

	public void addUser(String firstName, String lastName, Date birthDate,
			String login, String password)  {
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Creating new User and adding it to DB");
			Role role = rdao.findByName("client");
			User user = new User(firstName, lastName, login, password, birthDate, role);
			udao.persist(user);
			
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
