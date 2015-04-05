package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import entities.Ticket;

/**
 * Implementation of TicketDAO.
 *
 */
@Repository("ticketDao")
public class TicketDAOImpl extends GenericDAOImpl<Integer, Ticket> implements
		TicketDAO {

	/**
	 * @see dao.TicketDAO#findTicketByRunAndUserIds(int, int)
	 */
	@Override
	public List<Ticket> findTicketByRunAndLogin(int runId, String login) {
		String hql = "SELECT t FROM Ticket t "
					+ "WHERE t.userId.login=:login "
					+ "AND t.runId.id=:runId";
		@SuppressWarnings("unchecked")
		List<Ticket> ticketList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("login", login)
				.setParameter("runId", runId)
				.list();
		if (ticketList.isEmpty() || ticketList == null){
			ticketList = new ArrayList<Ticket>();
		}
		return ticketList;
	}

	/**
	 * @see dao.TicketDAO#findTicketsByRunId(int)
	 */
	@Override
	public List<Ticket> findTicketsByRunId(final int runId) {
		String hql = "SELECT t FROM Ticket t "
					+ "WHERE t.runId.id=:runId ";
		@SuppressWarnings("unchecked")
		List<Ticket> ticketList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("runId", runId)
				.list();
		if (ticketList.isEmpty() || ticketList == null){
			ticketList = new ArrayList<Ticket>();
		}
		return ticketList;
	}
	
	@Override
	public	List<Ticket> findTicketsBetweenTimePeriod(final Date startDate, Date endDate) {
		String hql = "SELECT t FROM Ticket t "
				+ "WHERE t.depTime BETWEEN :start AND :end ";
		@SuppressWarnings("unchecked")
		List<Ticket> ticketList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("start", startDate)
				.setParameter("end", endDate)
				.list();
		if (ticketList.isEmpty() || ticketList == null){
			ticketList = new ArrayList<Ticket>();
		}
		return ticketList;
	}
	

}
