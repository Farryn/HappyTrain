/**
 * 
 */
package util;

import org.springframework.security.core.userdetails.UserDetails;

import valueobjects.UserJsonVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TokenUtil {
	 	String getToken(UserJsonVO user);
	    boolean validate(String token);
	    UserDetails getUserFromToken(String token);
	    String getPassword(String encoded);
}
