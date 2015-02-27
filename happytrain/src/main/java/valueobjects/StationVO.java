package valueobjects;

import entities.Station;

public class StationVO {

	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String stationName) {
		this.name = stationName;
	}
	
	public StationVO(){
		
	}
	public StationVO(Station station){
		this.name = station.getName();
		this.id = station.getId();
	}
}
