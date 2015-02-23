package servlets;

import java.io.IOException;
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

import services.ClientService;
import services.StationService;
import entities.Run;
import entities.Station;

/**
 * Servlet implementation class ShowFoundTrainsServlet
 */
@WebServlet
public class ShowFoundTrainsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFoundTrainsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */
    private Date getDateFromString(String str){
    	Date date = new Date();
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return date;
    }
    
    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */
    private Station getStationFromString(String str){
    	Station station = new Station();
    	StationService ss=new StationService();
    	station=ss.getStationByName(str);
    	return station;
    }
    
    /**
     * Get data from request and process it
     * @param req Request Object
     * @param res Response Object 
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	String stationA = req.getParameter("stationFrom");
		String stationB = req.getParameter("stationTo");
		Date from = getDateFromString(req.getParameter("from"));
		Date to = getDateFromString(req.getParameter("to"));
		
		Station stationFrom = getStationFromString(req.getParameter("stationFrom"));
		Station stationTo = getStationFromString(req.getParameter("stationTo"));
		
		List<Date> departureDateTime = new ArrayList<Date>(); 
		List<Date> arrivalDateTime = new ArrayList<Date>();
		List<Integer> availableSeats = new ArrayList<Integer>();
		List<Long> timeInTrip = new ArrayList<Long>();
		ClientService cs = new ClientService();
		List<Run> runList = cs.searchTrain(stationFrom, stationTo, from, to);
		
		for(Run run:runList){
			Date departureTime=cs.getStationDepTime(stationFrom, run);
			Date arrivalTime=cs.getStationArrTime(stationTo, run);
			int count=cs.getStationAvailableSeats(stationFrom, run);
			long duration=arrivalTime.getTime()-departureTime.getTime();
			long difference=TimeUnit.MILLISECONDS.toHours(duration);
			
			departureDateTime.add(departureTime);
			arrivalDateTime.add(arrivalTime);
			availableSeats.add(count);
			timeInTrip.add(difference);
		}
		req.setAttribute("stationFrom", stationFrom);
		req.setAttribute("stationTo", stationFrom);
    	req.setAttribute("runList", runList);
    	req.setAttribute("departureDateTime", departureDateTime);
    	req.setAttribute("arrivalDateTime", arrivalDateTime);
    	req.setAttribute("availableSeats", availableSeats);
    	req.setAttribute("timeInTrip", timeInTrip);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
		
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowFoundTrain.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
