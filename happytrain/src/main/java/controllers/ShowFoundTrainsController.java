/**
 * 
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.ClientService;
import services.StationService;
import servlets.ShowFoundTrainsServlet;
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
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
    
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
	@RequestMapping(value = "/showtrain", method = RequestMethod.GET)
	public String processRequest(HttpServletRequest req) {
			LOG.info("Getting parameters from form");
	    	String stationA = req.getParameter("stationFrom");
			String stationB = req.getParameter("stationTo");
			String from = req.getParameter("from");
			String to = req.getParameter("to");
			
			LOG.info("Getting timetable list from ClientService");
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			try {
				timetableList = clientService.searchTrain(stationA, stationB, from, to);
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
	    	getStationListAndTime(req);
	    	return "FindTrain";
	}

	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startRequest(HttpServletRequest req) {
		getStationListAndTime(req);
		req.setAttribute("haveResult", 0);
		return "FindTrain";
	}
	
	private void getStationListAndTime(HttpServletRequest req){
		List<StationVO> stationList = new ArrayList<StationVO>();
	    String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		try {
			stationList = stationService.getAllStationVO();
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No station was found");
		}
		if (req.getParameter("from") != null) {
			req.setAttribute("from", req.getParameter("from"));
		} else {
			req.setAttribute("from", strDate);
		}
		if (req.getParameter("to") != null) {
			req.setAttribute("to", req.getParameter("to"));
		} else {
			req.setAttribute("to", strDate);
		}
		req.setAttribute("stationList", stationList);
	}
}
