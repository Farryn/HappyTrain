/**
 * 
 */
package controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ClientService;
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
	private static final Logger LOG = Logger.getLogger(BuyTicketController.class);
       
	
	@Autowired
	private ClientService clientService;
	
	/** Process data from request.
     * @return page 
     */
	@RequestMapping(value = "/buyticket", method = RequestMethod.GET)
	public String processGet() {
		return "protected/BuyTicket";
	}
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/buyticket", method = RequestMethod.POST)
	public ModelAndView processPost(@RequestParam(value="stationFrom") String stationFrom,
									@RequestParam(value="stationTo") String stationTo,
									@RequestParam(value="depTime") String depTime,
									@RequestParam(value="run") String runId) {
		ModelAndView modelAndView = new ModelAndView("protected/BuyTicket");
		/*LOG.info("Getting parameters from form");
		String stationFrom = req.getParameter("stationFrom");
		String stationTo = req.getParameter("stationTo");
		String depTime = req.getParameter("depTime");
		String runId = req.getParameter("run");*/
		User principal = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOG.info("Buying Ticket using ClientService");
		try {
			clientService.buyTicket(principal.getUsername(), stationFrom, stationTo, depTime, runId);
			modelAndView.addObject("fail", 0);
			//req.setAttribute("fail", 0);
		} catch (EmptyResultException e) {
			LOG.warn("Exception: " + e);
			switch(e.getMessage()) {
				case "Нет свободных мест": modelAndView.addObject("fail", 1);break;
				case "Вы уже зарегистрированы на этот поезд": modelAndView.addObject("fail", 2);break;
				case "До отправления поезда осталось менее 10 минут": modelAndView.addObject("fail", 3);break;
				default: modelAndView.addObject("fail", 4);
			}
			
			//req.setAttribute("failMessage", e.getMessage());
			//req.setAttribute("fail", 1);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add Ticket");
			modelAndView.addObject("failMessage", "Ошибка при покупке билета");
			modelAndView.addObject("fail", 4);
			//req.setAttribute("failMessage", "Ошибка при покупке билета");
			//req.setAttribute("fail", 1);
		}
		return modelAndView;
	}
}
