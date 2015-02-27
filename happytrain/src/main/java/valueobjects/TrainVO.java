package valueobjects;

import entities.Train;

public class TrainVO {

	private int id;
	private String number;
	private int seatsCount;
	
	public TrainVO() {
		
	}
	
	public TrainVO(Train train) {
		this.id = train.getId();
		this.number = train.getNumber();
		this.seatsCount = train.getSeatsCount();
	}

	
	public TrainVO(String number, int seatsCount) {
		super();
		this.number = number;
		this.seatsCount = seatsCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getSeatsCount() {
		return seatsCount;
	}

	public void setSeatsCount(int seatsCount) {
		this.seatsCount = seatsCount;
	}
}
