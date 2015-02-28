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
		Train train = null;
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			train = dao.findById(id);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		return train;
		
	}

	public List<TrainVO> getAllTrains(){
		List<Train> trainList = new ArrayList<Train>();
		List<TrainVO> trainVOList = new ArrayList<TrainVO>();
		
		TrainDAOImpl dao = new TrainDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			trainList = dao.findAll();
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
		for (Train train: trainList) {
			trainVOList.add(new TrainVO(train));
		}
		return trainVOList;
		
	}
	
	/*public void addTrain(Train train){
		TrainDAOImpl dao = new TrainDAOImpl();
		
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			dao.persist(train);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		
	}*/
	
	public void addTrain(Train train){
		TrainDAOImpl dao = new TrainDAOImpl();
		dao.persist(train);
	}
}
