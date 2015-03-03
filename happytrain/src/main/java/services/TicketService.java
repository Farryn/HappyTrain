package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import servlets.ShowPassengerServlet;
import util.HibernateUtil;
import valueobjects.TicketVO;
import valueobjects.UserVO;
import dao.TicketDAOImpl;
import entities.Ticket;
import entities.User;

public class TicketService {
	
	private static Logger log = Logger.getLogger(TicketService.class);

	public List<TicketVO> getTicketsByRunId(int runId) throws Exception {
		TicketDAOImpl tdao = new TicketDAOImpl();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
		
		log.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			log.info("Searching for Tickets by Run.Id " + runId);
			ticketList = tdao.findTicketsByRunId(runId);
			if (ticketList.isEmpty()) {
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
		for (Ticket ticket: ticketList) {
			ticketVOList.add(new TicketVO(ticket));
		}
		return ticketVOList;
	}
}
