/**
 * 
 */
package services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import config.AppConfig;
import dao.RunDAOImpl;
import entities.Train;

/**
 * @author Damir Tuktamyshev
 *
 */
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =  AppConfig.class , 
						loader = AnnotationConfigWebContextLoader.class)
public class EmployeeServiceTest {

	
	@Autowired
	private EmployeeService employeeService;
	/**
	 * Mock Service.
	 */
	@Mock
	private RouteService mockRoute;
	/**
	 * Mock Service.
	 */
	@Mock
	private TrainService mockTrain;
	
	/**
	 * init Mocks.
	 */
	@Before
	public final void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Test method for {@link services.EmployeeService#addTrain(java.lang.String, int, java.lang.String[])}.
	 */
	@Test
	public void testAddTrain() {
		
		employeeService.setRouteService(mockRoute);
		employeeService.setTrainService(mockTrain);
		
		//test1
		String[] array = new String[2];
		array[0] = "string1";
		array[1] = "string2";
		try {
			employeeService.addTrain("Name", 0, array);
		} catch (Exception e) {
			fail();
		}
		
		//test2
		String[] array2 = new String[0];
		try {
			employeeService.addTrain("Name", 0, array2);
			
		} catch (Exception e) {
			fail();
		}
		
		//test3
		array = null;
		try {
			employeeService.addTrain("Name", 0, array2);
			
		} catch (Exception e) {
			fail();
		}
		
	}

}
