package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.TimetableService;
import valueobjects.TimetableVO;

/**
 * Servlet implementation class ShowTimetable.
 */
@WebServlet
public class ShowTimetableServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowTimetableServlet.class);
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTimetableServlet() {
    }

    

    /** Process data from request.
     * @param req HttpServletRequest Object
     */
    private void processRequest(HttpServletRequest req){
    	
		String station = req.getParameter("station");
		if (station == null) {
			req.setAttribute("haveResult", 0);
		} else {
			processForm(req);
		}
    	
    }
    
    /** Process data from form.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
	private void processForm(HttpServletRequest req) {
		LOG.info("Getting parameters from form");
		String stationA = req.getParameter("station");
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		
		LOG.info("Getting Timetables by Station " + stationA + " between "+ from + "and" + to);
		TimetableService ts = new TimetableService();
		List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
		try {
			timetableList = ts.getTimetableByStation(stationA, from, to);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("haveResult", 1);
		req.setAttribute("station", stationA);
		req.setAttribute("timetableList", timetableList);
		req.setAttribute("from", from);
		req.setAttribute("to", to);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowTimetable.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processForm(request);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowTimetable.jsp");
		rd.forward(request, response);
	}

}
