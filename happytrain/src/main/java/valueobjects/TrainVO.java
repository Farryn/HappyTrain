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

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @return the seatsCount
	 */
	public int getSeatsCount() {
		return seatsCount;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @param seatsCount the seatsCount to set
	 */
	public void setSeatsCount(int seatsCount) {
		this.seatsCount = seatsCount;
	}

	
}
