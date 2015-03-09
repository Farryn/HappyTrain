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

import services.StationService;

/**
 * Servlet implementation class AddStationServlet.
 */
@WebServlet
public class AddStationServlet extends HttpServlet {
	
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(AddStationServlet.class);  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddStationServlet() {
        super();

    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     */
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from form");
    	String stationName = req.getParameter("stationName");
    	if (stationName == null || "".equals(stationName)) {
    		LOG.warn("Empty station name");
			req.setAttribute("fail", 1);
			return;
    	}
    	LOG.info("Adding Station into DB");
    	try {
			new StationService().addStation(stationName);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			LOG.error("Exception: " + e);
			LOG.warn("Could not add station into DB");
			req.setAttribute("fail", 1);
		}
	}
    
	


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/protected/AddTrain.jsp");
		rd.forward(request, response);
	}

}
