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

import services.EmployeeService;

/**
 * Servlet implementation class AddTrainServlet.
 */
@WebServlet
public class AddTrainServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(AddTrainServlet.class);   
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTrainServlet() {
        
    }

    
   
    /**
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from form");
    	String trainNumber = req.getParameter("trainNumber");
    	if (trainNumber == null || "".equals(trainNumber)) {
    		LOG.warn("Empty train number");
			req.setAttribute("fail", 1);
			return;
    	}
    	
		int seatsCount = Integer.parseInt(req.getParameter("seatsCount"));
		if (seatsCount == 0) {
    		LOG.warn("Empty seats count");
			req.setAttribute("fail", 1);
			return;
    	}
		
		String[] stationArray = req.getParameterValues("stationList[]");
		if (stationArray.length < 2) {
    		LOG.warn("Not enough stations count");
			req.setAttribute("fail", 1);
			return;
    	}
		
		LOG.info("Adding Train into DB");
		try {
			new EmployeeService().addTrain(trainNumber, seatsCount, stationArray);
			req.setAttribute("fail", 0);
		} catch (Exception e) {
			req.setAttribute("fail", 1);
			LOG.error("Exception: " + e);
			LOG.warn("Could not add train into DB");
			
		}
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	ServletContext sc = getServletContext();
    	processRequest(request);
		RequestDispatcher rd = sc.getRequestDispatcher("/protected/AddTrain.jsp");
		rd.forward(request, response);
	}

	

}
