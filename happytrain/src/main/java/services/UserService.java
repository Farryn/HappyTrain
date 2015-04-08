/**
 * 
 */
package services;

import java.util.Date;

import dao.RoleDAO;
import dao.RoleDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import util.EmptyResultException;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface UserService {
	public void addUser(String firstName, String lastName, Date birthDate, String login, String password) throws EmptyResultException;

	public void setUserDao(UserDAO userDAO);

	public void setRoleDao(RoleDAO roleDAO);
}
