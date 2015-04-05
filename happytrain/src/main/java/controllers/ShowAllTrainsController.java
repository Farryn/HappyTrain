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
import org.springframework.web.servlet.ModelAndView;

import services.TrainService;
import valueobjects.TrainVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowAllTrainsController {

	/**
	 * default constructor
	 */
	public ShowAllTrainsController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowAllTrainsController.class);
       
	@Autowired
	private TrainService trainService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/alltrains", method = RequestMethod.GET)
	public ModelAndView processGet() {
		ModelAndView modelAndView = new ModelAndView("protected/ShowAllTrains");
		LOG.info("Getting all trains from db");
    	List<TrainVO> trainList = new ArrayList<TrainVO>();
		trainList = trainService.getAllTrains();
		if (trainList.isEmpty()) {
			LOG.info("No result was found");
			modelAndView.addObject("emptyList", 1);
			//req.setAttribute("emptyList", 1);
		}
		modelAndView.addObject("trainList", trainList);
    	//req.setAttribute("trainList", trainList);
    	
    	//String url = req.getRequestURL().toString();
    	//req.setAttribute("servletUrl", url);
		return modelAndView;
	}
}
