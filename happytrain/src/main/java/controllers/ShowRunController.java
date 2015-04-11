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

import services.RunService;
import valueobjects.RunVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowRunController {

	/**
	 * 
	 */
	public ShowRunController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowRunController.class);
       
	@Autowired
	private RunService runService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public ModelAndView processGet(@RequestParam(value="train", required=false) Integer trainId,
									@RequestParam(value="lang", required = false) String lang) {
		ModelAndView modelAndView = new ModelAndView("protected/ShowRun");
		if (lang != null) {
			modelAndView.addObject("emptyList", 1);	
			return modelAndView;
		}
		//LOG.info("Getting parameters from GET");
    	//int trainId = Integer.parseInt(req.getParameter("train"));
    	
    	LOG.info("Getting Runs list from RunService");
		List<RunVO> runList = new ArrayList<RunVO>();
		runList = runService.getRunByTrainId(trainId);
		if (runList.isEmpty()) {
			LOG.info("No result was found");
			modelAndView.addObject("emptyList", 1);
			//req.setAttribute("emptyList", 1);
		}
		modelAndView.addObject("runList", runList);
		//req.setAttribute("runList", runList);
		return modelAndView;
	}
}
