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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import config.AppConfig;
import config.SecurityConfig;
import valueobjects.RunVO;
import valueobjects.TrainVO;
import dao.RunDAOImpl;
import dao.TrainDAOImpl;
import entities.Run;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
public class TrainServiceTest {

	@Mock
	private TrainDAOImpl mockDAO;
	
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Autowired
	private TrainService trainService;
	
	/**
	 * Test method for {@link services.TrainService#getAllTrains()}.
	 */
	@Test
	public void testGetAllTrains() {
		trainService.setTrainDao(mockDAO);
		int id = 1;
		
		//test1
		Train train = new Train("1E", 5);
		List<Train> trainList = new ArrayList<Train>();
		trainList.add(train);
		List<TrainVO> test = new ArrayList<TrainVO>();
		Mockito.when(mockDAO.findAll()).thenReturn(trainList);
		try {
			test = trainService.getAllTrains();
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findAll();
	    Assert.assertEquals(trainList.get(0).getNumber(), test.get(0).getNumber());
	    
	    //test2
	    Mockito.when(mockDAO.findAll()).thenReturn(null);
	    try {
	    	trainService.getAllTrains();   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findAll()).thenReturn(new ArrayList<Train>());
	    try {
	    	trainService.getAllTrains();   
	    } catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findAll()).thenThrow(new HibernateException("Some info"));
	    try {
	    	trainService.getAllTrains();   
	    	fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test method for {@link services.TrainService#addTrain()}.
	 */
	@Test
	public void testAddTrain() {
		trainService.setTrainDao(mockDAO);
		
		Train train = new Train();
		trainService.addTrain(train);
		Mockito.verify(mockDAO, Mockito.atLeastOnce()).persist(train);
		
		Mockito.doThrow(new HibernateException("Some info"));
		try {
		    trainService.addTrain(train);  
		    fail();
		} catch (Exception e) {
				
		}
		
	}

}
