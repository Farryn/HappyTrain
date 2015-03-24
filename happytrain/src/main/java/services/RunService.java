package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.EmptyResultException;
import valueobjects.RunVO;
import dao.RunDAO;
import entities.Run;

/**
 * @author Damir Tuktamyshev
 * Service for Run.
 */
@Service("runService")
public class RunService {

	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(RunService.class);
	
	/**
	 * DAO for Run.
	 */
	@Autowired
	private RunDAO runDao;
	

	/**
	 * @param dao the dao to set.
	 */
	public void setRunDao(RunDAO dao) {
		this.runDao = dao;
	}

	/**Get Run by given id.
	 * @param id Run id
	 * @return Run
	 * @throws EmptyResultException
	 */
	@Transactional
	public Run getRunById(int id) throws EmptyResultException {
		Run run = runDao.findById(id);
		if (run == null) {
			LOG.warn("Received no Run with given ID from DAO");
			throw new EmptyResultException("Рейса с таким ID не существует");
		}
		return run;
		
	}

	/**Get all Runs by given Train id.
	 * @param trainId Train id
	 * @return List of Runs
	 */
	@Transactional
	public List<RunVO> getRunByTrainId(int trainId) {
		List<Run> runList =  new ArrayList<Run>();
		List<RunVO> runVOList = new ArrayList<RunVO>();
		LOG.info("Search for Run by Train.Id " + trainId);
		runList = runDao.findByTrainId(trainId);
		if (runList.isEmpty()) {
			LOG.warn("Received empty Run List from DAO");
		}
		for (Run run: runList) {
			runVOList.add(new RunVO(run));
		}
		
		return runVOList;
	}
}
