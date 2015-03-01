package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.RouteService;
import services.TimetableService;
import valueobjects.StationVO;

/**
 * Servlet implementation class AddRunServlet
 */
@WebServlet
public class AddRunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddRunServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("train"));
		List<StationVO> stationList = new RouteService().getStationsByTrain(id);
		request.setAttribute("stationList", stationList);
		request.setAttribute("train", id);
		request.getRequestDispatcher("/protected/AddRun.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] arrivalTime = request.getParameterValues("arrivalTime[]");
		String[] departureTime = request.getParameterValues("departureTime[]");
		String[] stationArray = request.getParameterValues("stationList[]");
		int trainId = Integer.parseInt(request.getParameter("train"));
		new TimetableService().addRun(trainId, stationArray, arrivalTime, departureTime);
		request.getRequestDispatcher("/FindTrain.jsp").forward(request, response);
		
	}

}
