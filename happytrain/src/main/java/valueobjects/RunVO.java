package valueobjects;

import java.util.Date;

import entities.Run;
import entities.Train;

public class RunVO {
	
	private int id;
	private Date startTime;
	private Date finishTime;
	private Train trainId;
	
	public RunVO() {
	}
	
	public RunVO(Run run) {
		this.id = run.getId();
		this.trainId = run.getTrainId();
		this.startTime = run.getStartTime();
		this.finishTime = run.getFinishTime();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}

	/**
	 * @return the trainId
	 */
	public Train getTrainId() {
		return trainId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	/**
	 * @param trainId the trainId to set
	 */
	public void setTrainId(Train trainId) {
		this.trainId = trainId;
	}

	
}
