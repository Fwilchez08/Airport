/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Location {
    
    private final String airportId;
    private String airportName;
    private String airportCity;
    private String airportCountry;
    private double airportLatitude;
    private double airportLongitude;
    
    private List<Flight> departures = new ArrayList<>();
    private List<Flight> arrivals = new ArrayList<>();
    private List<Flight> scales = new ArrayList<>();

    public Location(String airportId, String airportName, String airportCity, String airportCountry, double airportLatitude, double airportLongitude) {
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
    }
    
    public Location(Location location) {
        this.airportId = location.airportId;
        this.airportName = location.airportName;
        this.airportCity = location.airportCity;
        this.airportCountry = location.airportCountry;
        this.airportLatitude = location.airportLatitude;
        this.airportLongitude = location.airportLongitude;
    }
    
    public String getAirportId() {
        return airportId;
    }

    public String getAirportName() {
        return airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public double getAirportLatitude() {
        return airportLatitude;
    }

    public double getAirportLongitude() {
        return airportLongitude;
    }
    
    public Location clonar(){
        return new Location(this); 
    }

    public List<Flight> getDepartures() {
        return departures;
    }

    public List<Flight> getArrivals() {
        return arrivals;
    }

    public List<Flight> getScales() {
        return scales;
    }

    public void addArrival(Flight flight) {
        arrivals.add(flight);
    }

    public void addScale(Flight flight) {
        scales.add(flight);
    }

    public void addDeparture(Flight f) {
        departures.add(f);
    }
    
    
}
