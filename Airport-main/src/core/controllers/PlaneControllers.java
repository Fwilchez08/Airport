/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Flight;
import core.models.Location;
import core.models.Plane;
import core.models.storage.Storage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fiorella W C
 */
public class PlaneControllers {
    public static Response crearAvion(String id, String brand, String model, int maxCapacity, String airline) {
        try {

            if (id == null || id.trim().isEmpty()) {
                return new Response("el id de el avion no puede estar vacío", Status.BAD_REQUEST);
            }

            if (Storage.getInstance().getAvion(id) != null) {
                return new Response("el avion debe ser unico", Status.BAD_REQUEST);
            }

            if (!id.trim().matches("^[A-Z]{2}\\d{5}$")) {
                return new Response("la id del avion debe seguir el formato: XXYYYYY (e.g. AB12345)", Status.BAD_REQUEST);
            }

            if (brand == null || brand.trim().isEmpty()) {
                return new Response("marca no se ha registrado", Status.BAD_REQUEST);
            }

            // válidar model
            if (model == null || model.trim().isEmpty()) {
                return new Response("Modelo no se ha registrado", Status.BAD_REQUEST);
            }

            if (maxCapacity < 0) {
                return new Response("Capacidad Maxima debe ser positiva", Status.BAD_REQUEST);
            }

            if (airline == null || airline.trim().isEmpty()) {
                return new Response("Aereolinea no se4 ha registrado", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();

            Plane plane = new Plane(id, brand, model, maxCapacity, airline);


            if (!storage.addAvion(new Plane(id, brand, model, maxCapacity, airline))) {
                return new Response("el avion ya existe", Status.BAD_REQUEST);
            }
            return new Response("el avion se creo exitosamente", Status.CREATED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }

    }

    public static Response getSortedPlanes() {
        try {
            ArrayList<Plane> aviones = Storage.getInstance().organizarAviones();
            ArrayList<Plane> copiaAviones = new ArrayList<>();
            if (aviones != null) {
                for (Plane plane : aviones) {
                    if (plane != null) {
                        copiaAviones.add(plane.clonar());
                    } else {
                        copiaAviones.add(null);
                    }
                }
            }
            return new Response("los aviones se agregaron con exito.", Status.OK, copiaAviones);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("Unexpected error.", Status.INTERNAL_SERVER_ERROR);
        }
    }


}
