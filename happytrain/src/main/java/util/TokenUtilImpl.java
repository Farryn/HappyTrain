/**
 * 
 */
package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import valueobjects.UserJsonVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Service
public class TokenUtilImpl implements TokenUtil {

	/**
	 * 
	 */
	public TokenUtilImpl() {
	}

	@Autowired
	private UserDetailsService userDetailsService;
	/* (non-Javadoc)
	 * @see util.TokenUtil#getToken(valueobjects.UserJsonVO)
	 */
	@Override
	public String getToken(UserJsonVO user) {
		String token = user.getUsername() + " token " + user.getPassword();
		return token;
	}

	/* (non-Javadoc)
	 * @see util.TokenUtil#validate(java.lang.String)
	 */
	@Override
	public boolean validate(String token) {
		if (token.equals("")) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see util.TokenUtil#getUserFromToken(java.lang.String)
	 */
	@Override
	public UserDetails getUserFromToken(String token) {
		String login = getLoginFromToken(token); 
		UserDetails user = userDetailsService.loadUserByUsername(login);
		return user;
	}
	
	private String getLoginFromToken(String token) {
		String temp = token.substring(1, token.length()-1);
		String[] arr = temp.split(" token ");
		return arr[0];
	}

}
