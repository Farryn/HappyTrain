/**
 * 
 */
package services;

import java.util.Date;

import util.EmptyResultException;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface UserService {
	public void addUser(String firstName, String lastName, Date birthDate, String login, String password) throws EmptyResultException;
}
