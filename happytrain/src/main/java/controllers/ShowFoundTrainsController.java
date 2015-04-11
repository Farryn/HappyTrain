/**
 * 
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
import services.StationService;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
 
/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowFoundTrainsController {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsController.class);
    
	@Autowired
	private StationService stationService;// = new StationService();
	
	@Autowired
	private ClientService clientService;
	/**
	 * @param stationService the stationService to set
	 */
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}


	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/showtrain", method = RequestMethod.POST)
	public ModelAndView processRequest(@RequestParam(value="stationFrom", required=false) String stationA,
								@RequestParam(value="stationTo", required=false) String stationB,
								@RequestParam(value="from", required=false) String from,
								@RequestParam(value="to", required=false) String to) {
			//LOG.info("Getting parameters from form");
	    	//String stationA = req.getParameter("stationFrom");
			//String stationB = req.getParameter("stationTo");
			//String from = req.getParameter("from");
			//String to = req.getParameter("to");
			ModelAndView modelAndView = new ModelAndView("FindTrain");
			modelAndView = getStationListAndTime(modelAndView);
			/*if (lang != null) {
				modelAndView.addObject("haveResult", 0);
				return modelAndView;
			}*/
			LOG.info("Getting timetable list from ClientService");
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			timetableList = clientService.searchTrain(stationA, stationB, from, to);
			if (timetableList.isEmpty()) {
				LOG.info("No result was found");
				modelAndView.addObject("emptyList", 1);
				//req.setAttribute("emptyList", 1);
			}
			modelAndView.addObject("haveResult", 1);
			modelAndView.addObject("stationFrom", stationA);
			modelAndView.addObject("stationTo", stationB);
			modelAndView.addObject("from", from);
			modelAndView.addObject("to", to);
			modelAndView.addObject("timetableList", timetableList);
			
			/*req.setAttribute("haveResult", 1);
			req.setAttribute("stationFrom", stationA);
			req.setAttribute("stationTo", stationB);
			req.setAttribute("from", from);
			req.setAttribute("to", to);
	    	req.setAttribute("timetableList", timetableList);*/
			
	    	return modelAndView;
	}

	@RequestMapping(value = "/showtrain", method = RequestMethod.GET)
	public ModelAndView langRequest() {
		return process();
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView startRequest() {
		return process();
	}
	
	private ModelAndView process() {
		ModelAndView modelAndView = new ModelAndView("FindTrain");
		modelAndView = getStationListAndTime(modelAndView);
		modelAndView.addObject("haveResult", 0);
		//req.setAttribute("haveResult", 0);
		return modelAndView;
	}
	private ModelAndView getStationListAndTime(ModelAndView modelAndView){
		List<StationVO> stationList = new ArrayList<StationVO>();
	    String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		stationList = stationService.getAllStationVO();
		if (stationList.isEmpty()) {
			LOG.info("No station was found");
		}
		Map<String, Object> map = modelAndView.getModel();
		if (!map.containsKey("from")) {
			modelAndView.addObject("from", strDate);
		}
		if (!map.containsKey("to")) {
			modelAndView.addObject("to", strDate);
		}
		modelAndView.addObject("stationList", stationList);
		return modelAndView;
		/*if (req.getParameter("from") != null) {
			req.setAttribute("from", req.getParameter("from"));
		} else {
			req.setAttribute("from", strDate);
		}
		if (req.getParameter("to") != null) {
			req.setAttribute("to", req.getParameter("to"));
		} else {
			req.setAttribute("to", strDate);
		}
		req.setAttribute("stationList", stationList);*/
	}
}
