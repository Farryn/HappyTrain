package dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import entities.Station;
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
	public List<Ticket> findTicketByRunAndUserIds(int runId, int userId) {
		String hql = "SELECT t FROM Ticket t "
					+ "WHERE t.userId.id=:userId "
					+ "AND t.runId.id=:runId";
		@SuppressWarnings("unchecked")
		List<Ticket> ticketList =  getSessionFactory().getCurrentSession().createQuery(hql)
				.setParameter("userId", userId)
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

	

}
