package services;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.EmptyResultException;
import dao.RoleDAO;
import dao.UserDAO;
import entities.Role;
import entities.User;



/**
 * @author Damir Tuktamyshev
 *	Service for User.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	
	/**
	 * DAO for User.
	 */
	
	private UserDAO userDao;
	/**
	 * DAO for Role.
	 */
	
	private RoleDAO roleDao;
	
	

	/**
	 * @param userDao the userDao to set
	 */
	@Autowired
	public void setUserDao(UserDAO userDao) {
		this.userDao = userDao;
	}

	/**
	 * @param roleDao the roleDao to set
	 */
	@Autowired
	public void setRoleDao(RoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	
	
	

	/**Adds User into DB.
	 * @param firstName First name
	 * @param lastName Last name
	 * @param birthDate Date of birth
	 * @param login Login
	 * @param password Password
	 * @throws EmptyResultException 
	 */
	@Transactional
	public void addUser(String firstName, String lastName, Date birthDate, String login, String password) throws EmptyResultException {
		LOG.info("Creating new User and adding it to DB");
		List<Role> roleList = roleDao.findByName("client");
		if (roleList.isEmpty()) {
			throw new EmptyResultException("Can't add User");
		}
		Role role = roleList.get(0);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		User user = new User(firstName, lastName, login, hashedPassword, birthDate, role);
		userDao.persist(user);
			
	}

	

}
