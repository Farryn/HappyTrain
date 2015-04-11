/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
import valueobjects.TimetableVO;
 
/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class LoginController {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(LoginController.class);
       

	
	/** Process data from request.
     * @return page 
     */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView processRequest(@RequestParam(value="error", required = false) String error) {
		ModelAndView modelAndView = new ModelAndView("Login");
		if (error != null) {
			modelAndView.addObject("failMessage", "Wrong credentials");
		}
	    	return modelAndView;
	}

	
	
}
