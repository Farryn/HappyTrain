/**
 * 
 */
package util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author Damir Tuktamyshev
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * 
	 */
	public CustomAuthenticationEntryPoint() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

	}

}
