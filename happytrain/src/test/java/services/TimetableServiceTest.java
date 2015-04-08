package services;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import valueobjects.TimetableVO;
import dao.RouteDAOImpl;
import dao.RunDAOImpl;
import dao.TimetableDAOImpl;
import dao.TrainDAOImpl;
import entities.Route;
import entities.Run;
import entities.Timetable;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
public class TimetableServiceTest {

	@Autowired
	private TimetableService timetableService;
	
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
	 * Test method for {@link services.timetableService#getTimetableByStation(String , String , String )}.
	 */
	@Test
	public void testGetTimetableByStation() {
		timetableService.setTimetableDao(mockDAOtimetable);
		
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
			testList = timetableService.getTimetableByStation("Station", dateStr, dateStr);
		} catch (NullPointerException e) {
			fail();
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOtimetable, Mockito.atLeastOnce()).getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any());
		Assert.assertEquals(run.getId(), testList.get(0).getRunId());
		
		
		/*Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(null);
		try {
			testList = timetableService.getTimetableByStation("Station", dateStr, dateStr);
			fail();
		} catch (NullPointerException e) {
			
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}*/
		
		Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(new ArrayList<Run>());
		try {
			testList = timetableService.getTimetableByStation("Station", dateStr, dateStr);
			
		} catch (NullPointerException e) {
			fail();
		} catch (Exception e) {
			fail();
		}
		
		/*Mockito.when(mockDAOtimetable.getRunFromTimetableByStation((String) Mockito.any(),(Date) Mockito.any(),(Date) Mockito.any())).thenReturn(runList);
		Mockito.when(mockDAOtimetable.findDepTimeFromStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(null);
		Mockito.when(mockDAOtimetable.findArrTimeToStation((String)Mockito.any(), (Run)Mockito.any())).thenReturn(null);
		try {
			testList = timetableService.getTimetableByStation("Station", dateStr, dateStr);
			fail();
		} catch (NullPointerException e) {
			
		} catch (IllegalStateException e) {
			fail();
		} catch (Exception e) {
			fail();
		}*/
	}

	/**
	 * Test method for {@link services.timetableService#addRun(int, String[],String[],String[])}.
	 */
	@Test
	public void testAddRun() {
		timetableService.setTimetableDao(mockDAOtimetable);
		timetableService.setRouteDao(mockDAOroute);
		timetableService.setRunDao(mockDAOrun);
		timetableService.setTrainDao(mockDAOtrain);
		
		//test 1
		String[] firstList = new String[1];
		String[] secondList = new String[2];
		String[] thirdList = new String[1];
		try {
			timetableService.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (Exception e) {
			
		}
		
		//test2
		firstList = new String[1];
		secondList = new String[1];
		thirdList = new String[2];
		try {
			timetableService.addRun(0, firstList, secondList, thirdList);
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
		List<Route> resList = new ArrayList<Route>();
		resList.add(new Route());
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(new Train());
		Mockito.when(mockDAOroute.findRouteByStationStringAndTrainId(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(resList);
		try {
			timetableService.addRun(0, firstList, secondList, thirdList);
		} catch (Exception e) {
			fail();
		}
		Mockito.verify(mockDAOrun, Mockito.atLeastOnce()).persist((Run)Mockito.any());
		Mockito.verify(mockDAOtimetable, Mockito.atLeastOnce()).persist((Timetable)Mockito.any());
		
		//test4
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(null);
		try {
			timetableService.addRun(0, firstList, secondList, thirdList);
			fail();
		}  catch (Exception e) {
		}

		//test5
		Mockito.when(mockDAOtrain.findById(Mockito.anyInt())).thenReturn(new Train());
		Mockito.when(mockDAOroute.findRouteByStationStringAndTrainId(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		try {
			timetableService.addRun(0, firstList, secondList, thirdList);
			fail();
		} catch (NullPointerException e) {
			
		} catch (Exception e) {
			fail();
		}
		
	}

}
