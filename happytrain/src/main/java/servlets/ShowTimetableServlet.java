package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.ClientService;
import services.StationService;
import services.TimetableService;
import valueobjects.RunVO;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * Servlet implementation class ShowTimetable
 */
@WebServlet
public class ShowTimetableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ShowTimetableServlet.class);
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowTimetableServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */

    
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	
		String station = req.getParameter("station");
		if (station == null) {
			req.setAttribute("haveResult", 0);
		} else {
			processForm(req, res);
		}
    	
    }
    
	private void processForm(HttpServletRequest req, HttpServletResponse res) {
		
		log.info("Getting parameters from form");
		String stationA = req.getParameter("station");
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		
		
		log.info("Getting Timetables by Station " + stationA + " between "+ from + "and" + to);
		TimetableService ts = new TimetableService();
		List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
		try {
			timetableList = ts.getTimetableByStation(stationA, from, to);
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowTimetable.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processForm(request,response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowTimetable.jsp");
		rd.forward(request, response);
	}

}
