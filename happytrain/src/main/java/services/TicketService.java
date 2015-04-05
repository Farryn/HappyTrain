/**
 * 
 */
package services;

import java.util.Date;
import java.util.List;

import valueobjects.TicketVO;

/**
 * @author Damir Tuktamyshev
 *
 */
public interface TicketService {
	public List<TicketVO> getTicketsByRunId(int runId);
	public List<TicketVO> getTicketsBetweenTimePeriod(Date startDate, Date endDate);
}
