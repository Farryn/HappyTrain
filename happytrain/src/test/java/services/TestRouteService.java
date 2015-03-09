/**
 * 
 */
package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.StationDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

/**
 * @author 
 *
 */
public class TestRouteService {

	
	/**
	 * Mock DAO.
	 */
	@Mock
	private RouteDAOImpl mockDAOroute;
	
	@Mock
	private StationDAOImpl mockDAOstation;
	/**
	 * init Mocks.
	 */
	@Before
	public final void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.RouteService#getStationsByTrain(int)}.
	 */
	@Test
	public void testGetStationsByTrain() {
		RouteService service = new RouteService();
		service.setRouteDao(mockDAOroute);
		
		//test1
		List<Station> stationList = new ArrayList<Station>();
		Station station = new Station();
		stationList.add(station);
		Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(stationList);
		try {
			service.getStationsByTrain(0);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAOroute, Mockito.atLeastOnce()).findStationsByTrain(Mockito.anyInt());
	    
	  //test2
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(null);
	    try {
	    	service.getStationsByTrain(0);   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(new ArrayList<Station>());
	    try {
	    	service.getStationsByTrain(0);   
	    	fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenThrow(new HibernateException("Some info"));
	    try {
	    	service.getStationsByTrain(0);   
	    	fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test method for {@link services.RouteService#addRoute(entities.Train, java.lang.String, int)}.
	 */
	@Test
	public void testAddRoute() {
		RouteService service = new RouteService();
		service.setRouteDao(mockDAOroute);
		service.setStationDao(mockDAOstation);
		
		//test1
		Mockito.when(mockDAOstation.findByName(Mockito.anyString())).thenReturn(new Station());
		try {
			service.addRoute(new Train(), "station", 2);
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOroute, Mockito.atLeastOnce()).persist((Route) Mockito.any());
				
		//test2
		Mockito.doThrow(new HibernateException("Some info"));
		try {
			service.addRoute(new Train(), "station", 2);   
			fail();
		} catch (Exception e) {
						
		}
		
		//test3
		Mockito.when(mockDAOstation.findByName(Mockito.anyString())).thenReturn(null);
		try {
			service.addRoute(new Train(), "station", 2);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		}
	}

}
