/**
 * 
 */
package util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author Damir Tuktamyshev
 *
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	/**
	 * 
	 */
	public CustomAccessDeniedHandler() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.access.AccessDeniedHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.access.AccessDeniedException)
	 */
	@Override
	public void handle(HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access is denied");

	}

}
