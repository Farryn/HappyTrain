/*package filters;

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

import entities.Station;
import entities.Train;
import services.RouteService;
import services.StationService;
import services.TrainService;
import valueobjects.StationVO;
import valueobjects.TrainVO;

*//**
 * Servlet Filter implementation class GetStationsByTrainFilter
 *//*
@WebFilter//(dispatcherTypes = {DispatcherType.REQUEST }, urlPatterns = { "/AddRun.jsp" })
public class GetStationsByTrainFilter implements Filter {

    *//**
     * Default constructor. 
     *//*
    public GetStationsByTrainFilter() {
        // TODO Auto-generated constructor stub
    }

	*//**
	 * @see Filter#destroy()
	 *//*
	public void destroy() {
		// TODO Auto-generated method stub
	}

	*//**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 *//*
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		int trainId = Integer.parseInt(request.getParameter("train"));
		TrainService ts = new TrainService();
		TrainVO train = ts.getTrainVOById(trainId);
		RouteService rs = new RouteService();
		//List<StationVO> stationList = rs.getStationsByTrain(trainId);
		request.setAttribute("stationList", stationList);
		request.setAttribute("train", train);
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	*//**
	 * @see Filter#init(FilterConfig)
	 *//*
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
*/