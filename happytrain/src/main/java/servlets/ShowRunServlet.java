package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import services.RunService;
import valueobjects.RunVO;

/**
 * Servlet implementation class ShowRunServlet.
 */
@WebServlet
public class ShowRunServlet extends HttpServlet {
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(ShowRunServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRunServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res) {
    	log.info("Getting parameters from GET");
    	int trainId = Integer.parseInt(req.getParameter("train"));
    	
    	log.info("Getting Runs list from RunService");
		List<RunVO> runList = new ArrayList<RunVO>();
		try {
			runList = new RunService().getRunByTrainId(trainId);
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("runList", runList);
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		request.getRequestDispatcher("/protected/ShowRun.jsp").forward(request, response);
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
