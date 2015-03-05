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

import services.ClientService;
import valueobjects.TimetableVO;

/**
 * Servlet implementation class ShowFoundTrainsServlet.
 */
@WebServlet
public class ShowFoundTrainsServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(ShowFoundTrainsServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowFoundTrainsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	
    	log.info("Getting parameters from form");
    	String stationA = req.getParameter("stationFrom");
		String stationB = req.getParameter("stationTo");
		String from = req.getParameter("from");
		String to = req.getParameter("to");
		
		log.info("Getting timetable list from ClientService");
		ClientService cs = new ClientService();
		List<TimetableVO> timetableList = new ArrayList<TimetableVO>();
		try {
			timetableList = cs.searchTrain(stationA, stationB, from, to);
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
			req.setAttribute("emptyList", 1);
			//e.printStackTrace();
		}
		
		req.setAttribute("haveResult", 1);
		req.setAttribute("stationFrom", stationA);
		req.setAttribute("stationTo", stationB);
		req.setAttribute("from", from);
		req.setAttribute("to", to);
    	req.setAttribute("timetableList", timetableList);
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("stationFrom") != null) {
			processRequest(request, response);
		} else {
			request.setAttribute("haveResult", 0);
		}
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/FindTrain.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
