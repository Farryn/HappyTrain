package filters;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.log4j.Logger;

import services.StationService;
import valueobjects.StationVO;
import entities.Station;

/**
 * Servlet Filter implementation class GetStationListFilter
 */
@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD} ,
								urlPatterns = {   "/FindTrain.jsp", "/ShowTimetable.jsp", "/alltrains",
											"/protected/AddTrain.jsp", "/protected/ShowAllTrains.jsp", "/Register.jsp"})
public class GetStationListFilter implements Filter {

	private static Logger log = Logger.getLogger(GetStationListFilter.class);
    /**
     * Default constructor. 
     */
    public GetStationListFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		StationService ss = new StationService();
		List<StationVO> stationList = null;
	    String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		try {
			stationList = ss.getAllStationVO();
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No station was found");
		}
		if (request.getParameter("from") != null) {
			request.setAttribute("from", request.getParameter("from"));
		} else {
			request.setAttribute("from", strDate);
		}
		if (request.getParameter("to") != null) {
			request.setAttribute("to", request.getParameter("to"));
		} else {
			request.setAttribute("to", strDate);
		}
		request.setAttribute("stationList", stationList);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
