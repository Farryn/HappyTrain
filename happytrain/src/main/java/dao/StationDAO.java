package dao;

import java.util.List;

import entities.Station;

/**
 * DAO for Station Entity.
 *
 */
public interface StationDAO extends GenericDAO<Integer, Station> {
	/**Get Station by given name.
	 * @param str Station name
	 * @return Station List
	 */
	List<Station> findByName(final String str);

}
