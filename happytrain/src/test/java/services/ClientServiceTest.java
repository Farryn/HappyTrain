/**
 * 
 */
package services;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import util.MyException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.UserVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.TimetableDAOImpl;
import entities.Run;

/**
 * @author Mup4uk
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ ClientService.class })
public class ClientServiceTest {

	
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
	 * Test method for {@link services.ClientService#searchTrain(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSearchTrain() {
		ClientService service = new ClientService();
		
		//test 1
		try {
			service.searchTrain("station", "station", "date", "date");
			fail();
		} catch (MyException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		//test2
		try {
			service.searchTrain(null, "station", "20.02.1998 15:15", "20.02.1998 15:15");
			fail();
		} catch (IllegalArgumentException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		//test3
		try {
			service.searchTrain("station", null, "20.02.1998 15:15", "20.02.1998 15:15");
			fail();
		} catch (IllegalArgumentException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		//test4
		try {
			service.searchTrain("station", null, "20.02.1998 15:15", "20.02.1998 15:15");
			fail();
		} catch (IllegalArgumentException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		//test5
		ClientService spy = PowerMockito.spy(new ClientService());
		try {
			List<TimetableVO> list = new ArrayList<TimetableVO>();
			list.add(new TimetableVO());
			PowerMockito.doReturn(list)
				.when(spy, "getSearchedTrains", Mockito.anyString(), Mockito.anyString(),(Date) Mockito.any(), (Date) Mockito.any());
			spy.searchTrain("station", "station", "20.02.1998 15:15", "20.02.1998 15:15");
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//test6
		try {
			PowerMockito.doThrow(new IllegalStateException())
				.when(spy, "getSearchedTrains", Mockito.anyString(), Mockito.anyString(),(Date) Mockito.any(), (Date) Mockito.any());
			spy.searchTrain("station", "station", "20.02.1998 15:15", "20.02.1998 15:15");
			fail();
		} catch (Exception e) {
			
		}
		
	}

	/**
	 * Test method for {@link services.ClientService#buyTicket(valueobjects.UserVO, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testBuyTicket() {
		ClientService spy = PowerMockito.spy(new ClientService());
		RouteDAOImpl dao = PowerMockito.mock(RouteDAOImpl.class);
		spy.setRouteDao(dao);
		//test1
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(dao, "findById",  Mockito.anyInt());
			PowerMockito.doNothing()
				.when(spy, "addTicket", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doNothing()
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket(new UserVO(), "from", "to", "depTime", "3");
			
		} catch (Exception e) {
			fail();
		}
		
		//test2
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(dao, "findById",  Mockito.anyInt());
			PowerMockito.doThrow(new NullPointerException())
				.when(spy, "addTicket", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doNothing()
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket(new UserVO(), "from", "to", "depTime", "3");
			fail();
		} catch (Exception e) {
			
		}
		
		//test3
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(dao, "findById",  Mockito.anyInt());
			PowerMockito.doNothing()
				.when(spy, "addTicket", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doThrow(new NullPointerException())
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket(new UserVO(), "from", "to", "depTime", "3");
			fail();
		} catch (Exception e) {
			
		}
	}

	/**
	 * Test method for {@link services.ClientService#getTimesFromStationList(int, java.util.List)}.
	 */
	@Test
	public void testGetTimesFromStationList() {
		
		//test1
		List<StationVO> list = new ArrayList<StationVO>();
		try {
			new ClientService().getTimesFromStationList(0, list);
			fail();
		} catch (IllegalArgumentException e) {
			
		}
		
		
		list.add(new StationVO());
		ClientService service = new ClientService();
		TimetableDAOImpl dao = PowerMockito.mock(TimetableDAOImpl.class);
		service.setTimetableDao(dao);
		RunService mockRun = PowerMockito.mock(RunService.class);
		service.setRunService(mockRun);
		
		//test2
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(new Date()).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(new Date()).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			service.getTimesFromStationList(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		
		//test3
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(null).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(new Date()).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			service.getTimesFromStationList(0, list);
			fail();
		} catch (Exception e) {
			
		}
		
		//test4
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(new Date()).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(null).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			service.getTimesFromStationList(0, list);
			fail();
		} catch (Exception e) {
			
		}
	}

}
