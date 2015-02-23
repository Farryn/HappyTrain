package entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "train")
public class Train {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "number")
	private String number;
	
	@Column(name = "seats_count")
	private int seatsCount;
	
	@OneToMany(targetEntity=Run.class, mappedBy="trainId")
    private List<Run> runs;
	
	@OneToMany(targetEntity=Route.class, mappedBy="trainId")
    private List<Route> routes;
	
	public Train() {
	}
	
	public Train(String number, int seatsCount) {
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
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}

	/**
	 * @return the seatsCount
	 */
	public int getSeatsCount() {
		return seatsCount;
	}

	/**
	 * @param seatsCount the seatsCount to set
	 */
	public void setSeatsCount(int seatsCount) {
		this.seatsCount = seatsCount;
	}

	/**
	 * @return the runs
	 */
	public List<Run> getRuns() {
		return runs;
	}

	/**
	 * @param runs the runs to set
	 */
	public void setRuns(List<Run> runs) {
		this.runs = runs;
	}

	/**
	 * @return the routes
	 */
	public List<Route> getRoutes() {
		return routes;
	}

	/**
	 * @param routes the routes to set
	 */
	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	
}
