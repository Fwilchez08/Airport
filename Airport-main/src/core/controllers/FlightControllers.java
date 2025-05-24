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

    public static Response CrearVuelo(long id, String PlaneId, String departureLocation, String arrivalLocation, String scale, int year, int month, int day, int hoursDurationArrival, int minutesDurationArrival) {
        try {
            String idStr = id + "";

            try {

                int longitud = idStr.length();

                if (!idStr.trim().matches("^[A-Z]{2}\\d{5}$")) {
                    return new Response("Plane id must be a valid format: XXYYYYY (e.g. AB12345)", Status.BAD_REQUEST);
                }
                if (idStr == "Y") {
                    if (longitud < 0 && longitud > 9) {
                        return new Response("la id es invalida", Status.BAD_REQUEST);
                    }
                    return new Response("Id must be positive", Status.BAD_REQUEST);
                } else {
                    if (idStr == "X") {
                        if (longitud < 3) {
                            return new Response("la id es invalida", Status.BAD_REQUEST);
                        }
                    } else {
                        return new Response("la id no cumple con el formato", Status.BAD_REQUEST);
                    }
                }

            } catch (NumberFormatException ex) {
                return new Response("la id debe tener X y Y", Status.BAD_REQUEST);
            }
            String hora = hoursDurationArrival + "";
            if (hora.equals("")) {
                return new Response("no se ingreso hora de llegada", Status.BAD_REQUEST);
            }

            String min = minutesDurationArrival + "";
            if (min== null || min.trim().isEmpty()) {
                return new Response("no se ingreso minutos de llegada", Status.BAD_REQUEST);
            }

            String locacion1 = departureLocation + "";
            if (locacion1== null || locacion1.trim().isEmpty()) {
                return new Response("no se ingreso la locacion de salida", Status.BAD_REQUEST);
            }

            try {
                if (hoursDurationArrival == 0 && minutesDurationArrival == 0) {
                    return new Response("El tiempo de vuelo es invalido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("el tiempo debe ser mayor a cero", Status.BAD_REQUEST);
            }
            String locacion2 = arrivalLocation + "";
            if (locacion2== null || locacion2.trim().isEmpty()) {
                return new Response("no se ingreso la locacion de llegada", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();

            Flight flight = new Flight(idStr, Storage.getInstance().getAvion(PlaneId), Storage.getInstance().getAereopurto(departureLocation), Storage.getInstance().getAereopurto(arrivalLocation), LocalDateTime.of(year, month, day, hoursDurationArrival, minutesDurationArrival, 1), hoursDurationArrival, minutesDurationArrival);
            ArrayList<Flight> vueloCopy = new ArrayList<>();
            vueloCopy.add(flight.clonar());

            Flight vuelo = new Flight(idStr, Storage.getInstance().getAvion(PlaneId), Storage.getInstance().getAereopurto(departureLocation), Storage.getInstance().getAereopurto(arrivalLocation), LocalDateTime.of(year, month, day, hoursDurationArrival, minutesDurationArrival, 1), hoursDurationArrival, minutesDurationArrival);

            if (!storage.getInstance().addVuelo(vuelo)) {
                return new Response("el vuelo ya existe", Status.BAD_REQUEST);
            }
            return new Response("el vuelo se creo exitosamente", Status.CREATED, vueloCopy);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response addFlight(String passengerId, String flightId) {
        try {

            int passengerIdInt = Integer.parseInt(passengerId);
            long passengerIdLong;
            int flightIdInt;

            // Válidar passengerId
            if (passengerId == null || passengerId.trim().isEmpty()) {
                return new Response("Passenger id must be not empty.", Status.BAD_REQUEST);
            }
            try {
                passengerIdLong = Long.parseLong(passengerId.trim());
                if (passengerIdLong <= 0) {
                    return new Response("la id del pasajero debe ser un número.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id del pasajero debe ser un número", Status.BAD_REQUEST);
            }
            if (passengerId.trim().length() > 15) {
                return new Response("la id del pasajero debe ser menor a 15 digitos", Status.BAD_REQUEST);
            }
            if (Storage.getInstance().getPassenger(passengerIdInt) != null) {
                return new Response("la id del pasajero debe ser unica", Status.BAD_REQUEST);
            }

            // Válidar flightId
            if (flightId == null || flightId.trim().isEmpty()) {
                return new Response("no se registro la id del vuelo", Status.BAD_REQUEST);
            }
            try {
                flightIdInt = Integer.parseInt(flightId.trim());
            } catch (NumberFormatException ex) {
                return new Response("la id del vuelo debe ser un número.", Status.BAD_REQUEST);
            }

            Storage.getInstance().getPassenger(passengerIdInt).addFlight(Storage.getInstance().getVuelo(flightIdInt));
            Storage.getInstance().getVuelo(flightIdInt).addPassenger(Storage.getInstance().getPassenger(passengerIdInt));
            
            return new Response("Passenger added succesfully to the flight.", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response delayFlight(String flightId, int hours, int minutes) {
        try {
            int flightIdInt;
            String hoursStr = hours + "";
            String minutesStr = minutes + "";

            if (flightId == null || flightId.trim().isEmpty()) {
                return new Response("campo de vuelo no se ha registrado", Status.BAD_REQUEST);
            }
            try {
                flightIdInt = Integer.parseInt(flightId.trim());
            } catch (NumberFormatException ex) {
                return new Response("campo de id de vuelo debe ser un número ", Status.BAD_REQUEST);
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

            Storage.getInstance().getVuelo(flightIdInt).delay(hours, minutes);
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
