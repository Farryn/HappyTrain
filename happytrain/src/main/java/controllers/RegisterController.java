/**
 * 
 */
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
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
	private static final Logger LOG = Logger.getLogger(RegisterController.class);
	
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
	public ModelAndView processPost(@RequestParam(value="first_name") String firstName,
									@RequestParam(value="last_name") String lastName,
									@RequestParam(value="login") String login,
									@RequestParam(value="password") String password,
									@RequestParam(value="birth_date") String birthDateString) {
		ModelAndView modelAndView = new ModelAndView("Register");
		
		
		if (!wrongInput(firstName, lastName, login, password)) {
			modelAndView.addObject("fail", 1);
			return modelAndView;
		}
		
		Date birthDate = null;
		try {
			birthDate = getDateFromString(birthDateString);
			Calendar cal = Calendar.getInstance();
			Date current = cal.getTime();
			if (current.compareTo(birthDate) <= 0 ) throw new ParseException(birthDateString, 0);
		} catch (ParseException e) {
			LOG.warn("Exception: " + e);
			modelAndView.addObject("fail", 1);
			return modelAndView;
		} 
		
		LOG.info("Adding User using UserService");
		try {
			userService.addUser(firstName, lastName, birthDate, login, password);
			modelAndView.addObject("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Could not add User into DB");
			modelAndView.addObject("fail", 1);
		}
		
	    return modelAndView;
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
