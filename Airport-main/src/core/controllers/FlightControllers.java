/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.Plane;
import java.time.LocalDateTime;
import core.models.Flight;
import core.models.storage.Storage;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Fiorella W C
 */
public class FlightControllers {

    public static Response CrearVuelo(String id, String PlaneId, String departureLocation, String arrivalLocation, String scale, int year, int month, int day, int hoursDurationArrival, int minutesDurationArrival, int hoursA, int minutesA, int hoursScale, int minutesScale) {
        try {

            if (id == null || id.trim().isEmpty()) {
                return new Response("la id no se ha registrado", Status.BAD_REQUEST);
            }

           if (!id.matches("^[A-Z]{3}\\d{3}$")){
                return new Response("La ID del avión debe seguir el formato: AAA999 (ej: MAS123)", Status.BAD_REQUEST);
            }
           
           /*if (PlaneId == null || PlaneId.trim().isEmpty()) {
                return new Response("El ID del avión no puede estar vacío", Status.BAD_REQUEST);
            }
            if (!PlaneId.matches("^[A-Z]{3}\\d{3}$")) {
                return new Response("La ID del avión debe seguir el formato: AAA999 (ej: MAS123)", Status.BAD_REQUEST);
            }*/

            if (hoursDurationArrival <= 0) {
                return new Response("la hora de llegada debe ser positiva  y diferente de cero", Status.BAD_REQUEST);
            }
            
            
            if (minutesDurationArrival <= 0) {
                return new Response("el numero de minutos debe ser positivo y diferente de cero", Status.BAD_REQUEST);
            }

            if (departureLocation == null || departureLocation.trim().isEmpty()) {
                return new Response("no se ingreso la locacion de salida", Status.BAD_REQUEST);
            }

            if (arrivalLocation == null || arrivalLocation.trim().isEmpty()) {
                return new Response("no se ingreso la locacion de llegada", Status.BAD_REQUEST);
            }

            if (hoursA <= 0) {
                return new Response("la hora de llegada debe ser mayor y distinta de cero", Status.BAD_REQUEST);

            }

            String minA = minutesA + "";
            if (minA == null || minA.trim().isEmpty()) {
                return new Response("no se registro los minutos de llegada", Status.BAD_REQUEST);
            }

            String horasS = hoursScale + "";
            if (horasS == null || horasS.trim().isEmpty()) {
                return new Response("no se registro la hora de la escala", Status.BAD_REQUEST);

            }

            String minS = minutesScale + "";
            if (minS == null || minS.trim().isEmpty()) {
                return new Response("no se registro los minutos de la escala", Status.BAD_REQUEST);

            }

            Storage storage = Storage.getInstance();

            
            Location locSalida = storage.getAereopurto(departureLocation);
            if (locSalida == null) {
                return new Response("locacion salida no encontrada", Status.BAD_REQUEST);
            }
            
            Location locLlegada = storage.getAereopurto(arrivalLocation);
            if (locLlegada == null) {
                return new Response("locaciob llegada no encontrada", Status.BAD_REQUEST);
            }
            
             Location origen = storage.getAereopurto(departureLocation);
            if (origen == null) {
                return new Response("Aeropuerto de salida no encontrado", Status.BAD_REQUEST);
            }

            Location destino = storage.getAereopurto(arrivalLocation);
            if (destino == null) {
                return new Response("Aeropuerto de llegada no encontrado", Status.BAD_REQUEST);
            }
            
             LocalDateTime fechaVuelo;
            try {
                fechaVuelo = LocalDateTime.of(year, month, day, hoursDurationArrival, minutesDurationArrival);
            } catch (Exception ex) {
                return new Response("Fecha de vuelo inválida", Status.BAD_REQUEST);
            }
            
            Plane avion = storage.getAvion(PlaneId);
            if (avion == null) {
                return new Response("Avión no encontrado", Status.BAD_REQUEST);
            }
            Flight vuelo = new Flight(PlaneId, Storage.getInstance().getAvion(PlaneId), Storage.getInstance().getAereopurto(departureLocation), Storage.getInstance().getAereopurto(arrivalLocation), LocalDateTime.of(year, month, day, hoursDurationArrival, minutesDurationArrival, 1), hoursDurationArrival, minutesDurationArrival);
            //Flight vuelo = new Flight(id, avion, origen, destino, fechaVuelo, hoursDurationArrival, minutesDurationArrival);

            if (!storage.addVuelo(vuelo)) {
                return new Response("El vuelo ya existe", Status.BAD_REQUEST);
            }

            ArrayList<Flight> vueloCopy = new ArrayList<>();
            vueloCopy.add(vuelo.clonar());
            return new Response("el vuelo se creo exitosamente", Status.CREATED);

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addFlight(String passengerId, String flightId) {
        try {


            // Válidar passengerId
            if (passengerId == null || passengerId.trim().isEmpty()) {
                return new Response("Passenger id must be not empty.", Status.BAD_REQUEST);
            }
            /*try {
                passengerIdLong = Long.parseLong(passengerId.trim());
                if (passengerIdLong <= 0) {
                    return new Response("la id del pasajero debe ser un número.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id del pasajero debe ser un número", Status.BAD_REQUEST);
            }*/
            if (passengerId.trim().length() > 15) {
                return new Response("la id del pasajero debe ser menor a 15 digitos", Status.BAD_REQUEST);
            }
            if (Storage.getInstance().getPassenger(passengerId) != null) {
                return new Response("la id del pasajero debe ser unica", Status.BAD_REQUEST);
            }

            // Válidar flightId
            if (flightId == null || flightId.trim().isEmpty()) {
                return new Response("no se registro la id del vuelo", Status.BAD_REQUEST);
            }

            Storage.getInstance().getPassenger(passengerId).addFlight(Storage.getInstance().getVuelo(flightId));
            Storage.getInstance().getVuelo(flightId).addPassenger(Storage.getInstance().getPassenger(passengerId));

            return new Response("Passenger added succesfully to the flight.", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response delayFlight(String flightId, int hours, int minutes) {
        try {
            String hoursStr = hours + "";
            String minutesStr = minutes + "";

            if (flightId == null || flightId.trim().isEmpty()) {
                return new Response("campo de vuelo no se ha registrado", Status.BAD_REQUEST);
            }

            // Válidar hours
            if (hoursStr == null || hoursStr.trim().isEmpty()) {
                return new Response("campo de Horas no debe estar vacio", Status.BAD_REQUEST);
            }

            // Válidar minutes
            if (minutesStr == null || minutesStr.trim().isEmpty()) {
                return new Response("campo de minutos no se ha registrado", Status.BAD_REQUEST);
            }

            if (hours <= 0 && minutes <= 0) {
                return new Response("la hora de retraso debe ser mayor a 00:o00.", Status.BAD_REQUEST);
            }

            Storage.getInstance().getVuelo(flightId).delay(hours, minutes);
            return new Response("se retraso el vuelo con exito.", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getSortedFlights() {
        try {
            ArrayList<Flight> flights = Storage.getInstance().getSortedFlights();
            ArrayList<Flight> flightsCopy = new ArrayList<>();
            if (flights != null) {
                for (Flight flight : flights) {
                    if (flight != null) {
                        flightsCopy.add(flight.clonar());
                    } else {
                        flightsCopy.add(null);
                    }
                }
            }
            return new Response("los vuelos se ordenaron con exito", Status.OK, flightsCopy);
        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
