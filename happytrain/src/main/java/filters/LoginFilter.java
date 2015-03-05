package filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.UserService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.UserVO;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter//(urlPatterns = {"/protected/*", "/alltrains"})
public class LoginFilter implements Filter {

	private static Logger log = Logger.getLogger(LoginFilter.class);
	
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	HashMap<String, String> urlRoleMap;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		UserVO user = (UserVO) ((HttpServletRequest) request).getSession().getAttribute("user");
		HttpServletRequest req = (HttpServletRequest) request;
	    String url = req.getServletPath();
	    if (user != null) {
	    	log.info("Checking for availability of url to current User");
			boolean isAuth  = new UserService().isUserAuth(user, req.getServletPath(), urlRoleMap);
			if (isAuth) {
				chain.doFilter(request, response);
			} else {
				request.setAttribute("URL", url);
				request.setAttribute("failMessage", "У Вас недостаточно прав для доступа"); 
				log.info("User do not have enough rights");
			    request.getRequestDispatcher("/Login.jsp").forward(request, response);
			}
		} else {
			log.info("There is no User in Session");
		    request.setAttribute("URL", url);
		    request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		urlRoleMap = new HashMap<String, String>();
		Enumeration<String> initParamsEnum = fConfig.getInitParameterNames();
		for (String role: Collections.list(initParamsEnum)) {
			urlRoleMap.put(role, fConfig.getInitParameter(role));
		}
	}

}
