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
    private Ticket.TipoTiquete tipo;
    private Ticket enAtencion;   
    
    private void atender(Ticket t) {
        t.setHora_atencion(LocalDateTime.now());
        enAtencion = t;
    }
    
    public Caja(tipoCaja tipo) {
        this.tipo = tipo;
    }

    public tipoCaja getTipo() {
        return tipo;
    }

    public void setTipo(tipoCaja tipo) {
        this.tipo = tipo;
        
    }
    
    
    
    
}
