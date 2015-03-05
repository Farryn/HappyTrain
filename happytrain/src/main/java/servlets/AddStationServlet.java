package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import entities.Station;
import services.StationService;

/**
 * Servlet implementation class AddStationServlet
 */
@WebServlet
public class AddStationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(AddStationServlet.class);  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
    	log.info("Getting parameters from form");
    	String stationName = req.getParameter("stationName");
    	if (stationName == null || stationName.equals("")) {
    		log.warn("Empty station name");
			req.setAttribute("fail", 1);
			return;
    	}
    	log.info("Adding Station into DB");
    	try {
			new StationService().addStation(stationName);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			log.error("Exception: " + e);
			log.warn("Could not add station into DB");
			req.setAttribute("fail", 1);
		}
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
