package services;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import valueobjects.TrainVO;
import dao.StationDAOImpl;
import dao.TrainDAOImpl;
import entities.Station;
import entities.Train;

public class TrainService {

	public TrainVO getTrainVOById(int id){
		TrainService ts = new TrainService();
		Train train = ts.getTrainById(id);
		TrainVO trainVO = new TrainVO(train);
		return trainVO;
		
	}
	
	public Train getTrainById(int id){
		
		TrainDAOImpl dao = new TrainDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Train train = dao.findById(id);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return train;
		
	}

	public List<TrainVO> getAllTrains(){
		List<Train> trainList = new ArrayList<Train>();
		List<TrainVO> trainVOList = new ArrayList<TrainVO>();
		TrainDAOImpl dao = new TrainDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		trainList = dao.findAll();
		HibernateUtil.closeCurrentSessionwithTransaction();
		for (Train train: trainList) {
			trainVOList.add(new TrainVO(train));
		}
		return trainVOList;
		
	}
	
	public void addTrain(Train train){
		TrainDAOImpl dao = new TrainDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		dao.persist(train);
		HibernateUtil.closeCurrentSessionwithTransaction();
		
	}
}
