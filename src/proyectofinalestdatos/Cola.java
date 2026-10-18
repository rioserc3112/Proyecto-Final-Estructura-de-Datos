/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalestdatos;

import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author emma
 */
public class Cola {
    private Nodo prim, ult;
    
    public boolean esVacia(){
        return prim==null;
    }
    
    //encolar
    public Nodo agregar(Ticket t){
        Nodo nuevo = new Nodo(t);
        
        if(esVacia()){
            prim = ult = nuevo;
        }else{
            ult.setSig(nuevo);
            ult=nuevo;
        }
        return nuevo;
    }
    
    //desencolar
    public void atendido(){            
        if(!esVacia()){
            prim.getDato().setHora_atencion(LocalDateTime.now());
            prim = prim.getSig();
            if(prim == null){
                ult = null;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Cola vacia");
        }
    }
    
    //toString
    @Override
    public String toString() {
        
        if(prim==null){
            return "No hay clientes en cola";
        }
        
        String r = "";
        
        Nodo aux = prim;
        while (aux != null) {
            if(!aux.getDato().isAtendido()){
                r += aux.getDato().getNombre() + "\n";
            }
            aux = aux.getSig();
        }
        return r;
    }
    
   
    
    //Existe
    public boolean existe(int id) {
        if (!esVacia()) {
            if (prim.getDato().getId() == id ||
                    ult.getDato().getId() == id) {
                return true;
            } else {
                Nodo aux = prim;
                while (aux != null) {
                    if (aux.getDato().getId() == id) {
                        return true;
                    }
                    aux = aux.getSig();
                }
            }
        }
        return false;
    }
    
  
}

