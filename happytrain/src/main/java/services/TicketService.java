package services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import valueobjects.TicketVO;
import dao.TicketDAO;
import entities.Ticket;

/**
 * @author Damir Tuktamyshev
 * Service for Ticket.
 */
@Service("ticketService")
public class TicketService {
	
	/**
	 * Logger.
	 */
	private static final Logger LOG = Logger.getLogger(TicketService.class);

	/**
	 * DAO for Ticket.
	 */
	@Autowired
	private TicketDAO ticketDao;
	
	/**
	 * @param ticketDao the ticketDao to set
	 */
	public void setTdao(TicketDAO ticketDao) {
		this.ticketDao = ticketDao;
	}
	
	/**Get list of Tickets by given Run id
	 * @param runId Run id
	 * @return TicketVO list
	 */
	@Transactional
	public List<TicketVO> getTicketsByRunId(int runId){
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
		LOG.info("Searching for Tickets by Run.Id " + runId);
		ticketList = ticketDao.findTicketsByRunId(runId);
		if (ticketList.isEmpty()) {
			LOG.warn("Received empty Ticket List from DAO");
		}
		for (Ticket ticket: ticketList) {
			ticketVOList.add(new TicketVO(ticket));
		}
		return ticketVOList;
	}
}
