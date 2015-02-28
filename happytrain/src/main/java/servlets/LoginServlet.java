package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;
import valueobjects.UserVO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String url = request.getParameter("url");
		String servletUrl = request.getParameter("servletUrl");
		UserService us = new UserService();
		UserVO user = us.findUserByLoginAndPass(login, password);
		if (user != null) {
		    request.getSession().setAttribute("user", user); 
		    if (servletUrl.isEmpty()) {
		    	response.sendRedirect(getServletContext().getContextPath()+url); 
		    } else {
		    	response.sendRedirect(servletUrl);
		    }
		} else {
			request.setAttribute("URL", url); 
			request.setAttribute("servletUrl", servletUrl); 
		    request.setAttribute("error", "Unknown login, try again"); 
		    request.getRequestDispatcher("/Login.jsp").forward(request, response); 
		}
	}

}
