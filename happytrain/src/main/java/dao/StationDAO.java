package dao;

import java.util.List;

import entities.Role;
import entities.Route;
import entities.Station;
import entities.Train;

public interface StationDAO extends GenericDAO<Integer, Station> {
	Station findByName(String str);
}
