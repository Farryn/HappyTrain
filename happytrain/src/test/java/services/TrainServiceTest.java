/**
 * 
 */
package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import valueobjects.RunVO;
import valueobjects.TrainVO;
import dao.RunDAOImpl;
import dao.TrainDAOImpl;
import entities.Run;
import entities.Train;

/**
 * @author Mup4uk
 *
 */
public class TrainServiceTest {


	@Mock
	private TrainDAOImpl mockDAO;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.TrainService#getAllTrains()}.
	 */
	@Test
	public void testGetAllTrains() {
		TrainService service = new TrainService(); 
		service.setDao(mockDAO);
		int id = 1;
		
		//test1
		Train train = new Train("1E", 5);
		List<Train> trainList = new ArrayList<Train>();
		trainList.add(train);
		List<TrainVO> test = new ArrayList<TrainVO>();
		Mockito.when(mockDAO.findAll()).thenReturn(trainList);
		try {
			test = service.getAllTrains();
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findAll();
	    Assert.assertEquals(trainList.get(0).getNumber(), test.get(0).getNumber());
	    
	    //test2
	    Mockito.when(mockDAO.findAll()).thenReturn(null);
	    try {
	    	service.getAllTrains();   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findAll()).thenReturn(new ArrayList<Train>());
	    try {
	    	service.getAllTrains();   
	    	fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findAll()).thenThrow(new HibernateException("Some info"));
	    try {
	    	service.getAllTrains();   
	    	fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test method for {@link services.TrainService#addTrain()}.
	 */
	@Test
	public void testAddTrain() {
		TrainService service = new TrainService(); 
		service.setDao(mockDAO);
		
		Train train = new Train();
		service.addTrain(train);
		Mockito.verify(mockDAO, Mockito.atLeastOnce()).persist(train);
		
		Mockito.doThrow(new HibernateException("Some info"));
		try {
		    service.addTrain(train);  
		    fail();
		} catch (Exception e) {
				
		}
		
	}

}
