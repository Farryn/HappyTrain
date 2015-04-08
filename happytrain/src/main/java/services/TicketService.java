/**
 * 
 */
package services;

import java.util.Date;
import java.util.List;

import dao.TicketDAO;
import dao.TicketDAOImpl;
import valueobjects.TicketVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TicketService {
	public List<TicketVO> getTicketsByRunId(int runId);
	public List<TicketVO> getTicketsBetweenTimePeriod(Date startDate, Date endDate);
	public void setTicketdao(TicketDAO ticketDAO);
}
