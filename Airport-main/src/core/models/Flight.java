/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import core.utils.Sujeto;

/**
 *
 * @author edangulo
 */
public class Flight extends Sujeto {

     private final String id;
    private final Plane plane;
    private final Location departureLocation;
    private final Location arrivalLocation;
    
    
    private Location scaleLocation;
    private LocalDateTime departureDate;
    private final int hoursDurationArrival;
    private final int minutesDurationArrival;
    private int hoursDurationScale;
    private int minutesDurationScale;
    private final List<Passenger> passengers;
    private Plane planeObject;


    public Flight(String id, Plane plane, Location departureLocation, 
                Location arrivalLocation, LocalDateTime departureDate, 
                int hoursDurationArrival, int minutesDurationArrival) {
        this(id, plane, departureLocation, null, arrivalLocation, departureDate,
            hoursDurationArrival, minutesDurationArrival, 0, 0);
    }

    
    public Flight(String id, Plane plane, Location departureLocation, 
                Location scaleLocation, Location arrivalLocation, 
                LocalDateTime departureDate, int hoursDurationArrival, 
                int minutesDurationArrival, int hoursDurationScale, 
                int minutesDurationScale) {
        
        
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Flight ID cannot be null or empty");
        }
        if (plane == null) {
            throw new IllegalArgumentException("Plane cannot be null");
        }
        if (departureLocation == null || arrivalLocation == null) {
            throw new IllegalArgumentException("Departure and arrival locations cannot be null");
        }
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date cannot be null");
        }
        
        this.id = id;
        this.plane = plane;
        this.departureLocation = departureLocation;
        this.scaleLocation = scaleLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureDate = departureDate;
        this.hoursDurationArrival = hoursDurationArrival;
        this.minutesDurationArrival = minutesDurationArrival;
        this.hoursDurationScale = hoursDurationScale;
        this.minutesDurationScale = minutesDurationScale;
        this.passengers = new ArrayList<>();
        
        
        this.plane.addFlight(this);
    }

    
    private Flight(Flight flight) {
        this.id = flight.id;
        this.plane = flight.plane.clonar();
        this.departureLocation = flight.departureLocation.clonar();
        this.scaleLocation = flight.scaleLocation != null ? flight.scaleLocation.clonar() : null;
        this.arrivalLocation = flight.arrivalLocation.clonar();
        this.departureDate = flight.departureDate;
        this.hoursDurationArrival = flight.hoursDurationArrival;
        this.minutesDurationArrival = flight.minutesDurationArrival;
        this.hoursDurationScale = flight.hoursDurationScale;
        this.minutesDurationScale = flight.minutesDurationScale;
        this.passengers = new ArrayList<>();
        
      
        for (Passenger passenger : flight.passengers) {
            this.passengers.add(passenger != null ? passenger.clonar() : null);
        }
        
        this.planeObject = flight.planeObject != null ? flight.planeObject.clonar() : null;
    }

  


    public LocalDateTime calculateArrivalDate() {
        return departureDate.plusHours(hoursDurationScale + hoursDurationArrival)
                          .plusMinutes(minutesDurationScale + minutesDurationArrival);
    }

    public void delay(int hours, int minutes) {
        if (hours < 0 || minutes < 0) {
            throw new IllegalArgumentException("Delay time cannot be negative");
        }
        this.departureDate = this.departureDate.plusHours(hours).plusMinutes(minutes);
    }

    public String getId() {
        return id;
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public void addPassenger(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Passenger cannot be null");
        }
        this.passengers.add(passenger);
    }

    
    public Location getDepartureLocation() {
        return departureLocation;
    }

    public Location getScaleLocation() {
        return scaleLocation;
    }

    public Location getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public int getHoursDurationArrival() {
        return hoursDurationArrival;
    }

    public int getMinutesDurationArrival() {
        return minutesDurationArrival;
    }

    public int getHoursDurationScale() {
        return hoursDurationScale;
    }

    public int getMinutesDurationScale() {
        return minutesDurationScale;
    }

    public Plane getPlane() {
        return plane;
    }

    public int getNumPassengers() {
        return passengers.size();
    }

    
    public void setDepartureDate(LocalDateTime departureDate) {
        if (departureDate == null) {
            throw new IllegalArgumentException("Departure date cannot be null");
        }
        this.departureDate = departureDate;
    }

    public void setScaleLocation(Location scaleLocation) {
        this.scaleLocation = scaleLocation;
    }

    public void setScaleDuration(int hours, int minutes) {
        if (hours < 0 || minutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        this.hoursDurationScale = hours;
        this.minutesDurationScale = minutes;
    }

    
    public void setPlaneObject(Plane plane) {
        this.planeObject = plane;
    }

    public Plane getPlaneObject() {
        return this.planeObject;
    }

    
    public Flight clonar() {
        return new Flight(this);
    }
}
