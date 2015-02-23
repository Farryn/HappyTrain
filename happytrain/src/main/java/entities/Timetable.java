package entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "timetable")
public class Timetable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "arr_time")
	private Date arrTime;
	
	@Column(name = "dep_time")
	private Date depTime;
	
	@Column(name = "available_seats")
	private int availableSeats;
	
	@ManyToOne
	@JoinColumn(name = "route_id")
	private Route routeId;
	
	@ManyToOne
	@JoinColumn(name = "run_id")
	private Run runId;
	
	public Timetable() {
	}
	
	public Timetable(Route routeId, Run runId, Date arrTime, Date depTime, int availableSeats) {
		this.routeId = routeId;
		this.runId = runId;
		this.arrTime = arrTime;
		this.depTime = depTime;
		this.availableSeats = availableSeats;
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
	 * @return the arrTime
	 */
	public Date getArrTime() {
		return arrTime;
	}

	/**
	 * @param arrTime the arrTime to set
	 */
	public void setArrTime(Date arrTime) {
		this.arrTime = arrTime;
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
	 * @return the availableSeats
	 */
	public int getAvailableSeats() {
		return availableSeats;
	}

	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	/**
	 * @return the routeId
	 */
	public Route getRouteId() {
		return routeId;
	}

	/**
	 * @param routeId the routeId to set
	 */
	public void setRouteId(Route routeId) {
		this.routeId = routeId;
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

	
}
