package valueobjects;

import entities.Ticket;

public class TicketVO {

	private UserVO userId;
	private String stationFrom;
	private String stationTo;
	
	public TicketVO(){
		
	}
	/**
	 * @param userId
	 * @param stationFrom
	 * @param stationTo
	 */
	public TicketVO(UserVO userId, String stationFrom, String stationTo) {
		this.userId = userId;
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
	}
	
	public TicketVO(Ticket ticket) {
		this.userId = new UserVO(ticket.getUserId());
		this.stationFrom = ticket.getStationFrom().getName();
		this.stationTo = ticket.getStationTo().getName();
	}
	public UserVO getUserId() {
		return userId;
	}
	public void setUserId(UserVO userId) {
		this.userId = userId;
	}
	public String getStationFrom() {
		return stationFrom;
	}
	public void setStationFrom(String stationFrom) {
		this.stationFrom = stationFrom;
	}
	public String getStationTo() {
		return stationTo;
	}
	public void setStationTo(String stationTo) {
		this.stationTo = stationTo;
	}
	
	
}
