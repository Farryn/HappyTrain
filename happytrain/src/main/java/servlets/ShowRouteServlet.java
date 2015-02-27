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

import services.ClientService;
import services.RouteService;
import services.RunService;
import services.TrainService;
import valueobjects.RunVO;
import valueobjects.StationVO;
import valueobjects.TrainVO;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * Servlet implementation class ShowRoutesServlet
 */
@WebServlet
public class ShowRouteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRouteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
    	String trainStr = req.getParameter("train");
		int trainId = Integer.parseInt(trainStr);
		TrainService trainService = new TrainService();
		TrainVO train = trainService.getTrainVOById(trainId);
    	
		RouteService routeService = new RouteService();
		List<StationVO> stationList = routeService.getStationsByTrain(train);
		req.setAttribute("haveRun", 0);
		req.setAttribute("stationList", stationList);
		
		String runStr = req.getParameter("run");
		if (runStr != null) {
			int runId = Integer.parseInt(runStr);
			RunService runService = new RunService();
			RunVO run = runService.getRunVOById(runId);
			
			List<Date> departureDateTime = new ArrayList<Date>(); 
			List<Date> arrivalDateTime = new ArrayList<Date>();
			ClientService clientService = new ClientService();
			
			if (!stationList.isEmpty()) {
				for (StationVO station:stationList) {
					Date departureTime = clientService.getStationDepTime(station, run);
					Date arrivalTime = clientService.getStationArrTime(station, run);
					
					departureDateTime.add(departureTime);
					arrivalDateTime.add(arrivalTime);
					
				}
			}
			req.setAttribute("departureDateTime", departureDateTime);
	    	req.setAttribute("arrivalDateTime", arrivalDateTime);
	    	req.setAttribute("haveRun", 1);
		}
		
		
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowRoute.jsp");
		rd.forward(request, response);
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
