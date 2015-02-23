package services;

import dao.StationDAOImpl;
import dao.TrainDAOImpl;
import entities.Station;
import entities.Train;

public class TrainService {

	public Train getTrainById(int id){
		TrainDAOImpl dao = new TrainDAOImpl();
		dao.openCurrentSessionwithTransaction();
		Train train = dao.findById(id);
		dao.closeCurrentSessionwithTransaction();
		return train;
		
	}
}
