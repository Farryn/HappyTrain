/**
 * 
 */
package services;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dao.RunDAOImpl;
import dao.TicketDAOImpl;
import entities.Run;
import entities.Station;
import entities.Ticket;
import entities.User;

/**
 * @author 
 *
 */
public class TicketServiceTest {

	

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
		TicketService service = new TicketService();
		service.setTdao(mockDAO);
		
		//test1
		Ticket ticket = new Ticket(new User(), new Run(), new Station(), new Station(), new Date());
		List<Ticket> ticketList = new ArrayList<Ticket>();
		ticketList.add(ticket);
		Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(ticketList);
		try {
			service.getTicketsByRunId(0);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	    Mockito.verify(mockDAO, Mockito.atLeastOnce()).findTicketsByRunId(Mockito.anyInt());
	    
	    //test2
	    Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(null);
		try {
			service.getTicketsByRunId(0);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		} 
	    
		//test3
		Mockito.when(mockDAO.findTicketsByRunId(Mockito.anyInt())).thenReturn(new ArrayList<Ticket>());
		try {
			service.getTicketsByRunId(0);
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		} 
	}

}
