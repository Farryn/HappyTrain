/**
 * 
 */
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.UserService;
import servlets.ShowFoundTrainsServlet;
import util.EmptyResultException;

/**
 * @author Damir Tuktamyshev
 *
 */

@Controller
public class RegisterController {

	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
	
	@Autowired
	private UserService userService;
	
	/** Process data from request.
     * @return page 
     */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String processGet() {
	    	return "Register";
	}

	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		
		if (!wrongInput(firstName, lastName, login, password)) {
			req.setAttribute("fail", 1);
			return "Register";
		}
		
		String birthDateString = req.getParameter("birth_date");
		Date birthDate = null;
		try {
			birthDate = getDateFromString(birthDateString);
		} catch (ParseException e) {
			LOG.warn("Exception: " + e);
			req.setAttribute("fail", 1);
			return "Register";
		} 
		
		LOG.info("Adding User using UserService");
		try {
			userService.addUser(firstName, lastName, birthDate, login, password);
			req.setAttribute("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Could not add User into DB");
			req.setAttribute("fail", 1);
		}
		
	    return "Register";
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
	
	/**Generates Date object from String.
	 * @param str String representing date
	 * @return Date
	 * @throws ParseException 
	 */
	private Date getDateFromString(String str) throws ParseException {
		Date date = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		date = sdf.parse(str);
		return date;
	}
}
