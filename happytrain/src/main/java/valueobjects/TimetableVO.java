package valueobjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimetableVO {

	private int trainId;
	private String trainNumber;
	private int runId;
	private Date departureDateTime; 
	private Date arrivalDateTime;
	private int availableSeats;
	
	/**
	 * 
	 */
	public TimetableVO() {
	}
	
	/**
	 * @param trainNumber
	 * @param runId
	 * @param departureDateTime
	 * @param arrivalDateTime
	 * @param availableSeats
	 */
	public TimetableVO(int trainId, String trainNumber, int runId,
			Date departureDateTime, Date arrivalDateTime,
			int availableSeats) {
		this.trainId = trainId;
		this.trainNumber = trainNumber;
		this.runId = runId;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
		this.availableSeats = availableSeats;
	}

	/**
	 * @param runId
	 * @param departureDateTime
	 * @param arrivalDateTime
	 */
	public TimetableVO(Date departureDateTime, Date arrivalDateTime) {
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	/**
	 * @param trainNumber
	 * @param departureDateTime
	 * @param arrivalDateTime
	 */
	public TimetableVO(String trainNumber, Date departureDateTime,
			Date arrivalDateTime) {
		this.trainNumber = trainNumber;
		this.departureDateTime = departureDateTime;
		this.arrivalDateTime = arrivalDateTime;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public int getRunId() {
		return runId;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public Date getDepartureDateTime() {
		return departureDateTime;
	}

	public void setDepartureDateTime(Date departureDateTime) {
		this.departureDateTime = departureDateTime;
	}

	public Date getArrivalDateTime() {
		return arrivalDateTime;
	}

	public void setArrivalDateTime(Date arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}


	
	
	
	
}
