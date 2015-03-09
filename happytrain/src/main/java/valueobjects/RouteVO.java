package valueobjects;



import entities.Route;
import entities.Station;
import entities.Train;

public class RouteVO {	
	
	private int id;
	private int stationOrdinalNumber;
	private Train trainId;
	private Station stationId;

	public RouteVO() {
		
	}
	public RouteVO(int stationOrdinalNumber, Train trainId, Station stationId) {
		super();
		this.stationOrdinalNumber = stationOrdinalNumber;
		this.trainId = trainId;
		this.stationId = stationId;
	}
	public RouteVO(Route route) {
		super();
		this.stationOrdinalNumber = route.getStationOrdinalNumber();
		this.trainId = route.getTrainId();
		this.stationId = route.getStationId();
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the stationOrdinalNumber
	 */
	public int getStationOrdinalNumber() {
		return stationOrdinalNumber;
	}
	/**
	 * @return the trainId
	 */
	public Train getTrainId() {
		return trainId;
	}
	/**
	 * @return the stationId
	 */
	public Station getStationId() {
		return stationId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param stationOrdinalNumber the stationOrdinalNumber to set
	 */
	public void setStationOrdinalNumber(int stationOrdinalNumber) {
		this.stationOrdinalNumber = stationOrdinalNumber;
	}
	/**
	 * @param trainId the trainId to set
	 */
	public void setTrainId(Train trainId) {
		this.trainId = trainId;
	}
	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Station stationId) {
		this.stationId = stationId;
	}
	
	
	
}
