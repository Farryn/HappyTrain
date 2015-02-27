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

import entities.Train;
import services.TrainService;
import valueobjects.TrainVO;

/**
 * Servlet implementation class ShowAllTrainsServlet
 */
@WebServlet
public class ShowAllTrainsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAllTrainsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Get data from request and process it
     * @param req Request Object
     * @param res Response Object 
     */
    private void processRequest(HttpServletRequest req, HttpServletResponse res){
    	
    	TrainService ts = new TrainService();
    	List<TrainVO> trainList = ts.getAllTrains();
    	req.setAttribute("trainList", trainList);
    	
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/ShowAllTrains.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
