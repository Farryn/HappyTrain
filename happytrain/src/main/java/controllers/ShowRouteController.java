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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
import services.RouteService;
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
	private static final Logger LOG = Logger.getLogger(ShowRouteController.class);
       
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private ClientService clientService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/route", method = RequestMethod.GET)
	public ModelAndView processGet(@RequestParam(value="train") String trainStr,
									@RequestParam(value="run", required = false) String runStr) {
		ModelAndView modelAndView = new ModelAndView("ShowRoute");
		//LOG.info("Getting parameters from GET");
    	//String trainStr = req.getParameter("train");
		int trainId = Integer.parseInt(trainStr);
		
		LOG.info("Getting Stations from RouteService");
		List<StationVO> stationList = new ArrayList<StationVO>();
		stationList = routeService.getStationsByTrain(trainId);
		if (stationList.isEmpty()) {
			modelAndView.addObject("emptyList", 1);			
			//req.setAttribute("emptyList", 1);
		}
		modelAndView.addObject("haveRun", 0);
		modelAndView.addObject("stationList", stationList);
		//req.setAttribute("haveRun", 0);
		//req.setAttribute("stationList", stationList);
		
		
		//String runStr = req.getParameter("run");
		if (runStr != null) {
			LOG.info("We have Run parameter");
			int runId = Integer.parseInt(runStr);
			
			LOG.info("Getting Timetable with Run.Id " + runId + " on every Station");
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			timetableList = clientService.getTimesFromStationList(runId, stationList);
			if (timetableList.isEmpty()) {
				LOG.info("No time was found");
				modelAndView.addObject("emptyList", 1);	
				//req.setAttribute("emptyList", 1);
			}
			modelAndView.addObject("timetableList", timetableList);	
			modelAndView.addObject("haveRun", 1);	
			//req.setAttribute("timetableList", timetableList);
	    	//req.setAttribute("haveRun", 1);
		}
		return modelAndView;
	}
}
