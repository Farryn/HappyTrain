package servlets;

import java.io.IOException;
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
import services.RouteService;
import services.RunService;
import services.TrainService;
import valueobjects.RunVO;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.TrainVO;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * Servlet implementation class ShowRoutesServlet
 */
@WebServlet
public class ShowRouteServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowRouteServlet.class);   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRouteServlet() {
    }
    
    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from GET");
    	String trainStr = req.getParameter("train");
		int trainId = Integer.parseInt(trainStr);
		
		LOG.info("Getting Stations from RouteService");
		RouteService routeService = new RouteService();
		List<StationVO> stationList = new ArrayList<StationVO>();
		try {
			stationList = routeService.getStationsByTrain(trainId);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("haveRun", 0);
		req.setAttribute("stationList", stationList);
		
		
		String runStr = req.getParameter("run");
		if (runStr != null) {
			LOG.info("We have Run parameter");
			int runId = Integer.parseInt(runStr);
			
			LOG.info("Getting Timetable with Run.Id " + runId + " on every Station");
			ClientService clientService = new ClientService();
			List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
			try {
				timetableList = clientService.getTimesFromStationList(runId, stationList);
			} catch (Exception e) {
				LOG.warn("Exception: " + e);
				LOG.info("No time was found");
				req.setAttribute("emptyList", 1);
			}
			
			req.setAttribute("timetableList", timetableList);
	    	req.setAttribute("haveRun", 1);
		}
		
		
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowRoute.jsp");
		rd.forward(request, response);
	}

	

	

}
