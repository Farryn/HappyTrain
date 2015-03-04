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
 * Servlet implementation class BuyTicketServlet
 */
@WebServlet
public class BuyTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(BuyTicketServlet.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
    	log.info("Getting parameters from form");
		/*String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String birthDate = req.getParameter("birthDate");
		String trainNumber = req.getParameter("train");*/
		String stationFrom = req.getParameter("stationFrom");
		String stationTo = req.getParameter("stationTo");
		String depTime = req.getParameter("depTime");
		String runId = req.getParameter("run");
		UserVO user = (UserVO) req.getSession().getAttribute("user");
		log.info("Buying Ticket using ClientService");
		try {
			new ClientService().buyTicket(user, stationFrom, stationTo, depTime, runId);
			req.setAttribute("fail", 0);
		} catch (MyException e) {
			log.warn("Exception: " + e);
			req.setAttribute("failMessage", e.getMessage());
			req.setAttribute("fail", 1);
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not add Ticket");
			req.setAttribute("failMessage", "Ошибка при покупке билета");
			req.setAttribute("fail", 1);
		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		request.getRequestDispatcher("/protected/BuyTicket.jsp").forward(request, response);
	}

	

}
