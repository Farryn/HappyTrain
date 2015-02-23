package entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;

	@Column(name = "dep_time")
	private Date depTime;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;
	
	@ManyToOne
	@JoinColumn(name = "run_id")
	private Run runId;
	
	@ManyToOne
	@JoinColumn(name = "station_from")
	private Station stationFrom;
	
	@ManyToOne
	@JoinColumn(name = "station_to")
	private Station stationTo;
	
	public Ticket() {
	}
	
	public Ticket(User userId, Run runId, Station stationFrom, Station stationTo, Date depTime) {
		this.userId = userId;
		this.runId = runId;
		this.stationFrom = stationFrom;
		this.stationTo = stationTo;
		this.depTime = depTime;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the depTime
	 */
	public Date getDepTime() {
		return depTime;
	}

	/**
	 * @param depTime the depTime to set
	 */
	public void setDepTime(Date depTime) {
		this.depTime = depTime;
	}

	/**
	 * @return the userId
	 */
	public User getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(User userId) {
		this.userId = userId;
	}

	/**
	 * @return the runId
	 */
	public Run getRunId() {
		return runId;
	}

	/**
	 * @param runId the runId to set
	 */
	public void setRunId(Run runId) {
		this.runId = runId;
	}

	/**
	 * @return the stationFrom
	 */
	public Station getStationFrom() {
		return stationFrom;
	}

	/**
	 * @param stationFrom the stationFrom to set
	 */
	public void setStationFrom(Station stationFrom) {
		this.stationFrom = stationFrom;
	}

	/**
	 * @return the stationTo
	 */
	public Station getStationTo() {
		return stationTo;
	}

	/**
	 * @param stationTo the stationTo to set
	 */
	public void setStationTo(Station stationTo) {
		this.stationTo = stationTo;
	}
	
	
	

	
}
