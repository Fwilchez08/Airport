/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models.storage;

import core.models.Flight;
import core.models.Location;
import core.models.Passenger;
import core.models.Person;
import core.models.Plane;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Fiorella W C
 */
public class Storage {


    // Instancia Singleton
    private static Storage instance;

    // Atributos del Storage
    private ArrayList<Passenger> pass;
    private ArrayList<Flight> vuelo;
    private ArrayList<Location> aereopuerto;
    private ArrayList<Plane> avion;

    private Storage() {
        this.pass = new ArrayList<>();
        this.vuelo = new ArrayList<>();
        this.aereopuerto = new ArrayList<>();
        this.avion = new ArrayList<>();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
    
  
    public boolean addPerson(Passenger passenger) {
        for (Passenger p : this.pass) {
            if (p.getId().equals(passenger.getId())) {
                return false;
            }
        }
        this.pass.add(passenger);
        return true;
    }

    public boolean addVuelo(Flight flight) {
        for (Flight f : this.vuelo) {
            if (f.getId().equals(flight.getId())) {
                return false;
            }
        }
        this.vuelo.add(flight);
        return true;
    }

    public boolean addAerepuerto(Location location) {
        for (Location Loc : this.aereopuerto) {
            if (Loc.getAirportId().equals(location.getAirportId())) {
                return false;
            }
        }
        this.aereopuerto.add(location);
        return true;
    }

    public boolean addAvion(Plane plane) {
        for (Plane A : this.avion) {
            if (A.getId().equals(plane.getId())) {
                return false;
            }
        }
        this.avion.add(plane);
        return true;
    }

    public Passenger getPassenger(String id) {
        for (Passenger passenger : this.pass) {
            if (passenger.getId().equals(id)) {
                return passenger;
            }
        }
        return null;
    }

    public Location getAereopurto(String id) {
        for (Location location : this.aereopuerto) {
            if (location.getAirportId().equals(id)) {
                return location;
            }
        }
        return null;
    }

    public Flight getVuelo(String id) {
        for (Flight flight : this.vuelo) {
            if (flight.getId().equals(id)) {
                return flight;
            }
        }
        return null;
    }

    public Plane getAvion(String id) {
        for (Plane plane : this.avion) {
            if (plane.getId().equals(id)) {
                return plane;
            }
        }
        return null;
    }

    public boolean delPerson(String id) {
        for (Passenger pass : this.pass) {
            if (pass.getId().equals(id)) {
                this.pass.remove(pass);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Plane> organizarAviones() {
        ArrayList<Plane> sortedPlanes = new ArrayList<>(avion);
        for (Plane p : avion) {
            if (p != null && p.getId() != null) {
                sortedPlanes.add(p);
            }
        }
        sortedPlanes.sort(Comparator.comparing(Plane::getId));
        return sortedPlanes;
    }

    public boolean actualizarPasajero(Passenger passenger) {
        Passenger updatePassenger = null;
        for (Passenger p : pass) {
            if (p.getId().equals(passenger.getId())) {
                updatePassenger = p;
                break;
            }
        }
        if (updatePassenger == null) {
            return false;
        }
        updatePassenger.setFirstname(passenger.getFirstname());
        updatePassenger.setLastname(passenger.getLastname());
        updatePassenger.setBirthDate(passenger.getBirthDate());
        updatePassenger.setCountryPhoneCode(passenger.getCountryPhoneCode());
        updatePassenger.setPhone(passenger.getPhone());
        updatePassenger.setCountry(passenger.getCountry());
        return true;
    }
    
    public ArrayList<Flight> getPassengerFlights(Passenger passenger) {
        for (Passenger p : pass) {
            if (p.getId().equals( passenger.getId())) {
                return passenger.getFlights();
            }
        }
        return new ArrayList<>();
    }
    
    public ArrayList<Passenger> getSortedPassengers() {
        ArrayList<Passenger> sortedPassengers = new ArrayList<>(pass);
        for (Passenger p : pass) {
            if (p != null) {
                sortedPassengers.add(p);
            }
        }
        sortedPassengers.sort(Comparator.comparing(Passenger::getId));
        return sortedPassengers;
    }
    
    public ArrayList<Flight> getSortedFlights() {
        ArrayList<Flight> sortedFlights = new ArrayList<>(vuelo);
        for (Flight f : vuelo) {
            if (f != null) {
                sortedFlights.add(f);
            }
        }
        sortedFlights.sort(Comparator.comparing(Flight::getDepartureDate));
        return sortedFlights;
    }
    
     public ArrayList<Location> organizarAirport() {
        ArrayList<Location> sortedLocation = new ArrayList<>(aereopuerto);
        for (Location l : aereopuerto) {
            if (l != null) {
                sortedLocation.add(l);
            }
        }
        sortedLocation.sort(Comparator.comparing(Location::getAirportId));
        return sortedLocation;
    }

    public ArrayList<Plane> getSortedPlanes() {
        ArrayList<Plane> sortedPlanes = new ArrayList<>(avion);
        for (Plane p : avion) {
            if (p != null && p.getId() != null) {
                sortedPlanes.add(p);
            }
        }
        sortedPlanes.sort(Comparator.comparing(Plane::getId));
        return sortedPlanes;
    }
}
