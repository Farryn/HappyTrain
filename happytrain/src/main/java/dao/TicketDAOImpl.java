package dao;

import java.util.List;

import util.HibernateUtil;
import entities.Ticket;

/**
 * Implementation of TicketDAO.
 *
 */
public class TicketDAOImpl extends GenericDAOImpl<Integer, Ticket> implements
		TicketDAO {

	/**
	 * @see dao.TicketDAO#findTicketByRunAndUserIds(int, int)
	 */
	@Override
	public Ticket findTicketByRunAndUserIds(int runId, int userId) {
		String hql = "SELECT t FROM Ticket t "
					+ "WHERE t.userId.id=:userId "
					+ "AND t.runId.id=:runId";
		Ticket ticket = (Ticket) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("userId", userId)
				.setParameter("runId", runId)
				.uniqueResult();
		return ticket;
	}

	/**
	 * @see dao.TicketDAO#findTicketsByRunId(int)
	 */
	@Override
	public List<Ticket> findTicketsByRunId(final int runId) {
		String hql = "SELECT t FROM Ticket t "
					+ "WHERE t.runId.id=:runId ";
		List<Ticket> ticketList = (List<Ticket>) HibernateUtil.getCurrentSession().createQuery(hql)
				.setParameter("runId", runId)
				.list();
		return ticketList;
	}

	

}
