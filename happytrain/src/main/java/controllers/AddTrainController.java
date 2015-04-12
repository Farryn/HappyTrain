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

import services.EmployeeService;
import services.StationService;
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
	private static final Logger LOG = Logger.getLogger(AddTrainController.class);
       
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private StationService stationService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addtrain", method = RequestMethod.GET)
	public ModelAndView processGet() {
		ModelAndView modelAndView = new ModelAndView("protected/AddTrain");
		modelAndView = getStationListAndTime(modelAndView);
		return modelAndView;
	}
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/addtrain", method = RequestMethod.POST)
	public ModelAndView processPost(@RequestParam(value = "trainNumber") String trainNumber,
									@RequestParam(value = "seatsCount") int seatsCount,
									@RequestParam(value = "stationList[]") String[] stationArray) {
		ModelAndView modelAndView = new ModelAndView("protected/AddTrain");
    	if (trainNumber == null || "".equals(trainNumber)) {
    		LOG.warn("Empty train number");
    		modelAndView.addObject("fail", 1);
			//req.setAttribute("fail", 1);
			return modelAndView;
    	}
    	
		if (seatsCount == 0) {
    		LOG.warn("Empty seats count");
    		modelAndView.addObject("fail", 1);
			return modelAndView;
    	}
		
		if (stationArray.length < 2) {
    		LOG.warn("Not enough stations count");
    		modelAndView.addObject("fail", 1);
			return modelAndView;
    	}
		
		LOG.info("Adding Train into DB");
		try {
			employeeService.addTrain(trainNumber, seatsCount, stationArray);
			modelAndView.addObject("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Exception: " + e);
			modelAndView.addObject("failMessage", e.getMessage());
			modelAndView.addObject("fail", 1);
		} catch (Exception e) {
			modelAndView.addObject("fail", 1);
			LOG.error("Exception: " + e);
			LOG.warn("Could not add train into DB");
			
		}
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
