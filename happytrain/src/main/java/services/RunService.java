package services;

import dao.RunDAOImpl;
import entities.Run;

public class RunService {

	public Run getRunById(int id){
		RunDAOImpl dao = new RunDAOImpl();
		dao.openCurrentSessionwithTransaction();
		Run run = dao.findById(id);
		dao.closeCurrentSessionwithTransaction();
		return run;
		
	}
}
