/**
 * 
 */
package valueobjects;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Damir Tuktamyshev
 *
 */
@XmlRootElement
public class TicketListVO {

	/**
	 * 
	 */
	public TicketListVO() {
	}
	
	public TicketListVO(List<TicketVO> ticketList) {
		this.ticketList = ticketList;
	}
	
	private List<TicketVO> ticketList;

	/**
	 * @return the ticketList
	 */
	public List<TicketVO> getTicketList() {
		return ticketList;
	}

	/**
	 * @param ticketList the ticketList to set
	 */
	public void setTicketList(List<TicketVO> ticketList) {
		this.ticketList = ticketList;
	}
	
}
