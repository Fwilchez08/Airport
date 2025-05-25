/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fiorella W C
 */
public abstract class Sujeto {
    private List<Observer> observador = new ArrayList<>();
    
    public void agregarObservador(Observer o){
        observador.add(o); 
    }
    
    public void eliminrObservador(Observer o){
        observador.remove(o);
    }
    
    public void notificarObservadores(){
        for(Observer o: observador){
            o.actualizar();
        }
    }
    
}
