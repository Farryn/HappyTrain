package services;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import valueobjects.RunVO;
import dao.RunDAOImpl;
import entities.Run;

public class RunService {

	
	
	public Run getRunById(int id){
		RunDAOImpl dao = new RunDAOImpl();
		Run run = null;
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			run = dao.findById(id);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		}
		HibernateUtil.closeCurrentSession();
		return run;
		
	}

	public RunVO getRunVOById(int runId) {
		RunService rs = new RunService();
		Run run = rs.getRunById(runId);
		RunVO runVO = new RunVO(run);
		return runVO;
	}

	public List<RunVO> getRunByTrainId(int trainId) {
		RunDAOImpl dao = new RunDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		List<Run> runList =  new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		try {
			runList = dao.findByTrainId(trainId);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		}
		for(Run run: runList){
			runVOList.add(new RunVO(run));
		}
		HibernateUtil.closeCurrentSession();
		return runVOList;
	}
}
