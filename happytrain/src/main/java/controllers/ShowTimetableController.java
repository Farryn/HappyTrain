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

import services.TimetableService;
import servlets.ShowFoundTrainsServlet;
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
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	/**
	 * 
	 */
	public ShowTimetableController() {
	}
	
	
	@Autowired
	private TimetableService timetableService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/timetable", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		req.setAttribute("haveResult", 0);
	    return "ShowTimetable";
	}

	
	@RequestMapping(value = "/timetable", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
		String stationA = req.getParameter("station");
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		
		LOG.info("Getting Timetables by Station " + stationA + " between "+ from + "and" + to);
		List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
		try {
			timetableList = timetableService.getTimetableByStation(stationA, from, to);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("haveResult", 1);
		req.setAttribute("station", stationA);
		req.setAttribute("timetableList", timetableList);
		req.setAttribute("from", from);
		req.setAttribute("to", to);
		return "ShowTimetable";
	}
}
