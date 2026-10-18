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
public class Caja {

    private int numeroCaja;
    private Cola cola = new Cola();
    private Ticket.TipoTicket tipo;
    private Nodo enAtencion;
    private int cantTickets;

    public Caja(int numeroCaja, Ticket.TipoTicket tipo) {
        this.numeroCaja = numeroCaja;
        this.tipo = tipo;
        this.enAtencion = null;
        this.cantTickets = 0;
    }

    public int getCantTickets() {
        return cantTickets;
    }

    public void setCantTickets(int cantTickets) {
        this.cantTickets = cantTickets;
    }
    
    public void agregar(Ticket t){
        Nodo n = cola.agregar(t);
                
        //Si no hay mas tickets, ticket entra directamente en atencion
        if (this.cantTickets == 0){
            this.setEnAtencion(n);
            t.setAtendido(true);
            enAtencion.getDato().setHora_atencion(LocalDateTime.now());
        }
        
        this.cantTickets++;
    }

    public void setEnAtencion(Nodo enAtencion) {
        this.enAtencion = enAtencion;
    }   
    
    
    public void atender() {
        if(enAtencion != null){
            JOptionPane.showMessageDialog(null, "Se atendio al cliente: " + enAtencion.getDato().getNombre());
            enAtencion.getDato().setHora_atencion(LocalDateTime.now());        
            cola.atendido();
            this.cantTickets--;

            //Si hay siguiente:
            if(enAtencion.getSig() != null){
                enAtencion.getDato().setAtendido(true);
                enAtencion = enAtencion.getSig();
            }
            else{
                enAtencion = null;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "No hay clientes para atender");
        }
        
    }

    public Caja(Ticket.TipoTicket tipo) {
        this.tipo = tipo;
    }

    public Ticket.TipoTicket getTipo() {
        return tipo;
    }

    public void setTipo(Ticket.TipoTicket tipo) {
        this.tipo = tipo;

    }

    @Override
    public String toString() {
        
        //Si hay alguien en atencion
        String nom = "";
        
        if(enAtencion != null){
            nom = enAtencion.getDato().getNombre();
        }
        else{
            nom = "No hay nadie siendo atendido";
        }
        
        
        return "Caja #" + numeroCaja +
                "\nTipo: " + tipo +
                "\nEn Atencion: " + nom +
                "\nCantidad de tickets: " + cantTickets +
                "\n\nClientes en cola:\n" + cola.toString();
    }
    
    

}
