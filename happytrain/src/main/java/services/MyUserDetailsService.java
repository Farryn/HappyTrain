package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService{

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(MyUserDetailsService.class);
	
	/**
	 * DAO for User.
	 */
	@Autowired
	private UserDAO userDao;
	/**
	 * DAO for Role.
	 */
	@Autowired
	private RoleDAO roleDao;
	
	

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

	
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
 
		List<User> userList = userDao.findByUserName(username);
		if(userList.isEmpty()) throw new UsernameNotFoundException("");
		User user = userList.get(0);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
 
		return buildUserForAuthentication(user, authorities);
 
	}
	
	
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
			return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), 
				true, true, true, true, authorities);
	}
	
	
	private List<GrantedAuthority> buildUserAuthority(Role userRole) {
		 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRole.getName()));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
 
		return Result;
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
