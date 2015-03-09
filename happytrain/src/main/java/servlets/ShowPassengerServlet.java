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
	private static final Logger LOG = Logger.getLogger(ShowPassengerServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPassengerServlet() {
    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req) {
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
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		request.getRequestDispatcher("/protected/ShowPassenger.jsp").forward(request, response);
	}

	

	

}
