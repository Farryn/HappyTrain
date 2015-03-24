/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.RouteService;
import services.TimetableService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.StationVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class AddRunController {

	/**
	 * default constructor
	 */
	public AddRunController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
    
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private TimetableService timetableService;
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addrun", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting parameters from GET");
		int id = Integer.parseInt(req.getParameter("train"));
		
		LOG.info("Getting Station list by Train.Id from RouteService");
		List<StationVO> stationList = new ArrayList<StationVO>();
		try {
			stationList = routeService.getStationsByTrain(id);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("stationList", stationList);
		req.setAttribute("train", id);
		return "protected/AddRun";
	}
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addrun", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from POST");
		String[] arrivalTime = req.getParameterValues("arrivalTime[]");
		String[] departureTime = req.getParameterValues("departureTime[]");
		String[] stationArray = req.getParameterValues("stationList[]");
		int trainId = Integer.parseInt(req.getParameter("train"));
		LOG.info("Adding Run to DB using TimetableService");
		try {
			timetableService.addRun(trainId, stationArray, arrivalTime, departureTime);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add run into DB");
			req.setAttribute("fail", 1);
		}
		return "protected/AddRun";
	}
}
