/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import core.views.AirportFrame;
import core.models.Passenger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Fiorella W C
 */
//AirportFrame airport = new AirportFrame();
//airport.setVisible(true);
public class Main {

    public static void main(String[] args) {

        List<Flight> flights= new ArrayList<>();;
        List<Plane> planes= new ArrayList<>();;
        List<Passenger> passengers= new ArrayList<>();;
        List<Location> locations= new ArrayList<>();;

        Gson gson = new Gson();

        try {

            FileReader reader = new FileReader("src/flights.json"); // Ruta relativa si está en la carpeta src

            Type flightListType = new TypeToken<List<Flight>>() {
            }.getType();
            flights = gson.fromJson(reader, flightListType);

            for (Flight flight : flights) {
                System.out.println("ID: " + flight.getId() + ", Plane: " + flight.getPlane());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FileReader reader = new FileReader("src/planes.json"); // Ruta relativa si está en la carpeta src

            Type planeListType = new TypeToken<List<Plane>>() {
            }.getType();
            //List<Plane> aviones = gson.fromJson(reader, planeListType);
            planes = gson.fromJson(reader, planeListType);

            for (Plane plane : planes) {
                System.out.println("ID: " + plane.getId() + ", vuelo: " + plane.getFlights());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FileReader reader = new FileReader("src/passengers.json"); // Ruta relativa si está en la carpeta src

            Type passengerListType = new TypeToken<List<Passenger>>() {
            }.getType();
            //List<Passenger> pasajeros = gson.fromJson(reader, passengerListType);
            passengers = gson.fromJson(reader, passengerListType);

            for (Passenger passenger : passengers) {
                System.out.println("ID: " + passenger.getId() + ", vuelo: " + passenger.getFlights());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            FileReader reader = new FileReader("src/locations.json"); // Ruta relativa si está en la carpeta src

            Type locationListType = new TypeToken<List<Location>>() {
            }.getType();
            //List<Location> aereopuerto = gson.fromJson(reader, locationListType);
            locations = gson.fromJson(reader, locationListType);

            for (Location location : locations) {
                System.out.println("ID: " + location.getAirportId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, Plane> planeMap = new HashMap<>();
        Map<String, Flight> flightMap = new HashMap<>();
        Map<String, Location> locationMap = new HashMap<>();

        for (Plane p : planes) {
            planeMap.put(p.getId(), p);
        }

        for (Flight f : flights) {
            flightMap.put(f.getId(), f);
        }

        for (Location loc : locations) {
            locationMap.put(loc.getAirportId(), loc);
        }

        for (Flight f : flights) {
            Plane plane = planeMap.get(f.getPlane());
            if (plane != null) {
                plane.addFlight(f);
                f.setPlaneObject(plane); // si agregas un campo planeObject en Flight
            }
        }
        for (Flight f : flights) {
            Location dep = locationMap.get(f.getDepartureLocation());
            Location arr = locationMap.get(f.getArrivalLocation());
            Location scale = locationMap.get(f.getScaleLocation());

            if (dep != null) {
                dep.addDeparture(f);
            }
            if (arr != null) {
                arr.addArrival(f);
            }
            if (scale != null) {
                scale.addScale(f); // si implementas escala
            }
        }
    }

}
