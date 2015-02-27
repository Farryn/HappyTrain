package services;

import java.util.List;

import entities.Station;
import entities.Train;
import valueobjects.StationVO;
import valueobjects.TrainVO;

public class EmployeeService {
	public void addTrain(String name, int seatsCount, List<StationVO> stationList) {
		TrainService ts = new TrainService();
		RouteService rs = new RouteService();
		StationService ss = new StationService();
		
		Train train = new Train(name, seatsCount);
		ts.addTrain(train);
		int count = 0;
		for (StationVO stationVO: stationList) {
			count++;
			Station station = ss.getStationByName(stationVO.getName());
			rs.addRoute(train, station, count);
		}
	}
}
