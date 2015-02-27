package entities;



import java.util.Date;
import java.util.List;

import javax.persistence.*;

import valueobjects.RunVO;

@Entity
@Table(name = "run")
public class Run {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "start_time")
	private Date startTime;
	
	@Column(name = "finish_time")
	private Date finishTime;
	
	@ManyToOne
	@JoinColumn(name = "train_id")
	private Train trainId;
	
	@OneToMany(targetEntity=Ticket.class, mappedBy="runId")
    private List<Ticket> tickets;
	
	@OneToMany(targetEntity=Timetable.class, mappedBy="runId")
    private List<Timetable> timetables;
	
	public Run() {
	}
	
	public Run(final Train trainId, final Date startTime, final Date finishTime) {
		this.trainId = trainId;
		this.startTime = startTime;
		this.finishTime = finishTime;
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
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @return the trainId
	 */
	public Train getTrainId() {
		return trainId;
	}

	/**
	 * @param trainId the trainId to set
	 */
	public void setTrainId(Train trainId) {
		this.trainId = trainId;
	}

	/**
	 * @return the tickets
	 */
	public List<Ticket> getTickets() {
		return tickets;
	}

	/**
	 * @param tickets the tickets to set
	 */
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	/**
	 * @return the timetables
	 */
	public List<Timetable> getTimetables() {
		return timetables;
	}

	/**
	 * @param timetables the timetables to set
	 */
	public void setTimetables(List<Timetable> timetables) {
		this.timetables = timetables;
	}

	
	
}
