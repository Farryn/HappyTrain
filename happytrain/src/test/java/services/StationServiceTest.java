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

import dao.RunDAOImpl;
import dao.StationDAOImpl;
import entities.Station;
import entities.Train;

/**
 * @author 
 *
 */
public class StationServiceTest {

	
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
		StationService service = new StationService();
		service.setDao(mockDAO);
		
		//test1
		List<Station> stationList = new ArrayList<Station>();
		Station station = new Station();
		stationList.add(station);
		Mockito.when(mockDAO.findAll()).thenReturn(stationList);
		try {
			service.getAllStations();
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findAll();
	    
	    //test2
	    Mockito.when(mockDAO.findAll()).thenReturn(null);
	    try {
	    	service.getAllStations();   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findAll()).thenReturn(new ArrayList<Station>());
	    try {
	    	service.getAllStations();   
	    	fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findAll()).thenThrow(new HibernateException("Some info"));
	    try {
	    	service.getAllStations();   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	}

	/**
	 * Test method for {@link services.StationService#addStation(java.lang.String)}.
	 */
	@Test
	public void testAddStation() {
		StationService service = new StationService();
		service.setDao(mockDAO);
		
		//test1
		try {
			service.addStation("Station");
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAO, Mockito.atLeastOnce()).persist((Station) Mockito.any());
		
		//test2
		Mockito.doThrow(new HibernateException("Some info"));
		try {
		    service.addStation("Station");   
		    fail();
		} catch (Exception e) {
				
		}
	}

}
