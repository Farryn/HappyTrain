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

import services.TrainService;
import valueobjects.TrainVO;

/**
 * Servlet implementation class ShowAllTrainsServlet.
 */
@WebServlet
public class ShowAllTrainsServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(ShowAllTrainsServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllTrainsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Get data from request and process it.
     * @param req Request Object
     * @param res Response Object 
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	log.info("Getting all trains from db");
    	TrainService ts = new TrainService();
    	List<TrainVO> trainList = new ArrayList<TrainVO>();
		try {
			trainList = ts.getAllTrains();
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
    	req.setAttribute("trainList", trainList);
    	
    	StringBuffer url = req.getRequestURL();
    	req.setAttribute("servletUrl", url);
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/protected/ShowAllTrains.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
