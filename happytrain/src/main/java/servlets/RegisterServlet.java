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
	private static Logger log = Logger.getLogger(RegisterServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
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
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
    	log.info("Getting parameters from form");
		String firstName = req.getParameter("first_name");
		if (firstName == null || firstName.equals("")) {
    		log.warn("Empty first name field");
			req.setAttribute("fail", 1);
			return;
    	}
		
		String lastName = req.getParameter("last_name");
		if (lastName == null || lastName.equals("")) {
    		log.warn("Empty last name field");
			req.setAttribute("fail", 1);
			return;
    	}
		
		String login = req.getParameter("login");
		if (login == null || login.equals("")) {
    		log.warn("Empty login field");
			req.setAttribute("fail", 1);
			return;
    	}
		
		String password = req.getParameter("password");
		if (password == null || password.equals("")) {
    		log.warn("Empty password field");
			req.setAttribute("fail", 1);
			return;
    	}
		
		String birthDateString = req.getParameter("birth_date");
		Date birthDate = null;
		try {
			birthDate = getDateFromString(birthDateString);
		} catch (Exception e) {
			log.warn("Exception: " + e);
		} 
		
		log.info("Adding User using UserService");
		try {
			new UserService().addUser(firstName, lastName, birthDate, login, password);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not add User into DB");
			req.setAttribute("fail", 1);
		}
		
		
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
		processRequest(request,response);
		request.getRequestDispatcher("/Register.jsp").forward(request, response);
	}

	

}
