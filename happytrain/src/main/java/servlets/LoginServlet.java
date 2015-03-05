package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.UserService;
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
	private static Logger log = Logger.getLogger(LoginServlet.class);
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Getting parameters from form");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String url = request.getParameter("url");
		String servletUrl = request.getParameter("servletUrl");
		
		log.info("Getting User by login and password from UserService");
		UserVO user = null;
		try {
			if (login == null || login.equals("")) {
				log.warn("Empty login");
				throw new MyException("Empty login");
			}
			
			if (password == null || password.equals("")) {
				log.warn("Empty password");
				throw new MyException("Empty password");
			}
			user = new UserService().findUserByLoginAndPass(login, password);
			request.getSession().setAttribute("user", user); 
			log.info("User was authentificated");
		    if (servletUrl.isEmpty()) {
		    	response.sendRedirect(getServletContext().getContextPath()+url); 
		    } else {
		    	response.sendRedirect(servletUrl);
		    }
		} catch (MyException e) {
			log.error("Exception: " + e);
			log.warn("Fail. Wrong credentials");
			request.setAttribute("failMessage", e.getMessage());
			request.setAttribute("URL", url); 
			request.setAttribute("servletUrl", servletUrl); 
			request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not log in");
			request.setAttribute("failMessage", "Could not log in");
			request.setAttribute("URL", url); 
			request.setAttribute("servletUrl", servletUrl); 
		    request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		}
		
	}

}
