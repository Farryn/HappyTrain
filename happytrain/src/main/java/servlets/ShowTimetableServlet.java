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
import services.TimetableService;
import entities.Run;
import entities.Station;
import entities.Train;

/**
 * Servlet implementation class ShowTimetable
 */
@WebServlet
public class ShowTimetableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
    
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	StationService ss=new StationService();
		List<Station> stationList = ss.getAllStations();
		req.setAttribute("stationList",stationList);
		
		String station=req.getParameter("station");
		if(station==null){
			req.setAttribute("haveResult", 0);
		}else{
			processForm(req,res);
		}
		req.setAttribute("stationList", stationList);
    	
    }
	private void processForm(HttpServletRequest req, HttpServletResponse res) {
		String stationA = req.getParameter("station");
		Date from = getDateFromString(req.getParameter("from"));
		Date to = getDateFromString(req.getParameter("to"));
		
		Station station = getStationFromString(stationA);
		
		List<Date> departureDateTime = new ArrayList<Date>(); 
		List<Date> arrivalDateTime = new ArrayList<Date>();
		
		ClientService cs = new ClientService();
		TimetableService ts = new TimetableService();
		
		List<Run> runList = ts.getTimetableFromStation(station, from, to);
		if (!runList.isEmpty()) {
			for (Run run: runList) {
				Date departureTime=cs.getStationDepTime(station, run);
				Date arrivalTime=cs.getStationArrTime(station, run);
				departureDateTime.add(departureTime);
				arrivalDateTime.add(arrivalTime);
				
			}
		}
		req.setAttribute("haveResult", 1);
		req.setAttribute("station", station);
		req.setAttribute("runList", runList);
    	req.setAttribute("departureDateTime", departureDateTime);
    	req.setAttribute("arrivalDateTime", arrivalDateTime);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
		ServletContext sc = getServletContext();
		//RequestDispatcher rd = sc.getRequestDispatcher("/ShowFoundTrain.jsp");
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowTimetable.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
