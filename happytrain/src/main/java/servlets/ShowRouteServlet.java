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
		int trainId = Integer.parseInt(req.getParameter("train"));
		int runId = Integer.parseInt(req.getParameter("run"));
		
		TrainService trainService = new TrainService();
		Train train = trainService.getTrainById(trainId);
		
		RunService runService = new RunService();
		Run run = runService.getRunById(runId);
		
		RouteService routeService = new RouteService();
		List<Station> stationList = routeService.getStationsByTrain(train);
		
		List<Date> departureDateTime = new ArrayList<Date>(); 
		List<Date> arrivalDateTime = new ArrayList<Date>();
		ClientService clientService = new ClientService();
		
		if (!stationList.isEmpty()) {
			for (Station station:stationList) {
				Date departureTime=clientService.getStationDepTime(station, run);
				Date arrivalTime=clientService.getStationArrTime(station, run);
				
				departureDateTime.add(departureTime);
				arrivalDateTime.add(arrivalTime);
				
			}
		}
		req.setAttribute("stationList", stationList);
		req.setAttribute("departureDateTime", departureDateTime);
    	req.setAttribute("arrivalDateTime", arrivalDateTime);
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
