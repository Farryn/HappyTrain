package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.TicketService;
import services.UserService;
import valueobjects.TicketVO;
import valueobjects.UserVO;

/**
 * Servlet implementation class ShowPassengerServlet
 */
@WebServlet
public class ShowPassengerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowPassengerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    private void processRequest(HttpServletRequest req, HttpServletResponse res) {
		int runId = Integer.parseInt(req.getParameter("run"));
		List<TicketVO> passengerList = new TicketService().getTicketsByRunId(runId);
		req.setAttribute("passengerList", passengerList);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
		request.getRequestDispatcher("/protected/ShowPassenger.jsp").forward(request, response);
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
