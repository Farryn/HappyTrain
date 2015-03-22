/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.EmployeeService;
import servlets.ShowFoundTrainsServlet;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class AddTrainController {

	/**
	 * default constructor
	 */
	public AddTrainController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addtrain", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
    	String trainNumber = req.getParameter("trainNumber");
    	if (trainNumber == null || "".equals(trainNumber)) {
    		LOG.warn("Empty train number");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
    	
		int seatsCount = Integer.parseInt(req.getParameter("seatsCount"));
		if (seatsCount == 0) {
    		LOG.warn("Empty seats count");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
		
		String[] stationArray = req.getParameterValues("stationList[]");
		if (stationArray.length < 2) {
    		LOG.warn("Not enough stations count");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
		
		LOG.info("Adding Train into DB");
		try {
			new EmployeeService().addTrain(trainNumber, seatsCount, stationArray);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			req.setAttribute("fail", 1);
			LOG.error("Exception: " + e);
			LOG.warn("Could not add train into DB");
			
		}
		return "protected/AddTrain";
	}
}
