package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import dao.RoleDAOImpl;
import dao.TicketDAOImpl;
import dao.UserDAOImpl;
import entities.Role;
import entities.User;
import util.HibernateUtil;
import util.MyException;
import valueobjects.UserVO;



public class UserService {

	private static Logger log = Logger.getLogger(UserService.class);
	
	public UserVO findUserByLoginAndPass(String login, String password) throws Exception {
		UserDAOImpl udao = new UserDAOImpl();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		UserVO userVO = null;
		try {
			User user = udao.findUser(login, password);
			if (user == null) {
				throw new MyException("¬ведены неверные данные");
			}
			userVO = new UserVO(user);
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		return userVO;
	}

	public boolean isUserAuth(UserVO userVO, String servletPath, HashMap<String, String> urlRoleMap) {
		String currentUserRole = userVO.getRole().getName();
		String availableURLs = urlRoleMap.get(currentUserRole);
		StringTokenizer st = new StringTokenizer(availableURLs, ",");
		ArrayList<String> availableURLsList =  new ArrayList<String>();
	    while (st.hasMoreTokens()) {
	    	availableURLsList.add(st.nextToken().trim());
	    }
	    if (availableURLsList.contains(servletPath)) {
	    	return true;
	    }
		return false;
	}

	public void addUser(String firstName, String lastName, Date birthDate,
			String login, String password) throws Exception {
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Creating new User and adding it to DB");
			Role role = rdao.findByName("client");
			User user = new User(firstName, lastName, login, password, birthDate, role);
			udao.persist(user);
			
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
	}

	

}
