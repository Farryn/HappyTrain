package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.RunVO;
import dao.RunDAO;
import dao.RunDAOImpl;
import entities.Run;

/**
 * @author Damir Tuktamyshev
 * Service for Run.
 */
public class RunService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(RunService.class);
	
	/**
	 * DAO for Run.
	 */
	private RunDAO dao = new RunDAOImpl();
	

	/**
	 * @param dao the dao to set.
	 */
	public void setDao(RunDAO dao) {
		this.dao = dao;
	}

	/**Get Run by given id.
	 * @param id Run id
	 * @return Run
	 * @throws NullPointerException
	 */
	public Run getRunById(int id) throws NullPointerException {
		Run run = null;
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			run = dao.findById(id);
			if (run == null) {
				LOG.warn("run is null");
				throw new NullPointerException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (NullPointerException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		return run;
		
	}

	/**Get all Runs by given Train id.
	 * @param trainId Train id
	 * @return List of Runs
	 * @throws NullPointerException
	 * @throws IllegalStateException
	 */
	public List<RunVO> getRunByTrainId(int trainId) throws NullPointerException, IllegalStateException {
		List<Run> runList =  new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Search for Run by Train.Id " + trainId);
			runList = dao.findByTrainId(trainId);
			if (runList.isEmpty()) {
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (IllegalStateException | NullPointerException | HibernateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		
		return runVOList;
	}
}
