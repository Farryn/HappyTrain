package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.UserService;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Converts String to Date Object.
     * @param str String with datetime from request parameter
     * @return This is String converted into Date Object
     */
    private Date getDateFromString(String str) {
    	Date date = new Date();
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
			date = sdf.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return date;
    }
    private void processRequest(HttpServletRequest req,	HttpServletResponse res) {
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		String birthDateString = req.getParameter("birth_date");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		Date birthDate = getDateFromString(birthDateString);
		
		new UserService().addUser(firstName, lastName, birthDate, login, password);
		
		
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
		processRequest(request,response);
		request.getRequestDispatcher("/FindTrain.jsp").forward(request, response);
	}

	

}
