package services;

import util.HibernateUtil;
import valueobjects.RunVO;
import dao.RunDAOImpl;
import entities.Run;

public class RunService {

	
	
	public Run getRunById(int id){
		RunDAOImpl dao = new RunDAOImpl();
		HibernateUtil.openCurrentSessionwithTransaction();
		Run run = dao.findById(id);
		HibernateUtil.closeCurrentSessionwithTransaction();
		return run;
		
	}

	public RunVO getRunVOById(int runId) {
		RunService rs = new RunService();
		Run run = rs.getRunById(runId);
		RunVO runVO = new RunVO(run);
		return runVO;
	}
}
