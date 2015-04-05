package dao;

import java.util.Date;
import java.util.List;

import entities.Ticket;

/**
 * DAO for Ticket Entity.
 *
 */
public interface TicketDAO extends GenericDAO<Integer, Ticket> {
	/**Get Ticket to given Run(by Id) and for given User(by Id).
	 * @param runId Run Id
	 * @param login User login 
	 * @return Ticket List
	 */
	List<Ticket> findTicketByRunAndLogin(int runId, String login);
	
	/**Get all Tickets to given Run(by Id).
	 * @param runId Run Id
	 * @return Ticket list
	 */
	List<Ticket> findTicketsByRunId(final int runId);
	
	/**Get all Tickets between startDate and endDate.
	 * @param startDate beginning of period
	 * @param endDate end of period
	 * @return Tickets List
	 */
	List<Ticket> findTicketsBetweenTimePeriod(final Date startDate, Date endDate);
}
