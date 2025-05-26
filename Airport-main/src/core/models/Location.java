/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.utils.Sujeto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author edangulo
 */
public class Location extends Sujeto{
    
    
    private final String airportId;
    private final double airportLatitude;
    private final double airportLongitude;
   
    private String airportName;
    private String airportCity;
    private String airportCountry;
    
    
    private final List<Flight> departures = new CopyOnWriteArrayList<>();
    private final List<Flight> arrivals = new CopyOnWriteArrayList<>();
    private final List<Flight> scales = new CopyOnWriteArrayList<>();

    
   public Location(String airportId, String airportName, String airportCity, 
                   String airportCountry, double airportLatitude, 
                   double airportLongitude) {
        
        //validateParameters(airportId, airportLatitude, airportLongitude);
        
        this.airportId = airportId;
        this.airportName = airportName;
        this.airportCity = airportCity;
        this.airportCountry = airportCountry;
        this.airportLatitude = airportLatitude;
        this.airportLongitude = airportLongitude;
    }

    public Location(Location location) {
        this(location.airportId, location.airportName, location.airportCity,
             location.airportCountry, location.airportLatitude, 
             location.airportLongitude);
    }
    
    
    /*private void validateParameters(String airportId, double latitude, double longitude) {
        if (airportId == null || airportId.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport ID cannot be null or empty");
        }
        if (!airportId.matches("^[A-Z]{3}$")) {
            throw new IllegalArgumentException("Airport ID must be 3 uppercase letters");
        }
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }*/
    
    
    public String getAirportId() {
        return airportId;
    }

    public double getAirportLatitude() {
        return airportLatitude;
    }

    public double getAirportLongitude() {
        return airportLongitude;
    }
    
    
    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        if (airportName == null || airportName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airport name cannot be null or empty");
        }
        this.airportName = airportName;
    }

    public String getAirportCity() {
        return airportCity;
    }

    public void setAirportCity(String airportCity) {
        if (airportCity == null || airportCity.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        this.airportCity = airportCity;
    }

    public String getAirportCountry() {
        return airportCountry;
    }

    public void setAirportCountry(String airportCountry) {
        if (airportCountry == null || airportCountry.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
        this.airportCountry = airportCountry;
    }
    
   
    public List<Flight> getDepartures() {
        return Collections.unmodifiableList(departures);
    }

    public List<Flight> getArrivals() {
        return Collections.unmodifiableList(arrivals);
    }

    public List<Flight> getScales() {
        return Collections.unmodifiableList(scales);
    }

    public void addArrival(Flight flight) {
        validateFlight(flight);
        arrivals.add(flight);
    }

    public void addScale(Flight flight) {
        validateFlight(flight);
        scales.add(flight);
    }

    public void addDeparture(Flight flight) {
        validateFlight(flight);
        departures.add(flight);
    }
    
    private void validateFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }
    }
    
 
    public Location clonar() {
        Location cloned = new Location(this);
        
        
        this.departures.forEach(f -> cloned.departures.add(f.clonar()));
        this.arrivals.forEach(f -> cloned.arrivals.add(f.clonar()));
        this.scales.forEach(f -> cloned.scales.add(f.clonar()));
        
        return cloned;
    }
    

    public boolean hasInternationalFlights() {
        return departures.stream().anyMatch(f -> !f.getArrivalLocation().getAirportCountry().equals(this.airportCountry)) ||
               arrivals.stream().anyMatch(f -> !f.getDepartureLocation().getAirportCountry().equals(this.airportCountry));
    }
    
    public int getTotalFlightOperations() {
        return departures.size() + arrivals.size() + scales.size();
    }
    
    
}
