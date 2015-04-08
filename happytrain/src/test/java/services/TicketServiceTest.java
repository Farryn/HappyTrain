/**
 * 
 */
package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import dao.TicketDAOImpl;
import entities.Run;
import entities.Station;
import entities.Ticket;
import entities.User;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
public class TicketServiceTest {
	
	@Autowired
	private TicketService ticketService;
	

	/**
	 * Mock DAO.
	 */
	@Mock
	private TicketDAOImpl mockDAO;
	
	/**
	 * init Mocks.
	 */
	@Before
	public final void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.TicketService#getTicketsByRunId(int)}.
	 */
	@Test
	public void testGetTicketsByRunId() {
		ticketService.setTicketdao(mockDAO);
		
		//test1
		Ticket ticket = new Ticket(new User(), new Run(), new Station(), new Station(), new Date());
		List<Ticket> ticketList = new ArrayList<Ticket>();
		ticketList.add(ticket);
		Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(ticketList);
		try {
			ticketService.getTicketsByRunId(0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findTicketsByRunId(Mockito.anyInt());
	    
	    //test2
	    Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(null);
		try {
			ticketService.getTicketsByRunId(0);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
		//test3
		Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(new ArrayList<Ticket>());
		try {
			ticketService.getTicketsByRunId(0);
		}  catch (Exception e) {
			fail();
		} 
	}

}
