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
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import config.AppConfig;
import util.MyException;
import valueobjects.StationVO;
import valueobjects.TimetableVO;
import valueobjects.UserVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.TimetableDAOImpl;
import entities.Run;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
/*@RunWith(PowerMockRunner.class)*/
@PrepareForTest({ ClientServiceImpl.class })
public class ClientServiceTest {

	@Autowired
	private ClientService clientService;
	private ClientService spy;
	
	
	/**
	 * Mock DAO.
	 */
	@Mock
	private RunDAOImpl mockDAOrun;
	
	@Mock
	private RouteDAOImpl mockDAOroute;
	/**
	 * init Mocks.
	 */
	@Before
	public final void init(){
		MockitoAnnotations.initMocks(this);
	}
	
	/*@Rule
    public PowerMockRule rule = new PowerMockRule();*/
	/**
	 * Test method for {@link services.ClientService#searchTrain(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSearchTrain() {
		
		//test 1
		try {
			clientService.searchTrain("station", "station", "date", "date");
			
		} catch (Exception e) {
			fail();
		}
		
		//test2
		try {
			clientService.searchTrain(null, "station", "20.02.1998 15:15", "20.02.1998 15:15");
		}  catch (Exception e) {
			fail();
		}
		
		//test3
		try {
			clientService.searchTrain("station", null, "20.02.1998 15:15", "20.02.1998 15:15");
		}  catch (Exception e) {
			fail();
		}
		
		//test4
		try {
			clientService.searchTrain("station", null, "20.02.1998 15:15", "20.02.1998 15:15");
		}  catch (Exception e) {
			fail();
		}
		
		//test5
		spy = PowerMockito.spy(clientService);
		try {
			List<TimetableVO> list = new ArrayList<TimetableVO>();
			list.add(new TimetableVO());
			PowerMockito.doReturn(list)
				.when(spy, "getSearchedTrains", Mockito.anyString(), Mockito.anyString(),(Date) Mockito.any(), (Date) Mockito.any());
			spy.searchTrain("station", "station", "20.02.1998 15:15", "20.02.1998 15:15");
			fail();
		} catch (Exception e) {
			
			
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
		spy =  PowerMockito.spy(clientService);
		//RouteDAOImpl dao = PowerMockito.mock(RouteDAOImpl.class);
		//spy.setRouteDao(mockDAOroute);
		//test1
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(mockDAOroute, "findById",  Mockito.anyInt());
			PowerMockito.doNothing()
				.when(spy, "addTicket",  Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doNothing()
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket("login", "from", "to", "depTime", "3");
			fail();
		} catch (Exception e) {
			
		}
		
		//test2
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(mockDAOroute, "findById",  Mockito.anyInt());
			PowerMockito.doThrow(new NullPointerException())
				.when(spy, "addTicket", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doNothing()
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket("login", "from", "to", "depTime", "3");
			fail();
		} catch (Exception e) {
			
		}
		
		//test3
		try {
			PowerMockito.doReturn(Boolean.TRUE)
				.when(spy, "checkForBuying", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
			PowerMockito.doReturn(new Run()).when(mockDAOroute, "findById",  Mockito.anyInt());
			PowerMockito.doNothing()
				.when(spy, "addTicket", (UserVO) Mockito.any(), Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any(), Mockito.anyString());
			PowerMockito.doThrow(new NullPointerException())
				.when(spy, "updateTimetable", Mockito.anyString(), Mockito.anyString(), (Run) Mockito.any());
			spy.buyTicket("login", "from", "to", "depTime", "3");
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
			clientService.getTimesFromStationList(0, list);
			
		} catch (Exception e) {
			fail();
		}
		
		
		list.add(new StationVO());
		TimetableDAOImpl dao = PowerMockito.mock(TimetableDAOImpl.class);
		clientService.setTimetableDao(dao);
		RunService mockRun = PowerMockito.mock(RunService.class);
		//clientService.setRunService(mockRun);
		
		//test2
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(new Date()).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(new Date()).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			clientService.getTimesFromStationList(0, list);
			fail();
		} catch (Exception e) {
			
			
		}
		
		//test3
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(null).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(new Date()).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			clientService.getTimesFromStationList(0, list);
			
		} catch (Exception e) {
			fail();
		}
		
		//test4
		try {
			PowerMockito.doReturn(new Run()).when(mockRun, "getRunById",  Mockito.anyInt());
			PowerMockito.doReturn(new Date()).when(dao, "findDepTimeFromStation",  Mockito.anyString(), (Run) Mockito.any());
			PowerMockito.doReturn(null).when(dao, "findArrTimeToStation",  Mockito.anyString(), (Run) Mockito.any());
			clientService.getTimesFromStationList(0, list);
			
		} catch (Exception e) {
			fail();
		}
	}

}
