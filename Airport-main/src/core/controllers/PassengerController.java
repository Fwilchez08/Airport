/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Person;
import core.models.storage.Storage;
import core.views.AirportFrame;
import java.util.Scanner;
import core.models.Flight;
import core.models.Passenger;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Fiorella W C
 */
public class PassengerController {

    Scanner leer = new Scanner(System.in);

    public static Response AgregarPasajeros(String id, String firstname, String lastname, int year, int month, int day, int countryPhoneCode, long phone, String country) {

        try {
            long passengerIdLong;
            int firsnameInt;
            int lastnameInt;
            int yearInt, monthInt, dayInt;
            int phoneCodeInt;
            long phoneLong;
            int countryInt;

            String codnum = countryPhoneCode + "";

            String numlong = phone + "";
            String año = year + "";

            int ageInt;
            boolean genderB;

            try {
                int longitudId = id.length();
                if (longitudId < 0 && longitudId > 15) {
                    return new Response("el id es invalida", Status.BAD_REQUEST);
                }

            } catch (NumberFormatException ex) {
                return new Response("la Id debe tener minimo 15 digitos", Status.BAD_REQUEST);
            }

            if (Storage.getInstance().getPassenger(id) != null) {
                return new Response("la identificacion del pasagero debe ser unica", Status.BAD_REQUEST);
            }

            if (id == null || id.trim().isEmpty()) {
                return new Response("no se ha ingresado el id", Status.BAD_REQUEST);
            }
            if (codnum == null || codnum.trim().isEmpty()) {
                return new Response("no se ha ingresado el codigo", Status.BAD_REQUEST);
            }
            if (firstname == null || firstname.trim().isEmpty()) {
                return new Response("no se ha ingresado el nombre", Status.BAD_REQUEST);
            }
            if (numlong == null || numlong.trim().isEmpty()) {
                return new Response("no se ha ingresado el numero de telefono", Status.BAD_REQUEST);
            }

            if (lastname == null || lastname.trim().isEmpty()) {
                return new Response("no se ha ingresado el apellido", Status.BAD_REQUEST);
            }
            String mes = month + "";
            if (mes == null || mes.trim().isEmpty()) {
                return new Response("no se ha ingresado el mes", Status.BAD_REQUEST);
            }
            String dia = day + "";
            if (dia == null || dia.trim().isEmpty()) {
                return new Response("no se ha ingresado el dia", Status.BAD_REQUEST);
            }

            if (año == null || año.trim().isEmpty()) {
                return new Response("no se ha ingresado el año", Status.BAD_REQUEST);
            }

            if (country == null || country.trim().isEmpty()) {
                return new Response("no se ha registrado el pais", Status.BAD_REQUEST);
            }

            try {
                int longitud = año.length();
                if (longitud > 5 || longitud <= 0) {
                    return new Response("el año ingresado no es valido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("el año debe tener 4 digitos", Status.BAD_REQUEST);
            }

            int longcod = codnum.length();
            if (longcod < 0 && longcod > 3) {
                return new Response("el codigo es invalido", Status.BAD_REQUEST);
            }

            int longN = numlong.length();
            if (longN < 0 && longN < 11) {
                return new Response("el numero es invalido", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();
            Passenger passenger = new Passenger(id, firstname, lastname, LocalDate.of(year, month, day), countryPhoneCode, phone, country);

            if (!storage.getInstance().addPerson(new Passenger(id, firstname, lastname, LocalDate.of(year, month, day), countryPhoneCode, phone, country))) {
                return new Response("A person with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("El pasajero se creo con exito", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response Actualizar(String id, String firsname, String lastname, int year, int month, int day, int phoneCode, long phone, String country) {
        try {
            int firsnameInt;
            int lastnameInt;
            int yearInt, monthInt, dayInt;
            int phoneCodeInt;
            long phoneLong;
            int countryInt;

            if (Storage.getInstance().getPassenger(id) == null) {
                return new Response("Passenger with that id not exits.", Status.BAD_REQUEST);
            }

            // Válidar firstane
            if (firsname == null || firsname.trim().isEmpty()) {
                return new Response("Firstname must be not empty.", Status.BAD_REQUEST);
            }
            try {
                firsnameInt = Integer.parseInt(firsname.trim());
                return new Response("Firsname must be not a number.", Status.BAD_REQUEST);
            } catch (NumberFormatException ex) {

            }

            // Válidar lastname
            if (lastname == null || lastname.trim().isEmpty()) {
                return new Response("Lastname must be not empty.", Status.BAD_REQUEST);
            }
            try {
                lastnameInt = Integer.parseInt(lastname.trim());
                return new Response("Lastname must be not a number.", Status.BAD_REQUEST);
            } catch (NumberFormatException ex) {

            }

            // Válidar year
            String año = year + "";
            if (año == null || año.trim().isEmpty()) {
                return new Response("Year must be not empty.", Status.BAD_REQUEST);
            }
            try {
                if (year <= 0) {
                    return new Response("Year must be positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Year must be a number.", Status.BAD_REQUEST);
            }

            // Válidar month
            String mes = month + "";
            if (mes == null || mes.trim().isEmpty()) {
                return new Response("Month must be not empty.", Status.BAD_REQUEST);
            }
            try {
                if (month <= 0) {
                    return new Response("Month must be positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Month must be a number.", Status.BAD_REQUEST);
            }

            // Válidar day
            String dia = day + "";
            if (dia == null || dia.trim().isEmpty()) {
                return new Response("Day must be not empty.", Status.BAD_REQUEST);
            }
            try {
                if (day <= 0) {
                    return new Response("Day must be positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Day must be a number.", Status.BAD_REQUEST);
            }

            // Válidar phoneCode
            String cod = phoneCode + "";
            if (cod == null || cod.trim().isEmpty()) {
                return new Response("Phone code must be not empty.", Status.BAD_REQUEST);
            }
            if (cod.length() > 3) {
                return new Response("Phone code must have a maiximum of 3 digits.", Status.BAD_REQUEST);
            }
            try {

                if (phoneCode < 0) {
                    return new Response("Phone code must be positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Phone code must be a number.", Status.BAD_REQUEST);
            }

            String telefono = phone + "";
            if (telefono == null || telefono.trim().isEmpty()) {
                return new Response("Phone must be not empty.", Status.BAD_REQUEST);
            }
            if (telefono.length() > 11) {
                return new Response("Phone must have a maiximum of 11 digits.", Status.BAD_REQUEST);
            }
            try {
                if (phone < 0) {
                    return new Response("Phone must be positive.", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("Phone must be a number.", Status.BAD_REQUEST);
            }
            if (country == null || country.trim().isEmpty()) {
                return new Response("Country must be not empty.", Status.BAD_REQUEST);
            }
            try {
                countryInt = Integer.parseInt(country.trim());
                return new Response("Country must be not a number.", Status.BAD_REQUEST);
            } catch (NumberFormatException ex) {

            }

            Storage storage = Storage.getInstance();
            Passenger passenger = new Passenger(id, firsname, lastname, LocalDate.of(year, month, day), phoneCode, phone, country);
            ArrayList<Passenger> passengersCopy = new ArrayList<>();
            passengersCopy.add(passenger.clonar());

            if (!storage.getInstance().actualizarPasajero(new Passenger(id, firsname, lastname, LocalDate.of(year, month, day), phoneCode, phone, country))) {
                return new Response("Passenger id not found.", Status.NOT_FOUND);
            }

            return new Response("Passenger updated succesfully.", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response mostrrarVuelos(String passengerId) {
        try {

            if (passengerId == null || passengerId.trim().isEmpty()) {
                return new Response("Passenger id must be not empty.", Status.BAD_REQUEST);
            }
            if (passengerId.trim().length() > 15) {
                return new Response("Passenger id must have a maximum of 15 digits.", Status.BAD_REQUEST);
            }
            if (Storage.getInstance().getPassenger(passengerId) == null) {
                return new Response("Passenger id not exist.", Status.BAD_REQUEST);
            }

            ArrayList<Flight> flights = Storage.getInstance().getPassengerFlights(Storage.getInstance().getPassenger(passengerId));
            flights.sort(Comparator.comparing(Flight::getDepartureDate));

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

            return new Response("Flights loaded succesfully.", Status.OK);

        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getSortedPassengers() {
        try {
            ArrayList<Passenger> passengers = Storage.getInstance().getSortedPassengers();
            ArrayList<Passenger> passengersCopy = new ArrayList<>();
            if (passengers != null) {
                for (Passenger passenger : passengers) {
                    if (passenger != null) {
                        passengersCopy.add(passenger.clonar());
                    } else {
                        passengersCopy.add(null);
                    }
                }
            }
            return new Response("Passengers loaded succesfully.", Status.OK, passengersCopy);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
