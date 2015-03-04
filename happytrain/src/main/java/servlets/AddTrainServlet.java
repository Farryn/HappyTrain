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

import services.EmployeeService;
import services.RouteService;
import services.StationService;
import services.TrainService;
import valueobjects.RouteVO;
import valueobjects.StationVO;
import valueobjects.TrainVO;
import entities.Route;
import entities.Station;
import entities.Train;

/**
 * Servlet implementation class AddTrainServlet
 */
@WebServlet
public class AddTrainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AddTrainServlet.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTrainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
   
    
    
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
    	log.info("Getting parameters from form");
    	String trainNumber = req.getParameter("trainNumber");
		int seatsCount = Integer.parseInt(req.getParameter("seatsCount"));
		String[] stationArray = req.getParameterValues("stationList[]");
		
		log.info("Adding Train into DB");
		try {
			new EmployeeService().addTrain(trainNumber, seatsCount, stationArray);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not add train into DB");
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
		processRequest(request,response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/protected/AddTrain.jsp");
		rd.forward(request, response);
	}

	

}
