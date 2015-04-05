/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.StationService;

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
	private static final Logger LOG = Logger.getLogger(AddStationController.class);
       
	@Autowired
	private StationService stationService;
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addstation", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		return "protected/AddStation";
	}
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addstation", method = RequestMethod.POST)
	public ModelAndView processPost(@RequestParam(value = "stationName") String stationName) {
		ModelAndView modelAndView = new ModelAndView("protected/AddStation");
		LOG.info("Getting parameters from form");
    	//String stationName = req.getParameter("stationName");
    	if (stationName == null || "".equals(stationName)) {
    		LOG.warn("Empty station name");
    		modelAndView.addObject("fail", 1);
			//req.setAttribute("fail", 1);
			return modelAndView;
    	}
    	LOG.info("Adding Station into DB");
    	try {
			stationService.addStation(stationName);
			modelAndView.addObject("fail", 0);
			//req.setAttribute("fail", 0);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add station into DB");
			modelAndView.addObject("fail", 1);
			//req.setAttribute("fail", 1);
		}
		return modelAndView;
	}
}
