/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalestdatos;

import java.time.LocalDateTime;

/**
 *
 * @author emma
 */
public class Caja {

    private int numeroCaja;
    private Cola cola = new Cola();
    private Ticket.TipoTicket tipo;
    private Ticket enAtencion;
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
        cola.agregar(t);
    }
    

    private void atender(Ticket t) {
        t.setHora_atencion(LocalDateTime.now());
        enAtencion = t;
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

}
