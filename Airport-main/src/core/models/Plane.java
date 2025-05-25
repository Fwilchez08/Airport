/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.models.Flight;
import core.utils.Sujeto;
import java.util.ArrayList;

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
    private ArrayList<Flight> flights;
    private ArrayList<Location> aereopuerto;
    private ArrayList<Plane> avion;

    public Plane(String id, String brand, String model, int maxCapacity, String airline) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.maxCapacity = maxCapacity;
        this.airline = airline;
        this.flights = new ArrayList<>();
    }
    
    public Plane(Plane plane) {
        this.id = plane.id;
        this.brand = plane.brand;
        this.model = plane.model;
        this.maxCapacity = plane.maxCapacity;
        this.airline = plane.airline;
        this.flights = new ArrayList<>();
        if (plane.flights != null) {
            for (Flight flight : plane.flights) {
                if (flight != null) {
                    this.flights.add(flight.clonar());
                } else {
                    this.flights.add(null);
                }
            }
        }
    }
    

    public void addFlight(Flight flight) {
        this.flights.add(flight);
        
        notificarObservadores();
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

    public ArrayList<Flight> getFlights() {
        return flights;
    }
    
    public int getNumFlights() {
        return flights.size();
    }
    
    public Plane clonar() {
        return new Plane(this);
    }
    
}
