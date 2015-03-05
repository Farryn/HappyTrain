package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.TicketService;
import valueobjects.TicketVO;

/**
 * Servlet implementation class ShowPassengerServlet.
 */
@WebServlet
public class ShowPassengerServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(ShowPassengerServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPassengerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res) {
    	log.info("Getting parameters from GET");
		int runId = Integer.parseInt(req.getParameter("run"));
		
		log.info("Getting passenger list from TicketService");
		List<TicketVO> passengerList = new ArrayList<TicketVO>();
		try {
			passengerList = new TicketService().getTicketsByRunId(runId);
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("passengerList", passengerList);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		request.getRequestDispatcher("/protected/ShowPassenger.jsp").forward(request, response);
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
