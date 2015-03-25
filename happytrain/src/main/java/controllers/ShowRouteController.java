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

import services.ClientService;
import services.RouteService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.StationVO;
import valueobjects.TimetableVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowRouteController {

	/**
	 * default constructor
	 */
	public ShowRouteController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private ClientService clientService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting parameters from GET");
    	String trainStr = req.getParameter("train");
		int trainId = Integer.parseInt(trainStr);
		
		LOG.info("Getting Stations from RouteService");
		List<StationVO> stationList = new ArrayList<StationVO>();
		stationList = routeService.getStationsByTrain(trainId);
		if (stationList.isEmpty()) {
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("haveRun", 0);
		req.setAttribute("stationList", stationList);
		
		
		String runStr = req.getParameter("run");
		if (runStr != null) {
			LOG.info("We have Run parameter");
			int runId = Integer.parseInt(runStr);
			
			LOG.info("Getting Timetable with Run.Id " + runId + " on every Station");
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			timetableList = clientService.getTimesFromStationList(runId, stationList);
			if (timetableList.isEmpty()) {
				LOG.info("No time was found");
				req.setAttribute("emptyList", 1);
			}
			
			req.setAttribute("timetableList", timetableList);
	    	req.setAttribute("haveRun", 1);
		}
		return "ShowRoute";
	}
}