/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.ClientService;
import servlets.ShowFoundTrainsServlet;
import util.EmptyResultException;
import valueobjects.UserVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class BuyTicketController {

	/**
	 * default constructor
	 */
	public BuyTicketController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	@Autowired
	private ClientService clientService;
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/buyticket", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
		String stationFrom = req.getParameter("stationFrom");
		String stationTo = req.getParameter("stationTo");
		String depTime = req.getParameter("depTime");
		String runId = req.getParameter("run");
		UserVO user = (UserVO) req.getSession().getAttribute("user");
		LOG.info("Buying Ticket using ClientService");
		try {
			clientService.buyTicket(user, stationFrom, stationTo, depTime, runId);
			req.setAttribute("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Exception: " + e);
			req.setAttribute("failMessage", e.getMessage());
			req.setAttribute("fail", 1);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add Ticket");
			req.setAttribute("failMessage", "Ошибка при покупке билета");
			req.setAttribute("fail", 1);
		}
		return "protected/BuyTicket";
	}
}
