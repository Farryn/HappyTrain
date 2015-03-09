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
import dao.RunDAOImpl;
import entities.Run;
import entities.Train;

/**
 * @author 
 *
 */
public class RunServiceTest {

	
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
		RunService rs = new RunService(); 
		rs.setDao(mockDAO);
		int id = 1;
		
		//test1
		Run run = new Run(new Train(), new Date(), new Date());
		Run test = new Run();
		Mockito.when(mockDAO.findById(id)).thenReturn(run);
		try {
			test = rs.getRunById(id);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findById(id);
	    Assert.assertEquals(run.getStartTime(), test.getStartTime());
	    
	    //test2
	    Mockito.when(mockDAO.findById(id)).thenReturn(null);
	    try {
	    	rs.getRunById(id);   
	    	fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findById(id)).thenThrow(new HibernateException("Some info"));
	    try {
	    	rs.getRunById(id);   
	    	fail();
		} catch (Exception e) {
			
		}
	    
	}

	/**
	 * Test method for {@link services.RunService#getRunByTrainId(int)}.
	 */
	@Test
	public void testGetRunByTrainId() {
		RunService rs = new RunService(); 
		rs.setDao(mockDAO);
		int id = 1;
		
		//test1
		Run run = new Run(new Train(), new Date(), new Date());
		List <Run> runList = new ArrayList<Run>();
		runList.add(run);
		List <RunVO> test = new ArrayList<RunVO>();
		Mockito.when(mockDAO.findByTrainId(id)).thenReturn(runList);
		try {
			test = rs.getRunByTrainId(id);
		} catch (Exception e) {
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findByTrainId(id);
	    Assert.assertEquals(runList.get(0).getStartTime(), test.get(0).getStartTime());
	    
	    //test2
	    Mockito.when(mockDAO.findByTrainId(id)).thenReturn(null);
	    try {
	    	rs.getRunByTrainId(id);   
	    	fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
	    //test3
	    Mockito.when(mockDAO.findByTrainId(id)).thenReturn(new ArrayList<Run>());
	    try {
	    	rs.getRunByTrainId(id);   
	    	fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		}
	    
	    //test4
	    Mockito.when(mockDAO.findByTrainId(id)).thenThrow(new HibernateException("Some info"));
	    try {
	    	rs.getRunByTrainId(id);   
	    	fail();
		} catch (Exception e) {
			
		}
	}

}
