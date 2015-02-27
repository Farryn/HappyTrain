package entities;

import java.util.List;

import javax.persistence.*;

import valueobjects.RouteVO;
import valueobjects.StationVO;
import valueobjects.TrainVO;

@Entity
@Table(name = "route")
public class Route {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "station_ordinal_number")
	private int stationOrdinalNumber;

	@ManyToOne
	@JoinColumn(name = "train_id")
	private Train trainId;
	
	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station stationId;
	
	@OneToMany(targetEntity=Timetable.class, mappedBy="routeId")
    private List<Timetable> timetables;
	
	public Route() {
	}
	
	public Route(Train train, Station station, int stationOrdinalNumber ) {
		this.trainId = train;
		this.stationId = station;
		this.stationOrdinalNumber = stationOrdinalNumber;
	}

	

	public Route(RouteVO routeVO) {
		this.trainId = routeVO.getTrainId();
		this.stationId = routeVO.getStationId();
		this.stationOrdinalNumber = routeVO.getStationOrdinalNumber();
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
	 * @return the stationOrdinalNumber
	 */
	public int getStationOrdinalNumber() {
		return stationOrdinalNumber;
	}

	/**
	 * @param stationOrdinalNumber the stationOrdinalNumber to set
	 */
	public void setStationOrdinalNumber(int stationOrdinalNumber) {
		this.stationOrdinalNumber = stationOrdinalNumber;
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
	 * @return the stationId
	 */
	public Station getStationId() {
		return stationId;
	}

	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Station stationId) {
		this.stationId = stationId;
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
