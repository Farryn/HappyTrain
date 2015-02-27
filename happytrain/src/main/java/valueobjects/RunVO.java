package valueobjects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Train getTrainId() {
		return trainId;
	}

	public void setTrainId(Train trainId) {
		this.trainId = trainId;
	}
}
