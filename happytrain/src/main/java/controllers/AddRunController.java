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

import services.RouteService;
import services.TimetableService;
import util.EmptyResultException;
import valueobjects.StationVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class AddRunController {

	

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(AddRunController.class);
    
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private TimetableService timetableService;
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addrun", method = RequestMethod.GET)
	public ModelAndView processGet(@RequestParam(value="train", required=false) Integer id,
									@RequestParam(value="lang", required = false) String lang) {
		ModelAndView modelAndView = new ModelAndView("protected/AddRun");
		if (lang != null) {
			modelAndView.addObject("emptyList", 1);	
			return modelAndView;
		}
		LOG.info("Getting parameters from GET");
		//int id = Integer.parseInt(request.getParameter("train"));
		
		LOG.info("Getting Station list by Train.Id from RouteService");
		List<StationVO> stationList = new ArrayList<StationVO>();
		stationList = routeService.getStationsByTrain(id);
		if (stationList.isEmpty()) {
			LOG.info("No result was found");
			modelAndView.addObject("emptyList", 1);
			//req.setAttribute("emptyList", 1);
		}
		modelAndView.addObject("stationList", stationList);
		modelAndView.addObject("train", id);
		//req.setAttribute("stationList", stationList);
		//req.setAttribute("train", id);
		return modelAndView;
	}
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addrun", method = RequestMethod.POST)
	public ModelAndView processPost(@RequestParam(value="arrivalTime[]") String[] arrivalTime, 
								@RequestParam(value="departureTime[]") String[] departureTime,
								@RequestParam(value="stationArray[]") String[] stationArray,
								@RequestParam(value="train") int trainId) {
		//LOG.info("Getting parameters from POST");
		//String[] arrivalTime = req.getParameterValues("arrivalTime[]");
		//String[] departureTime = req.getParameterValues("departureTime[]");
		//String[] stationArray = req.getParameterValues("stationList[]");
		//int trainId = Integer.parseInt(req.getParameter("train"));
		ModelAndView modelAndView = new ModelAndView("protected/AddRun");
		LOG.info("Adding Run to DB using TimetableService");
		try {
			timetableService.addRun(trainId, stationArray, arrivalTime, departureTime);
			modelAndView.addObject("fail", 0);
			//req.setAttribute("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Exception: " + e);
			modelAndView.addObject("failMessage", e.getMessage());
			modelAndView.addObject("fail", 1);
			//req.setAttribute("failMessage", e.getMessage());
			//req.setAttribute("fail", 1);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add run into DB");
			modelAndView.addObject("fail", 1);
			//req.setAttribute("fail", 1);
		}
		return modelAndView;
	}
}
