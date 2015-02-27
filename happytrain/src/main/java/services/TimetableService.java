package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.HibernateUtil;
import valueobjects.RunVO;
import valueobjects.StationVO;
import dao.TimetableDAOImpl;
import dao.TrainDAOImpl;
import entities.Run;
import entities.Station;
import entities.Train;

public class TimetableService {
	
	public List<RunVO> getTimetableFromStation(StationVO stationVO, Date from, Date to){
		StationService ss = new StationService();
		Station station = ss.getStationByName(stationVO.getName());
		List<Run> runList = new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		TimetableDAOImpl dao = new TimetableDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		runList = dao.getTimetableFromStation(station, from, to);
		HibernateUtil.closeCurrentSessionwithTransaction();
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		return runVOList;
		
	}
}
