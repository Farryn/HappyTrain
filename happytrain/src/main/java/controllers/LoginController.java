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

import services.ClientService;
import servlets.ShowFoundTrainsServlet;
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
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       

	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String processRequest() {
			
	    	return "Login";
	}

	
	
}
