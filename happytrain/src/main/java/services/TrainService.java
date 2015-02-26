package services;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<Train> getAllTrains(){
		List<Train> train = new ArrayList<Train>();
		TrainDAOImpl dao = new TrainDAOImpl();
		dao.openCurrentSessionwithTransaction();
		train = dao.findAll();
		dao.closeCurrentSessionwithTransaction();
		return train;
		
	}
	
	public void addTrain(Train train){
		TrainDAOImpl dao = new TrainDAOImpl();
		dao.openCurrentSessionwithTransaction();
		dao.persist(train);
		dao.closeCurrentSessionwithTransaction();
		
	}
}
