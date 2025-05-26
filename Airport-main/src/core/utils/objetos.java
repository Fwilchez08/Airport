/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.utils;
import com.formdev.flatlaf.json.Json;
import core.views.AirportFrame;
import core.models.Passenger;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.Storage;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author Fiorella W C
 */
public class objetos {
    
    public static void main(String[] args) {

        List<Flight> flights = new ArrayList<>();
        List<Plane> planes = new ArrayList<>();
        List<Passenger> passengers = new ArrayList<>();
        List<Location> locations = new ArrayList<>();


        try (FileReader reader = new FileReader("src/planes.json")) {
            var raw = (List<?>) Json.parse(reader);
            for (Object obj : raw) {
                if (obj instanceof Map) {
                    planes.add(new Plane((Plane) (Map<String, Object>) obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("src/passengers.json")) {
            var raw = (List<?>) Json.parse(reader);
            for (Object obj : raw) {
                if (obj instanceof Map) {
                    passengers.add(new Passenger((Passenger) (Map<String, Object>) obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (FileReader reader = new FileReader("src/locations.json")) {
            var raw = (List<?>) Json.parse(reader);
            for (Object obj : raw) {
                if (obj instanceof Map) {
                    locations.add(new Location((Location) (Map<String, Object>) obj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Plane> planeMap = planes.stream().collect(Collectors.toMap(Plane::getId, p -> p));
        Map<String, Flight> flightMap = flights.stream().collect(Collectors.toMap(Flight::getId, f -> f));
        Map<String, Location> locationMap = locations.stream().collect(Collectors.toMap(Location::getAirportId, l -> l));

        for (Flight f : flights) {
            Plane plane = planeMap.get(f.getPlane());
            if (plane != null) {
                plane.addFlight(f);
                f.setPlaneObject(plane);
            }
        }

        for (Flight f : flights) {
            Location dep = locationMap.get(f.getDepartureLocation());
            Location arr = locationMap.get(f.getArrivalLocation());
            Location scale = locationMap.get(f.getScaleLocation());

            if (dep != null) dep.addDeparture(f);
            if (arr != null) arr.addArrival(f);
            if (scale != null) scale.addScale(f);
        }

        planes.forEach(Storage.getInstance()::addAvion);
        locations.forEach(Storage.getInstance()::addAerepuerto);
        passengers.forEach(Storage.getInstance()::addPerson);
        flights.forEach(Storage.getInstance()::addVuelo);
    }
    
    
}
