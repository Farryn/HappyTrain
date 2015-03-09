package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.ClientService;
import util.MyException;
import valueobjects.UserVO;

/**
 * Servlet implementation class BuyTicketServlet.
 */
@WebServlet
public class BuyTicketServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(BuyTicketServlet.class);   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyTicketServlet() {
    }
    
    /**
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from form");
		String stationFrom = req.getParameter("stationFrom");
		String stationTo = req.getParameter("stationTo");
		String depTime = req.getParameter("depTime");
		String runId = req.getParameter("run");
		UserVO user = (UserVO) req.getSession().getAttribute("user");
		LOG.info("Buying Ticket using ClientService");
		try {
			new ClientService().buyTicket(user, stationFrom, stationTo, depTime, runId);
			req.setAttribute("fail", 0);
		} catch (MyException e) {
			LOG.warn("Exception: " + e);
			req.setAttribute("failMessage", e.getMessage());
			req.setAttribute("fail", 1);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add Ticket");
			req.setAttribute("failMessage", "Ошибка при покупке билета");
			req.setAttribute("fail", 1);
		}

	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		request.getRequestDispatcher("/protected/BuyTicket.jsp").forward(request, response);
	}

	

}
