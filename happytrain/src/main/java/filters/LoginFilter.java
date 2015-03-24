/*package filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import services.UserService;
import valueobjects.UserVO;

*//**
 * Servlet Filter implementation class LoginFilter.
 *//*
@WebFilter
public class LoginFilter implements Filter {

	*//**
	 * Logger instance.
	 *//*
	private static final Logger LOG = Logger.getLogger(LoginFilter.class);
	
	*//**
	 * Available URLs to Role mapping.
	 *//*
	private Map<String, String> urlRoleMap;
    *//**
     * Default constructor. 
     *//*
    public LoginFilter() {
    }

	

	
	*//**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 *//*
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		UserVO user = (UserVO) ((HttpServletRequest) request).getSession().getAttribute("user");
		HttpServletRequest req = (HttpServletRequest) request;
	    String url = req.getServletPath();
	    if (user != null) {
	    	LOG.info("Checking for availability of url to current User");
			boolean isAuth  = new UserService().isUserAuth(user, req.getServletPath(), urlRoleMap);
			if (isAuth) {
				chain.doFilter(request, response);
			} else {
				request.setAttribute("URL", url);
				request.setAttribute("failMessage", "У Вас недостаточно прав для доступа"); 
				LOG.info("User do not have enough rights");
			    request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
		} else {
			LOG.info("There is no User in Session");
		    request.setAttribute("URL", url);
		    request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}

	*//**
	 * @see Filter#init(FilterConfig)
	 *//*
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		urlRoleMap = new HashMap<String, String>();
		Enumeration<String> initParamsEnum = fConfig.getInitParameterNames();
		for (String role: Collections.list(initParamsEnum)) {
			urlRoleMap.put(role, fConfig.getInitParameter(role));
		}
	}




	@Override
	public void destroy() {
		// empty
		
	}

}
*/