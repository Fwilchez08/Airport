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
import java.util.List;

/**
 *
 * @author Fiorella W C
 */
public class airportControllers {
    public static Response CrearAereopuerto(String airportId, String airportName, String airportCity, String airportCountry, String airportLatitude, String airportLongitude) {
        try {
            BigDecimal number1 = new BigDecimal(airportLatitude);
            int scala1 = number1.stripTrailingZeros().scale();
            
            BigDecimal number2 = new BigDecimal(airportLongitude);
            int scala2 = number2.stripTrailingZeros().scale();
            try {
                int longitud = airportId.length();
                if (longitud < 3 && airportId.equals(airportId.toLowerCase())) {
                    return new Response("Id no es valida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la latitud debe tener X y Y", Status.BAD_REQUEST);
            }

            try {
                if (scala1 < 4) {
                    return new Response("la latitud no es valida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe estar al menos 4 cifras decimales", Status.BAD_REQUEST);
            }
            
            try {
                if (scala2< 4) {
                    return new Response("la longitud no es valida", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la longitud debe estar al menos 4 cifras decimales", Status.BAD_REQUEST);
            }
            double Latitud = Double.parseDouble(airportLatitude);
            double Longitud = Double.parseDouble(airportLongitude);
            try {
                if (Latitud > 90 && Latitud < -90) {
                    return new Response("la latitud no esta en el perimetro requerido", Status.BAD_REQUEST);
                }
            } catch (NumberFormatException ex) {
                return new Response("la id debe estar entre -90, 90", Status.BAD_REQUEST);
            }

            
            try {
                if (Longitud > 180 && Longitud < -180) {
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

            if (airportLatitude == null || airportLatitude.trim().isEmpty()) {
                return new Response("no se registro la latitud del aereopuerto", Status.BAD_REQUEST);
            }
            if (airportLongitude == null || airportLongitude.trim().isEmpty()) {
                return new Response("no se registro la longitud del aereopuerto", Status.BAD_REQUEST);
            }

            
            
            Storage storage = Storage.getInstance();
            Location location = new Location(airportId, airportName, airportCity, airportCountry, Latitud, Longitud);

            if (!storage.addAerepuerto(new Location(airportId, airportName, airportCity, airportCountry, Latitud, Longitud))) {
                return new Response("A person with that id already exists", Status.BAD_REQUEST);
            }
            return new Response("el aereopuerto ha sido creado", Status.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
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
