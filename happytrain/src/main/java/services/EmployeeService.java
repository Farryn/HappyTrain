package services;

import java.util.ArrayList;
import java.util.List;

import entities.Station;
import entities.Train;
import util.HibernateUtil;
import valueobjects.StationVO;
import valueobjects.TrainVO;

public class EmployeeService {
	public void addTrain(String name, int seatsCount, List<StationVO> stationVOList) {
		TrainService ts = new TrainService();
		RouteService rs = new RouteService();
		StationService ss = new StationService();
		
		Train train = new Train(name, seatsCount);
		List<Station> stationList = new ArrayList<Station>();
		for (StationVO stationVO: stationVOList) {
			Station station = ss.getStationByName(stationVO.getName());
			stationList.add(station);
		}
		
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			ts.addTrain(train);
			int count = 0;
			for (Station station: stationList) {
				count++;
				rs.addRoute(train, station, count);
			}
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
	}
}
