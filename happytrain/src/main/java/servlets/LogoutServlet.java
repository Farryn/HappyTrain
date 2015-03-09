package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class LogoutServlet.
 */
@WebServlet
public class LogoutServlet extends HttpServlet {
	
	/**
	 * Serial Id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(LogoutServlet.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("Logging out");
		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
		request.getRequestDispatcher("/").forward(request, response);
	}

	

}
