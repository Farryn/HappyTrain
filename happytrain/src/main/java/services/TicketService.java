package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;

import util.HibernateUtil;
import valueobjects.TicketVO;
import dao.TicketDAO;
import dao.TicketDAOImpl;
import entities.Ticket;

/**
 * @author Damir Tuktamyshev
 * Service for Ticket.
 */
public class TicketService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TicketService.class);

	/**
	 * DAO for Ticket.
	 */
	private TicketDAO ticketDao = new TicketDAOImpl();
	
	/**
	 * @param ticketDao the ticketDao to set
	 */
	public void setTdao(TicketDAO ticketDao) {
		this.ticketDao = ticketDao;
	}
	
	/**Get list of Tickets by given Run id
	 * @param runId Run id
	 * @return TicketVO list
	 * @throws IllegalStateException
	 */
	public List<TicketVO> getTicketsByRunId(int runId) throws  IllegalStateException {
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
		
		LOG.info("Opening Hibernate Session with transaction");
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		try {
			LOG.info("Searching for Tickets by Run.Id " + runId);
			ticketList = ticketDao.findTicketsByRunId(runId);
			if (ticketList.isEmpty()) {
				LOG.warn("Empty ticket list");
				throw new IllegalStateException();
			}
			LOG.info("Commiting transaction");
			HibernateUtil.commitTransaction();
		} catch (HibernateException | IllegalStateException e) {
			LOG.warn("Transaction was rollbacked");
			HibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			LOG.info("Closing Hibernate Session");
			HibernateUtil.closeCurrentSession();
		}
		for (Ticket ticket: ticketList) {
			ticketVOList.add(new TicketVO(ticket));
		}
		return ticketVOList;
	}
}
