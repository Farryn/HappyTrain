package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import util.HibernateUtil;
import valueobjects.RunVO;
import dao.RunDAOImpl;
import entities.Run;

public class RunService {

	private static Logger log = Logger.getLogger(RunService.class);
	
	/*private RunVO getRunVOById(int runId) throws Exception{
		RunService rs = new RunService();
		Run run = rs.getRunById(runId);
		RunVO runVO = new RunVO(run);
		return runVO;
	}*/
	
	public Run getRunById(int id) throws Exception{
		RunDAOImpl dao = new RunDAOImpl();
		Run run = null;
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			run = dao.findById(id);
			if (run == null) {
				log.warn("run is null");
				throw new IllegalStateException();
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		return run;
		
	}

	public List<RunVO> getRunByTrainId(int trainId) throws Exception {
		RunDAOImpl dao = new RunDAOImpl();
		List<Run> runList =  new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Search for Run by Train.Id " + trainId);
			runList = dao.findByTrainId(trainId);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			log.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			log.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			log.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		
		return runVOList;
	}
}
