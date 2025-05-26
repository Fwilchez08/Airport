/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.models.Flight;
import core.utils.Sujeto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author edangulo
 */
public class Plane extends Sujeto{
     private final String id;
    private String brand;
    private String model;
    private final int maxCapacity;
    private String airline;
    private List<Flight> flights;

    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>();
    }
    
    public Plane(Plane plane) {
        this(plane.id, plane.brand, plane.model, plane.maxCapacity, plane.airline);
        if (plane.flights != null) {
            for (Flight flight : plane.flights) {
                this.flights.add(flight != null ? flight.clonar() : null);
            }
        }
    }

    public void addFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Flight cannot be null");
        }
        this.flights.add(flight);
    }

    public List<Flight> getFlights() {
        return Collections.unmodifiableList(flights);
    }

    public Plane clonar() {
        return new Plane(this);
    }

    
    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public int getNumFlights() {
        return flights.size();
    }

    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
    
}
