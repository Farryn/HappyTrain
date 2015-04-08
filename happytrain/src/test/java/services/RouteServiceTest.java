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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import config.AppConfig;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.StationDAOImpl;
import entities.Route;
import entities.Station;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
public class RouteServiceTest {

	@Autowired
	private RouteService routeService;
	
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
		routeService.setRouteDao(mockDAOroute);
		
		//test1
		List<Station> stationList = new ArrayList<Station>();
		Station station = new Station();
		stationList.add(station);
		Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(stationList);
		try {
			routeService.getStationsByTrain(0);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAOroute, Mockito.atLeastOnce()).findStationsByTrain(Mockito.anyInt());
	    
	  //test2
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(null);
	    try {
	    	routeService.getStationsByTrain(0);   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenReturn(new ArrayList<Station>());
	    try {
	    	routeService.getStationsByTrain(0);   
		}  catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAOroute.findStationsByTrain(Mockito.anyInt())).thenThrow(new HibernateException("Some info"));
	    try {
	    	routeService.getStationsByTrain(0);   
	    	fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test method for {@link services.RouteService#addRoute(entities.Train, java.lang.String, int)}.
	 */
	@Test
	public void testAddRoute() {
		routeService.setRouteDao(mockDAOroute);
		routeService.setStationDao(mockDAOstation);
		
		//test1
		List<Station> stationList = new ArrayList<Station>();
		stationList.add(new Station());
		Mockito.when(mockDAOstation.findByName(Mockito.anyString())).thenReturn(stationList);
		try {
			routeService.addRoute(new Train(), "station", 2);
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOroute, Mockito.atLeastOnce()).persist((Route) Mockito.any());
				
		//test2
		Mockito.doThrow(new HibernateException("Some info"));
		try {
			routeService.addRoute(new Train(), "station", 2);   
			fail();
		} catch (Exception e) {
						
		}
		
		//test3
		Mockito.when(mockDAOstation.findByName(Mockito.anyString())).thenReturn(null);
		try {
			routeService.addRoute(new Train(), "station", 2);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		}
	}

}
