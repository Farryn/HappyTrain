/**
 * 
 */
package services;


import static org.junit.Assert.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import util.MyException;
import util.PasswordHash;
import valueobjects.UserVO;
import dao.RoleDAOImpl;
import dao.UserDAOImpl;
import entities.Role;
import entities.User;

/**
 * @author Mup4uk
 *
 */
public class UserServiceTest {

	
	@Mock
	private UserDAOImpl mockDAOuser;
	
	@Mock
	private RoleDAOImpl mockDAOrole;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.MyUserDetailsService#findUserByLoginAndPass(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testFindUserByLoginAndPass() {
		MyUserDetailsService service = new MyUserDetailsService(); 
		service.setUserDao(mockDAOuser);
		String login = "Login";
		String password = "Password";
		String hash = null;
		try {
			hash = PasswordHash.createHash("Password");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
		User user = new User("Name", "LastName", "login", "password", new Date(), new Role());
		UserVO expected = new UserVO(user);
		UserVO test = new UserVO(user);
		Mockito.when(mockDAOuser.getPasswordForLogin(Mockito.anyString())).thenReturn(hash);
		Mockito.when(mockDAOuser.findUser(Mockito.anyString(), Mockito.anyString())).thenReturn(user);
		try {
			test = service.findUserByLoginAndPass(login, password);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	    Mockito.verify(mockDAOuser, Mockito.atLeastOnce()).findUser(Mockito.anyString(), Mockito.anyString());
	    Assert.assertEquals(expected.getFirstName(), test.getFirstName());
	    
	    
	    Mockito.when(mockDAOuser.findUser(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
	    try {
	    	service.findUserByLoginAndPass(login, password);   
	    	fail();
		} catch (MyException e) {
			
		} catch (Exception e) {
			fail();
		} 

	    Mockito.when(mockDAOuser.findUser(Mockito.anyString(), Mockito.anyString())).thenThrow(new HibernateException("Some info"));
	    try {
	    	service.findUserByLoginAndPass(login, password);   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	}

	/**
	 * Test method for {@link services.MyUserDetailsService#isUserAuth(valueobjects.UserVO, java.lang.String, java.util.HashMap)}.
	 */
	@Test
	public void testIsUserAuth() {
		
	}

	/**
	 * Test method for {@link services.MyUserDetailsService#addUser(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddUser() {
		MyUserDetailsService service = new MyUserDetailsService(); 
		service.setUserDao(mockDAOuser);
		service.setRoleDao(mockDAOrole);
		String login = "client";
		String password = "Password";
		
		Role role = new Role("admin");
		Mockito.when(mockDAOrole.findByName(login)).thenReturn(role);
		try {
			service.addUser("Name", "LastName", new Date(), "login", "password");
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAOrole, Mockito.atLeastOnce()).findByName(login);
	    
	    

	    Mockito.when(mockDAOrole.findByName(login)).thenThrow(new HibernateException("Some info"));
	    try {
	    	service.findUserByLoginAndPass(login, password);   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	    
	   
	}

}
