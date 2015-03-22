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
import services.RouteService;
import services.TicketService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.StationVO;
import valueobjects.TicketVO;
import valueobjects.TimetableVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowPassengerController {

	/**
	 * default constructor
	 */
	public ShowPassengerController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/passenger", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting parameters from GET");
		int runId = Integer.parseInt(req.getParameter("run"));
		
		LOG.info("Getting passenger list from TicketService");
		List<TicketVO> passengerList = new ArrayList<TicketVO>();
		try {
			passengerList = new TicketService().getTicketsByRunId(runId);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("passengerList", passengerList);
		return "protected/ShowPassenger";
	}
}
