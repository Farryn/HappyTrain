package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.UserService;

/**
 * Servlet implementation class RegisterServlet.
 */
@WebServlet
public class RegisterServlet extends HttpServlet {
	
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(RegisterServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
    }

    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */
    private Date getDateFromString( String str) throws ParseException, IllegalArgumentException  {
    	if (str == null) {
    		throw new IllegalArgumentException();
    	}
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		date = sdf.parse(str);
		
    	return date;
    }
    
    /** Processing request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from form");
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if (!wrongInput(firstName, lastName, login, password)) {
			req.setAttribute("fail", 1);
			return;
		}
		
		String birthDateString = req.getParameter("birth_date");
		Date birthDate = null;
		try {
			birthDate = getDateFromString(birthDateString);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
		} 
		
		LOG.info("Adding User using UserService");
		try {
			new UserService().addUser(firstName, lastName, birthDate, login, password);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add User into DB");
			req.setAttribute("fail", 1);
			
		}
		
		
	}
	

	private boolean wrongInput(String firstName, String lastName, String login, String password) {
		if (firstName == null || "".equals(firstName)) {
    		LOG.warn("Empty first name field");
			return false;
    	}
		
		if (lastName == null || "".equals(lastName)) {
    		LOG.warn("Empty last name field");
    		return false;
    	}
		
		if (login == null || "".equals(login)) {
    		LOG.warn("Empty login field");
    		return false;
    	}
		
		if (password == null || "".equals(password)) {
    		LOG.warn("Empty password field");
    		return false;
    	}
		return true;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		request.getRequestDispatcher("/Register.jsp").forward(request, response);
	}

	

}
