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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStationOrdinalNumber() {
		return stationOrdinalNumber;
	}
	public void setStationOrdinalNumber(int stationOrdinalNumber) {
		this.stationOrdinalNumber = stationOrdinalNumber;
	}
	public Train getTrainId() {
		return trainId;
	}
	public void setTrainId(Train trainId) {
		this.trainId = trainId;
	}
	public Station getStationId() {
		return stationId;
	}
	public void setStationId(Station stationId) {
		this.stationId = stationId;
	}
	
}
