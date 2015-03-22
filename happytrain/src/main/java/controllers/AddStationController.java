/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.StationService;
import servlets.ShowFoundTrainsServlet;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class AddStationController {

	/**
	 * default constructor
	 */
	public AddStationController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addstation", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
    	String stationName = req.getParameter("stationName");
    	if (stationName == null || "".equals(stationName)) {
    		LOG.warn("Empty station name");
			req.setAttribute("fail", 1);
			return "protected/AddStation";
    	}
    	LOG.info("Adding Station into DB");
    	try {
			new StationService().addStation(stationName);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add station into DB");
			req.setAttribute("fail", 1);
		}
		return "protected/AddStation";
	}
}
