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
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTrainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */
    private StationVO getStationFromString(String str) {
    	StationVO station = new StationVO();
    	StationService ss = new StationService();
    	station = ss.getStationVOByName(str);
    	return station;
    }
    
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
		String trainNumber = req.getParameter("trainNumber");
		int seatsCount = Integer.parseInt(req.getParameter("seatsCount"));
		String[] stationArray = req.getParameterValues("stationList[]");
		
		List<StationVO> stationList = new ArrayList<StationVO>();
		if (stationArray.length > 0) {
			for (String str: stationArray) {
				stationList.add(getStationFromString(str));
			}
		}
		EmployeeService es = new EmployeeService();
		es.addTrain(trainNumber, seatsCount, stationList);
		
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
		RequestDispatcher rd = sc.getRequestDispatcher("/FindTrain.jsp");
		rd.forward(request, response);
	}

	

}
