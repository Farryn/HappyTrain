package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import services.StationService;
import dao.GenericDAOImpl;
import dao.RoleDAOImpl;
import dao.RouteDAOImpl;
import dao.StationDAOImpl;
import dao.TimetableDAOImpl;
import entities.Role;
import entities.Route;
import entities.Run;
import entities.Station;
import entities.Train;

public class App {

	
	public static void main(String[] args) {
		
		/*StationDAOImpl dao=new StationDAOImpl();
		dao.openCurrentSessionwithTransaction();
		Station A=(Station) dao.findById(2);
		Station B=(Station) dao.findById(4);
		dao.closeCurrentSessionwithTransaction();
		Date from=new Date();
		Date to=new Date();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
			String dateFrom = "01-01-2015 00:00:00";
			from = sdf.parse(dateFrom);
			String dateTo = "01-01-2015 00:05:00";
			to = sdf.parse(dateTo);
		}catch(Exception e){
		}
		
		RouteDAOImpl routedao=new RouteDAOImpl();
		routedao.openCurrentSessionwithTransaction();
		List<Route> routeList=routedao.findRouteFromAtoB(A, B);
		routedao.closeCurrentSessionwithTransaction();
		
		for (Route t : routeList) {
			System.out.println("Routes "+t.getId());
			
		}
		
		TimetableDAOImpl tdao=new TimetableDAOImpl();
		tdao.openCurrentSessionwithTransaction();
		List<Run> runList=tdao.findTrainWithDepTimeBetweenPeriodOfTime(routeList, from, to);
		tdao.closeCurrentSessionwithTransaction();
		*//*
		StationService ss=new StationService();
		List<Station> stList=ss.getAllStations();
		for (Station t : stList) {
			System.out.println(t.getName());
		}*/
	}
}
