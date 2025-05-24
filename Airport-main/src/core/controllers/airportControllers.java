/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Location;
import core.models.Passenger;
import core.models.Person;
import core.models.Plane;
import core.models.storage.Storage;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Fiorella W C
 */
public class airportControllers {

    public static Response CrearVuelo(String airportId, String airportName, String airportCity, String airportCountry, double airportLatitude, double airportLongitude) {
        try {
            BigDecimal number = new BigDecimal(airportId);
            int scala = number.stripTrailingZeros().scale();
            try {
                int longitud = airportId.length();
                if (longitud < 3 && airportId.equals(airportId.toLowerCase())) {
                    return new Response("Id no es valida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe tener X y Y", Status.BAD_REQUEST);
            }

            try {
                if (scala < 4) {
                    return new Response("la latitud no es valida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe estar al menos 4 cifras decimales", Status.BAD_REQUEST);
            }
            try {
                if (airportLatitude > 90 && airportLatitude < -90) {
                    return new Response("la latitud no esta en el perimetro requerido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe estar entre -90, 90", Status.BAD_REQUEST);
            }

            try {
                if (airportLongitude > 180 && airportLongitude < -180) {
                    return new Response("la longitud no esta en el perimetro requerido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe estar entre -180, 180", Status.BAD_REQUEST);
            }

            if (airportId == null || airportId.trim().isEmpty()) {
                return new Response("no se ingreso la id", Status.BAD_REQUEST);
            }

            if (airportName == null || airportName.trim().isEmpty()) {
                return new Response("no se ingreso el nombre de el aereopuerto", Status.BAD_REQUEST);
            }

            if (airportCity == null || airportCity.trim().isEmpty()) {
                return new Response("no se ingreso ciudad del aereopuerto", Status.BAD_REQUEST);
            }

            if (airportCountry == null || airportCountry.trim().isEmpty()) {
                return new Response("no se ingreso la locacion del aereopuerto", Status.BAD_REQUEST);
            }

            String longitudLatitud = airportLatitude + "";
            if (longitudLatitud == null || longitudLatitud.trim().isEmpty()) {
                return new Response("no se registro la latitud del aereopuerto", Status.BAD_REQUEST);
            }

            String longitudLongitud = airportLongitude + "";
            if (longitudLongitud == null || longitudLongitud.trim().isEmpty()) {
                return new Response("no se registro la longitud del aereopuerto", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();

            Location location = new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude);
            ArrayList<Location> airportCopy = new ArrayList<>();
            airportCopy.add(location.clonar());

            if (!storage.getInstance().addAerepuerto(new Location(airportId, airportName, airportCity, airportCountry, airportLatitude, airportLongitude))) {
                return new Response("A person with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("Pasajero ha sido creado", Status.CREATED, airportCopy);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
        
        

    public static Response getSortedAirport() {
        try {
            ArrayList<Location> aereopuerto = Storage.getInstance().organizarAirport();
            ArrayList<Location> copiaAirport = new ArrayList<>();
            if (aereopuerto != null) {
                for (Location location : aereopuerto) {
                    if (location != null) {
                        copiaAirport.add(location.clonar());
                    } else {
                        copiaAirport.add(null);
                    }
                }
            }
            return new Response("los aviones se agregaron con exito.", Status.OK, copiaAirport);
        } catch (Exception ex) {
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
