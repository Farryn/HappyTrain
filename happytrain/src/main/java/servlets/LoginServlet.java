package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.MyUserDetailsService;
import util.MyException;
import valueobjects.UserVO;

/**
 * Servlet implementation class LoginServlet.
 */
@WebServlet
public class LoginServlet extends HttpServlet {
	
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(LoginServlet.class);
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("Getting parameters from form");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String url = request.getParameter("url");
		String servletUrl = request.getParameter("servletUrl");
		
		LOG.info("Getting User by login and password from UserService");
		UserVO user = null;
		try {
			if (login == null || "".equals(login)) {
				LOG.warn("Empty login");
				throw new MyException("Empty login");
			}
			
			if (password == null || "".equals(password)) {
				LOG.warn("Empty password");
				throw new MyException("Empty password");
			}
			user = new MyUserDetailsService().findUserByLoginAndPass(login, password);
			request.getSession().setAttribute("user", user); 
			LOG.info("User was authentificated");
		    if (servletUrl.isEmpty()) {
		    	response.sendRedirect(getServletContext().getContextPath()+url); 
		    } else {
		    	response.sendRedirect(servletUrl);
		    }
		} catch (MyException e) {
			LOG.error("Exception: " + e);
			LOG.warn("Fail. Wrong credentials");
			request.setAttribute("failMessage", e.getMessage());
			request.setAttribute("URL", url); 
			request.setAttribute("servletUrl", servletUrl); 
			request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not log in");
			request.setAttribute("failMessage", "Could not log in");
			request.setAttribute("URL", url); 
			request.setAttribute("servletUrl", servletUrl); 
		    request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		}
		
	}

}
