/**
 * 
 */
package services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;

import dao.RunDAOImpl;
import entities.Train;

/**
 * @author Mup4uk
 *
 */
public class EmployeeServiceTest {

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
		
		EmployeeService service = new EmployeeService();
		service.setRouteService(mockRoute);
		service.setTrainService(mockTrain);
		
		//test1
		String[] array = new String[2];
		array[0] = "string1";
		array[1] = "string2";
		try {
			service.addTrain("Name", 0, array);
		} catch (Exception e) {
			fail();
		}
		
		//test2
		String[] array2 = new String[0];
		try {
			service.addTrain("Name", 0, array2);
			fail();
		} catch (Exception e) {
			
		}
		
		//test3
		array = null;
		try {
			service.addTrain("Name", 0, array2);
			fail();
		} catch (Exception e) {
			
		}
		
	}

}
