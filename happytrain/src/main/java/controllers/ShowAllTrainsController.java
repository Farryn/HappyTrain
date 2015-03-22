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

import services.TrainService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.TrainVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowAllTrainsController {

	/**
	 * default constructor
	 */
	public ShowAllTrainsController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/alltrains", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting all trains from db");
    	TrainService ts = new TrainService();
    	List<TrainVO> trainList = new ArrayList<TrainVO>();
		try {
			trainList = ts.getAllTrains();
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
    	req.setAttribute("trainList", trainList);
    	
    	String url = req.getRequestURL().toString();
    	req.setAttribute("servletUrl", url);
		return "protected/ShowAllTrains";
	}
}
