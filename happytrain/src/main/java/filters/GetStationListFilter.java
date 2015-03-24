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
/*@WebFilter(dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD} ,
								urlPatterns = {   "/FindTrain.jsp", "/ShowTimetable.jsp", "/alltrains",
											"/protected/AddTrain.jsp", "/protected/ShowAllTrains.jsp", "/Register.jsp"})*/
public class GetStationListFilter implements Filter {

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(GetStationListFilter.class);
    /**
     * Default constructor. 
     */
    public GetStationListFilter() {
    }

	

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		StationService ss = new StationService();
		List<StationVO> stationList = null;
	    String strDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
		try {
			stationList = ss.getAllStationVO();
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No station was found");
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



	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// empty
		
	}



	@Override
	public void destroy() {
		// empty
		
	}

	

}
