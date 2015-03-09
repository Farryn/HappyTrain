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
	private static final Logger LOG = Logger.getLogger(ShowRunServlet.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowRunServlet() {
    }

    /** Process data from request.
     * @param req HttpServletRequest Object
     * @param res HttpServletResponse Object
     */
    private void processRequest(HttpServletRequest req) {
    	LOG.info("Getting parameters from GET");
    	int trainId = Integer.parseInt(req.getParameter("train"));
    	
    	LOG.info("Getting Runs list from RunService");
		List<RunVO> runList = new ArrayList<RunVO>();
		try {
			runList = new RunService().getRunByTrainId(trainId);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("runList", runList);
		
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request);
		request.getRequestDispatcher("/protected/ShowRun.jsp").forward(request, response);
	}

	

	

}
