package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import dao.RoleDAOImpl;
import dao.TicketDAOImpl;
import dao.UserDAOImpl;
import entities.Role;
import entities.User;
import util.HibernateUtil;
import valueobjects.UserVO;



public class UserService {

	public UserVO findUserByLoginAndPass(String login, String password) {
		UserDAOImpl udao = new UserDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		UserVO userVO = null;
		try {
			User user = udao.findUser(login, password);
			userVO = new UserVO(user);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
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
			String login, String password) {
		RoleDAOImpl rdao = new RoleDAOImpl();
		UserDAOImpl udao = new UserDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			Role role = rdao.findByName("client");
			User user = new User(firstName, lastName, login, password, birthDate, role);
			udao.persist(user);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
	}

	

}
