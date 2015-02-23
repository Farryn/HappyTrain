package services;

import java.util.Date;
import java.util.List;

import dao.TimetableDAOImpl;
import dao.TrainDAOImpl;
import entities.Run;
import entities.Station;
import entities.Train;

public class TimetableService {
	public List<Run> getTimetableFromStation(Station station, Date from, Date to){
		TimetableDAOImpl dao = new TimetableDAOImpl();
		dao.openCurrentSessionwithTransaction();
		List<Run> list = dao.getTimetableFromStation(station, from, to);
		dao.closeCurrentSessionwithTransaction();
		return list;
		
	}
}
