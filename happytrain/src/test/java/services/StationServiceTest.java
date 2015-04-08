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
import dao.RunDAOImpl;
import dao.StationDAOImpl;
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
public class StationServiceTest {

	@Autowired
	private StationService stationService;
	
	/**
	 * Mock DAO.
	 */
	@Mock
	private StationDAOImpl mockDAO;
	
	/**
	 * init Mocks.
	 */
	@Before
	public final void init(){
		MockitoAnnotations.initMocks(this);
	}
	/**
	 * Test method for {@link services.StationService#getAllStations()}.
	 */
	@Test
	public void testGetAllStations() {
		stationService.setStationDao(mockDAO);
		
		//test1
		List<Station> stationList = new ArrayList<Station>();
		Station station = new Station();
		stationList.add(station);
		Mockito.when(mockDAO.findAll()).thenReturn(stationList);
		try {
			stationService.getAllStations();
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findAll();
	    
	    //test2
	    Mockito.when(mockDAO.findAll()).thenReturn(null);
	    try {
	    	stationService.getAllStations();   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findAll()).thenReturn(new ArrayList<Station>());
	    try {
	    	stationService.getAllStations();   
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findAll()).thenThrow(new HibernateException("Some info"));
	    try {
	    	stationService.getAllStations();   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	}

	/**
	 * Test method for {@link services.StationService#addStation(java.lang.String)}.
	 */
	@Test
	public void testAddStation() {
		stationService.setStationDao(mockDAO);
		
		//test1
		try {
			stationService.addStation("Station");
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAO, Mockito.atLeastOnce()).persist((Station) Mockito.any());
		
		//test2
		Mockito.doThrow(new HibernateException("Some info"));
		try {
		    stationService.addStation("Station");   
		    fail();
		} catch (Exception e) {
				
		}
	}

}
