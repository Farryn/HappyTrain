package valueobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import entities.Ticket;

public class TicketVO {

	private String firstName;
	private String lastName;
	private String stationFrom;
	private String stationTo;
	private String depTime;
	
	public TicketVO(){
		
	}
	
	/**
	 * @param userId
	 * @param stationFrom
	 * @param stationTo
	 *//*
	public TicketVO(UserVO userId, String stationFrom, String stationTo) {
		this.userId = userId;
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
	}*/
	
	public TicketVO(Ticket ticket) {
		DateFormat df = new SimpleDateFormat("dd-M-yyyy HH:mm");
		this.firstName = ticket.getUserId().getFirstName();
		this.lastName = ticket.getUserId().getLastName();
		this.stationFrom = ticket.getStationFrom().getName();
		this.stationTo = ticket.getStationTo().getName();
		this.depTime = df.format(ticket.getDepTime());
	}
	
	
	public String getDepTime() {
		return depTime;
	}

	public void setDepTime(String depTime) {
		this.depTime = depTime;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
