/**
 * 
 */
package filters;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import util.CustomAccessDeniedHandler;
import util.TokenUtil;

/**
 * @author Damir Tuktamyshev
 *
 */
@Component
public class RestAuthFilter extends GenericFilterBean{

		/**
		 * 
		 */
		public RestAuthFilter() {
		}
		
		@Autowired 
		private TokenUtil tokenUtil;
		
		/*@Autowired
		private AuthenticationManager authenticationManager;*/
		
		
		@Override
	    public void doFilter(ServletRequest request, ServletResponse response,
	            FilterChain chain) throws IOException, ServletException {
			RequestMatcher resourcesMatcher = new AntPathRequestMatcher("/tickets/**");
			if(resourcesMatcher.matches((HttpServletRequest) request)){
		        Map<String, String[]> parms = request.getParameterMap();
		        if(parms.containsKey("token")) {
		            String token = parms.get("token")[0]; // grab the first "token" parameter
		            if (tokenUtil.validate(token)) {
		                UserDetails userDetails = tokenUtil.getUserFromToken(token);
		                Authentication auth = 
		                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
		                SecurityContextHolder.getContext().setAuthentication(auth);
		            }
				}
		        chain.doFilter(request, response);
			}
		}
		
}
