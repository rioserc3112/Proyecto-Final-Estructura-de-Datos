/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalestdatos;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author emma3
 */
public class Ticket {
    private int id;
    private String nombre;
    private int edad;
    private LocalDateTime hora_creacion;
    private LocalDateTime hora_atencion;
    ArrayList<Tramite> tramites = new ArrayList<>();
    private TipoTicket tipo;
    private boolean atendido;
    
    public enum Tramite {
        DEPOSITOS,
        RETIROS,
        CAMBIO_DIVISAS,
        PAGO_SERVICIOS,
        PAGO_TARJETA,
        PAGO_PRESTAMO,
        TRANSFERENCIAS,
        OTROS
    }

    public enum TipoTicket {
        P,  // preferencial
        A,  // un solo trámite
        B   // dos o más trámites
    }

    public Ticket(int id, String nombre, int edad, ArrayList<Tramite> tramites, TipoTicket tipo) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.hora_creacion = LocalDateTime.now();
        this.hora_atencion = null;
        this.tramites.addAll(tramites);
        this.tipo = tipo;
        this.atendido = false;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public LocalDateTime getHora_creacion() {
        return hora_creacion;
    }

    public void setHora_creacion(LocalDateTime hora_creacion) {
        this.hora_creacion = hora_creacion;
    }

    public LocalDateTime getHora_atencion() {
        return hora_atencion;
    }

    public void setHora_atencion(LocalDateTime hora_atencion) {
        this.hora_atencion = hora_atencion;
    }

    public ArrayList<Tramite> getTramite() {
        return tramites;
    }


    public TipoTicket getTipo() {
        return tipo;
    }

    public void setTipo(TipoTicket tipo) {
        this.tipo = tipo;
    }
    
    
    
    }

