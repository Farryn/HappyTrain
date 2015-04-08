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
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import config.AppConfig;
import valueobjects.RunVO;
import dao.RunDAOImpl;
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
public class RunServiceTest {

	@Autowired
	private RunService runService;
	
	
	/**
	 * Mock DAO.
	 */
	@Mock
	private RunDAOImpl mockDAO;
	
	/**
	 * init Mocks.
	 */
	@Before
	public final void init(){
		MockitoAnnotations.initMocks(this);
	}
	/**
	 * Test method for {@link services.RunService#getRunById(int)}.
	 */
	@Test
	public void testGetRunById() {
		runService.setRunDao(mockDAO);
		int id = 1;
		
		//test1
		Run run = new Run(new Train(), new Date(), new Date());
		Run test = new Run();
		Mockito.when(mockDAO.findById(id)).thenReturn(run);
		try {
			test = runService.getRunById(id);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findById(id);
	    Assert.assertEquals(run.getStartTime(), test.getStartTime());
	    
	    //test2
	    Mockito.when(mockDAO.findById(id)).thenReturn(null);
	    try {
	    	runService.getRunById(id);   
	    	fail();
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findById(id)).thenThrow(new HibernateException("Some info"));
	    try {
	    	runService.getRunById(id);   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	}

	/**
	 * Test method for {@link services.RunService#getRunByTrainId(int)}.
	 */
	@Test
	public void testGetRunByTrainId() {
		runService.setRunDao(mockDAO);
		int id = 1;
		
		//test1
		Run run = new Run(new Train(), new Date(), new Date());
		List <Run> runList = new ArrayList<Run>();
		runList.add(run);
		List <RunVO> test = new ArrayList<RunVO>();
		Mockito.when(mockDAO.findByTrainId(id)).thenReturn(runList);
		try {
			test = runService.getRunByTrainId(id);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findByTrainId(id);
	    Assert.assertEquals(runList.get(0).getStartTime(), test.get(0).getStartTime());
	    
	    //test2
	    Mockito.when(mockDAO.findByTrainId(id)).thenReturn(null);
	    try {
	    	runService.getRunByTrainId(id);   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findByTrainId(id)).thenReturn(new ArrayList<Run>());
	    try {
	    	runService.getRunByTrainId(id);   
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findByTrainId(id)).thenThrow(new HibernateException("Some info"));
	    try {
	    	runService.getRunByTrainId(id);   
	    	fail();
		} catch (Exception e) {
			
		}
	}

}
