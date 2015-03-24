package dao;

import java.util.List;

import entities.Ticket;

/**
 * DAO for Ticket Entity.
 *
 */
public interface TicketDAO extends GenericDAO<Integer, Ticket> {
	/**Get Ticket to given Run(by Id) and for given User(by Id).
	 * @param runId Run Id
	 * @param userId User Id 
	 * @return Ticket List
	 */
	List<Ticket> findTicketByRunAndUserIds(int runId, int userId);
	
	/**Get all Tickets to given Run(by Id).
	 * @param runId Run Id
	 * @return Runs list
	 */
	List<Ticket> findTicketsByRunId(final int runId);
}
