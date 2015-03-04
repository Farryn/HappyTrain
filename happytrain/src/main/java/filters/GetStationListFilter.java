package filters;

import java.io.IOException;
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
											"/protected/AddTrain.jsp", "/protected/ShowAllTrains.jsp"})
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
		try {
			stationList = ss.getAllStationVO();
		} catch (Exception e) {
			log.warn("Exception: " + e);
			log.info("No station was found");
		}
		request.setAttribute("stationList", stationList);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
