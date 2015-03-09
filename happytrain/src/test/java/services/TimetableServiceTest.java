package services;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import valueobjects.TimetableVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.TimetableDAOImpl;
import dao.TrainDAOImpl;
import entities.Route;
import entities.Run;
import entities.Timetable;
import entities.Train;

public class TimetableServiceTest {

	
	/**
	 * Mock DAO.
	 */
	@Mock
	private TimetableDAOImpl mockDAOtimetable;
	
	@Mock
	private TrainDAOImpl mockDAOtrain;
	
	@Mock
	private RunDAOImpl mockDAOrun;
	
	@Mock
	private RouteDAOImpl mockDAOroute;
	
	/**
	 * init Mocks.
	 */
	@Before
	public final void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.TimetableService#getTimetableByStation(String , String , String )}.
	 */
	@Test
	public void testGetTimetableByStation() {
		TimetableService service = new TimetableService();
		service.setTimetableDao(mockDAOtimetable);
		
		List<Run> runList = new ArrayList<Run>();
		List<TimetableVO> testList = new ArrayList<TimetableVO>();
		Run run = new Run(new Train(), new Date(), new Date());
		runList.add(run);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		Date date = new Date();
		String dateStr = sdf.format(date);
		
		Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(runList);
		Mockito.when(mockDAOtimetable.findDepTimeFromStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(date);
		Mockito.when(mockDAOtimetable.findArrTimeToStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(date);
		try {
			testList = service.getTimetableByStation("Station", dateStr, dateStr);
		} catch (NullPointerException e) {
			fail();
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOtimetable, Mockito.atLeastOnce()).getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any());
		Assert.assertEquals(run.getId(), testList.get(0).getRunId());
		
		
		Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(null);
		try {
			testList = service.getTimetableByStation("Station", dateStr, dateStr);
			fail();
		} catch (NullPointerException e) {
			
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(new ArrayList<Run>());
		try {
			testList = service.getTimetableByStation("Station", dateStr, dateStr);
			fail();
		} catch (NullPointerException e) {
			fail();
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail();
		}
		
		Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(runList);
		Mockito.when(mockDAOtimetable.findDepTimeFromStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(null);
		Mockito.when(mockDAOtimetable.findArrTimeToStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(null);
		try {
			testList = service.getTimetableByStation("Station", dateStr, dateStr);
			fail();
		} catch (NullPointerException e) {
			
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
	}

	/**
	 * Test method for {@link services.TimetableService#addRun(int, String[],String[],String[])}.
	 */
	@Test
	public void testAddRun() {
		TimetableService service = new TimetableService();
		service.setTimetableDao(mockDAOtimetable);
		service.setRouteDao(mockDAOroute);
		service.setRunDao(mockDAOrun);
		service.setTrainDao(mockDAOtrain);
		
		//test 1
		String[] firstList = new String[1];
		String[] secondList = new String[2];
		String[] thirdList = new String[1];
		try {
			service.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (Exception e) {
			
		}
		
		//test2
		firstList = new String[1];
		secondList = new String[1];
		thirdList = new String[2];
		try {
			service.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (Exception e) {
			
		}
		
		
		//test3
		firstList = new String[1];
		secondList = new String[1];
		thirdList = new String[1];
		SimpleDateFormat sdf = new SimpleDateFormat("dd.M.yyyy HH:mm");
		secondList[0] = sdf.format(new Date());
		thirdList[0] = sdf.format(new Date());
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(new Train());
		Mockito.when(mockDAOroute.findRouteByStationStringAndTrainId(Mockito.anyString(), Mockito.anyInt())).thenReturn(new Route());
		try {
			service.addRun(0, firstList, secondList, thirdList);
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOrun, Mockito.atLeastOnce()).persist((Run)Mockito.any());
		Mockito.verify(mockDAOtimetable, Mockito.atLeastOnce()).persist((Timetable)Mockito.any());
		
		//test4
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(null);
		try {
			service.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		}

		//test5
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(new Train());
		Mockito.when(mockDAOroute.findRouteByStationStringAndTrainId(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		try {
			service.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		}
		
	}

}
