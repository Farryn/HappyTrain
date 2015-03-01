package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.ClientService;
import valueobjects.UserVO;

/**
 * Servlet implementation class BuyTicketServlet
 */
@WebServlet
public class BuyTicketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BuyTicketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String birthDate = req.getParameter("birthDate");
		String trainNumber = req.getParameter("train");
		String stationFrom = req.getParameter("stationFrom");
		String stationTo = req.getParameter("stationTo");
		String depTime = req.getParameter("depTime");
		String runId = req.getParameter("run");
		UserVO user = (UserVO) req.getSession().getAttribute("user");
		//new ClientService().buyTicket(user, firstName, lastName, birthDate, trainNumber, stationFrom, stationTo, depTime, runId);
		new ClientService().buyTicket(user, trainNumber, stationFrom, stationTo, depTime, runId);

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
	}

	

}
