/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinalestdatos;

import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 *
 * @author emmanuelR
 */
public class Main {

    static ArrayList<Caja> cajas = new ArrayList<>();
    static Configuracion config = new Configuracion();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        crearCajas();
        MenuPrincipal();

    }

    private static void crearCajas() {
        //Se crea caja preferencial (P)        
        cajas.add(new Caja(config.getIndexCajaPreferencial()+1, Ticket.TipoTicket.P));

        //Se crea caja rapida, un solo tramite (A)
        cajas.add(new Caja(config.getIndexCajaRapida()+1, Ticket.TipoTicket.A));

        //Se crean todas las cajas de tipo B (dos o mas tramites)
        //De 3 en adelante todas on tipo B
        crearCajasR(3, config.getCantCajas());

    }

    private static void crearCajasR(int n, int max) {

        //Caso base
        if (n > max) {
            return;
        }

        cajas.add(new Caja(n, Ticket.TipoTicket.B));

        crearCajasR(n + 1, max);

    }

    private static int pedirEdad() {

        while (true) {
            try {
                int edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la edad del cliente"));

                if (edad > config.getMin_edad() && edad < config.getMax_edad()) {
                    return edad;
                } else {
                    JOptionPane.showMessageDialog(null, "La edad es incorrecta, ingrese un numero entre "
                            + config.getMin_edad() + " y " + config.getMax_edad());
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El numero ingresado es incorrecto");
            }
        }

    }

    private static ArrayList<Ticket.Tramite> pedirTramites() {

        ArrayList<Ticket.Tramite> lista_tramites = new ArrayList<>();

        int res;

        //Se pide un tramite a la vez
        while (true) {

            res = JOptionPane.showOptionDialog(
                    null, // Esto siempre es nulo
                    "Elija el tramite que desea hacer:",
                    "Tramites",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    Ticket.Tramite.values(), // las opciones de tramites
                    null); // Opcion por defecto

            if (res == JOptionPane.CLOSED_OPTION) {
                // En caso de que se presiona cancelar o se cierre la ventana
            }

            //Agrega a la lista de tramites el tramite seleccionado
            lista_tramites.add(Ticket.Tramite.values()[res]);

            res = JOptionPane.showConfirmDialog(
                    null, // ventana padre (siempre nulo)
                    "¿Desea agregar otro trámite?", // mensaje
                    "Tramites", // titlo de ventana
                    JOptionPane.YES_NO_OPTION);        // botones

            if (res != JOptionPane.YES_OPTION) {
                return lista_tramites;

            }

        }

    }

    private static Ticket.TipoTicket pedir_tipo_tramite(int edad, int cant_tramites) {

        //En caso de que sea adulto mayor
        if (edad >= 65) {
            return Ticket.TipoTicket.P;
        }

        int res = JOptionPane.showConfirmDialog(
                null, // ventana padre (siempre nulo)
                "¿El cliente tiene alguna condición preferencial? (Embarazo, discapacidad o cliente preferencial)", // mensaje
                "Tramites", // titlo de ventana
                JOptionPane.YES_NO_OPTION);        // botones SI y NO

        if (res == JOptionPane.YES_OPTION) {
            return Ticket.TipoTicket.P;

        }

        if (cant_tramites == 1) {
            return Ticket.TipoTicket.A;
        } else {
            return Ticket.TipoTicket.B;
        }

    }

    private static void crearTicket() {

        //Se piden todos los datos al cliente
        try {
            String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente");
            int edad = pedirEdad();
            ArrayList<Ticket.Tramite> tramites = pedirTramites();
            Ticket.TipoTicket tipo = pedir_tipo_tramite(edad, tramites.size());

            Ticket t = new Ticket(config.actualizarContTiquet(), nombre, edad, tramites, tipo);

            enviarTicketParaCaja(t);

        } catch (Error e) {
            JOptionPane.showMessageDialog(null,
                    "Algo salio mal");
        }

    }

    private static void enviarTicketParaCaja(Ticket t) {

        //Si ticket es preferencial se envia a caja preferencial
        if (t.getTipo() == Ticket.TipoTicket.P) {
            cajas.get(config.getIndexCajaPreferencial()).agregar(t);
        } //Si es un solo tramite se envia a caja A (Caja rapida)
        else if (t.getTipo() == Ticket.TipoTicket.A) {
            cajas.get(config.getIndexCajaRapida()).agregar(t);
        } //De ser varios tramites se envia a Caja con menor cantidad de tickets
        else {

            Caja cajaMasVacia = cajas.get(3); //Inicia en 3 porque 1 y 2 son tipo P y A

            for (Caja c : cajas) {
                if (c.getTipo() == Ticket.TipoTicket.B){
                    if (c.getCantTickets() < cajaMasVacia.getCantTickets()) {
                        cajaMasVacia = c;
                    }                    
                }
            }

            cajaMasVacia.agregar(t);
        }
    }

    private static void verEstadoCajas() {

        for (Caja c : cajas) {
            JOptionPane.showMessageDialog(null, c.toString());
        }

    }

    private static void atenderTicket() {

        while (true) {
            try {
                String res = JOptionPane.showInputDialog("¿Cual Caja desea atender?");

                //En caso de que el usuario presionara Cancelar
                if (res == null) {
                    return;
                }

                int indexCaja = Integer.parseInt(res);

                if (indexCaja > 0 && indexCaja <= config.getCantCajas()) {

                    //En caso de que ingresara un numero de caja correcto, se atiende a esa caja
                    //Se pone -1 porque array comienza desde 0 no 1
                    cajas.get(indexCaja - 1).atender();
                    return;

                } else {
                    JOptionPane.showMessageDialog(null, "Debe de ingresar un numero entre 1 y "
                            + config.getCantCajas());
                }

            } // En caso de que el usuario ingrese letras:
            catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido");
            }
        }

    }

    private static void MenuPrincipal() {

        String res;

        do {
            res = JOptionPane.showInputDialog(" ******* MENU PRINCIPAL *******\n"
                    + "Bienvenido a " + config.getNomBanco() + "\n\n"
                    + "1) Agregar un tiquete\n2) Atender ticket\n3) Ver estado de las cajas\n4) Reportes\n5) Tipo de cambio del dólar\n0) SALIR\n\n"
                    + "Ingrese una opcion (0-3)");
            
            switch (res) {
                case "1":
                    crearTicket();
                    break;
                case "2":
                    atenderTicket();
                    break;
                case "3":
                    verEstadoCajas();
                    break;
                case "0":
                    JOptionPane.showMessageDialog(null,
                            "Cerrando el programa");
                    break;
                //Si se presiona cancelar
                case null:
                    JOptionPane.showMessageDialog(null,
                            "Cerrando el programa");
                    return;                    
                default:
                    JOptionPane.showMessageDialog(null,
                            "Error opcion no valida");
            }
        } while (!res.equals("0"));

    }

}
