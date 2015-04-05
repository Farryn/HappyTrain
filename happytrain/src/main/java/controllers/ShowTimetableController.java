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

import services.StationService;
import services.TimetableService;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
 
/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowTimetableController {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowTimetableController.class);
       
	/**
	 * 
	 */
	public ShowTimetableController() {
	}
	
	
	@Autowired
	private TimetableService timetableService;
	
	@Autowired
	private StationService stationService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	public ModelAndView processGet() {
		ModelAndView modelAndView = new ModelAndView("ShowTimetable");
		modelAndView = getStationListAndTime(modelAndView);
		modelAndView.addObject("haveResult", 0);
		//req.setAttribute("haveResult", 0);
	    return modelAndView;
	}

	
	@RequestMapping(value = "/timetable", method = RequestMethod.POST)
	public ModelAndView processPost(@RequestParam(value="station") String stationA,
									@RequestParam(value="from") String from,
									@RequestParam(value="to") String to) {
		ModelAndView modelAndView = new ModelAndView("ShowTimetable");
		modelAndView = getStationListAndTime(modelAndView);
		/*LOG.info("Getting parameters from form");
		String stationA = req.getParameter("station");
		String from = req.getParameter("from");
		String to = req.getParameter("to");*/
		
		LOG.info("Getting Timetables by Station " + stationA + " between "+ from + "and" + to);
		List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
		timetableList = timetableService.getTimetableByStation(stationA, from, to);
		if (timetableList.isEmpty()) {
			LOG.info("No result was found");
			modelAndView.addObject("emptyList", 1);
			//req.setAttribute("emptyList", 1);
		}
		modelAndView.addObject("haveResult", 1);
		modelAndView.addObject("station", stationA);
		modelAndView.addObject("timetableList", timetableList);
		modelAndView.addObject("from", from);
		modelAndView.addObject("to", to);
		
		/*req.setAttribute("haveResult", 1);
		req.setAttribute("station", stationA);
		req.setAttribute("timetableList", timetableList);
		req.setAttribute("from", from);
		req.setAttribute("to", to);*/
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
	}
}
