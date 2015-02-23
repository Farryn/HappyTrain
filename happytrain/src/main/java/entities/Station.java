package entities;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "station")
public class Station {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(targetEntity=Ticket.class, mappedBy="stationFrom")
    private List<Ticket> ticketsFrom;
	
	@OneToMany(targetEntity=Ticket.class, mappedBy="stationTo")
    private List<Ticket> ticketsTo;
	
	@OneToMany(targetEntity=Route.class, mappedBy="stationId")
    private List<Route> routes;
	
	public Station() {
	}
	
	public Station( String name) {
		this.name = name;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ticketsFrom
	 */
	public List<Ticket> getTicketsFrom() {
		return ticketsFrom;
	}

	/**
	 * @param ticketsFrom the ticketsFrom to set
	 */
	public void setTicketsFrom(List<Ticket> ticketsFrom) {
		this.ticketsFrom = ticketsFrom;
	}

	/**
	 * @return the ticketsTo
	 */
	public List<Ticket> getTicketsTo() {
		return ticketsTo;
	}

	/**
	 * @param ticketsTo the ticketsTo to set
	 */
	public void setTicketsTo(List<Ticket> ticketsTo) {
		this.ticketsTo = ticketsTo;
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
