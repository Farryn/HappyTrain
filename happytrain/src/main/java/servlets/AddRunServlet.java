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

import services.RouteService;
import services.TimetableService;
import valueobjects.StationVO;

/**
 * Servlet implementation class AddRunServlet.
 */
@WebServlet
public class AddRunServlet extends HttpServlet {
	
	/**
	 * Serial id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(AddRunServlet.class);   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRunServlet() {
        super();
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Getting parameters from GET");
		int id = Integer.parseInt(request.getParameter("train"));
		
		log.info("Getting Station list by Train.Id from RouteService");
		List<StationVO> stationList = new ArrayList<StationVO>();
		try {
			stationList = new RouteService().getStationsByTrain(id);
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
			request.setAttribute("emptyList", 1);
		}
		request.setAttribute("stationList", stationList);
		request.setAttribute("train", id);
		request.getRequestDispatcher("/protected/AddRun.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info("Getting parameters from POST");
		String[] arrivalTime = request.getParameterValues("arrivalTime[]");
		String[] departureTime = request.getParameterValues("departureTime[]");
		String[] stationArray = request.getParameterValues("stationList[]");
		int trainId = Integer.parseInt(request.getParameter("train"));
		log.info("Adding Run to DB using TimetableService");
		try {
			new TimetableService().addRun(trainId, stationArray, arrivalTime, departureTime);
			request.setAttribute("fail", 0);
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not add run into DB");
			request.setAttribute("fail", 1);
		}
		request.getRequestDispatcher("/protected/AddRun.jsp").forward(request, response);
		
	}

}
