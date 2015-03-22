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

import services.ClientService;
import servlets.ShowFoundTrainsServlet;
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
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/showtrain", method = RequestMethod.GET)
	public String processRequest(HttpServletRequest req) {
			LOG.info("Getting parameters from form");
	    	String stationA = req.getParameter("stationFrom");
			String stationB = req.getParameter("stationTo");
			String from = req.getParameter("from");
			String to = req.getParameter("to");
			
			LOG.info("Getting timetable list from ClientService");
			ClientService cs = new ClientService();
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			try {
				timetableList = cs.searchTrain(stationA, stationB, from, to);
			} catch (Exception e) {
				LOG.warn("Exception: " + e);
				LOG.info("No result was found");
				req.setAttribute("emptyList", 1);
			}
			
			req.setAttribute("haveResult", 1);
			req.setAttribute("stationFrom", stationA);
			req.setAttribute("stationTo", stationB);
			req.setAttribute("from", from);
			req.setAttribute("to", to);
	    	req.setAttribute("timetableList", timetableList);
	    	return "FindTrain";
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startRequest(HttpServletRequest req) {
		req.setAttribute("haveResult", 0);
		return "FindTrain";
	}
}
