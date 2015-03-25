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

import services.EmployeeService;
import services.StationService;
import servlets.ShowFoundTrainsServlet;
import util.EmptyResultException;
import valueobjects.StationVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class AddTrainController {

	/**
	 * default constructor
	 */
	public AddTrainController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private StationService stationService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addtrain", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		getStationListAndTime(req);
		return "protected/AddTrain";
	}
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addtrain", method = RequestMethod.POST)
	public String processPost(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
    	String trainNumber = req.getParameter("trainNumber");
    	if (trainNumber == null || "".equals(trainNumber)) {
    		LOG.warn("Empty train number");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
    	
		int seatsCount = Integer.parseInt(req.getParameter("seatsCount"));
		if (seatsCount == 0) {
    		LOG.warn("Empty seats count");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
		
		String[] stationArray = req.getParameterValues("stationList[]");
		if (stationArray.length < 2) {
    		LOG.warn("Not enough stations count");
			req.setAttribute("fail", 1);
			return "protected/AddTrain";
    	}
		
		LOG.info("Adding Train into DB");
		try {
			employeeService.addTrain(trainNumber, seatsCount, stationArray);
			req.setAttribute("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Exception: " + e);
			req.setAttribute("failMessage", e.getMessage());
			req.setAttribute("fail", 1);
		} catch (Exception e) {
			req.setAttribute("fail", 1);
			LOG.error("Exception: " + e);
			LOG.warn("Could not add train into DB");
			
		}
		return "protected/AddTrain";
	}
	
	private void getStationListAndTime(HttpServletRequest req){
		List<StationVO> stationList = new ArrayList<StationVO>();
	    String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		stationList = stationService.getAllStationVO();
		if (stationList.isEmpty()) {
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
