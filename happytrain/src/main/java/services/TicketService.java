package services;

import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;
import valueobjects.TicketVO;
import valueobjects.UserVO;
import dao.TicketDAOImpl;
import entities.Ticket;
import entities.User;

public class TicketService {
	
	public List<TicketVO> getTicketsByRunId(int runId) {
		TicketDAOImpl tdao = new TicketDAOImpl();
		HibernateUtil.openCurrentSession();
		HibernateUtil.beginTransaction();
		List<Ticket> ticketList = new ArrayList<Ticket>();
		List<TicketVO> ticketVOList = new ArrayList<TicketVO>();
		try {
			ticketList = tdao.findTicketsByRunId(runId);
			HibernateUtil.commitTransaction();
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction();
		} finally {
			HibernateUtil.closeCurrentSession();
		}
		for (Ticket ticket: ticketList) {
			ticketVOList.add(new TicketVO(ticket));
		}
		return ticketVOList;
	}
}
