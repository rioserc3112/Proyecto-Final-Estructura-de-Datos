/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinalestdatos;

import javax.swing.JOptionPane;
import java.util.ArrayList;

/**
 * Punto de entrada del sistema de atención del banco.
 */
public class Main {

    static ArrayList<Caja> cajas = new ArrayList<>();
    static Configuracion config;
    static Login login;

    public static void main(String[] args) {
        // La autenticación se realiza antes de mostrar o cargar los módulos del banco.
        login = new Login();
        if (!login.autenticar()) {
            JOptionPane.showMessageDialog(null, "Acceso denegado. Cerrando el programa");
            return;
        }

        config = new Configuracion();
        crearCajas();
        MenuPrincipal();
    }

    private static void crearCajas() {
        cajas.clear();
        for (int numero = 1; numero <= config.getCantCajas(); numero++) {
            cajas.add(new Caja(numero, config.getTipoCaja(numero)));
        }
    }

    private static int pedirEdad() {
        while (true) {
            String entrada = JOptionPane.showInputDialog("Ingrese la edad del cliente");
            if (entrada == null) {
                return -1;
            }
            try {
                int edad = Integer.parseInt(entrada.trim());
                if (edad > config.getMin_edad() && edad < config.getMax_edad()) {
                    return edad;
                }
                JOptionPane.showMessageDialog(null,
                        "La edad es incorrecta, ingrese un número entre "
                        + config.getMin_edad() + " y " + config.getMax_edad());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "El número ingresado es incorrecto");
            }
        }
    }

    private static ArrayList<Ticket.Tramite> pedirTramites() {
        ArrayList<Ticket.Tramite> listaTramites = new ArrayList<>();
        while (true) {
            int seleccion = JOptionPane.showOptionDialog(
                    null, "Elija el trámite que desea hacer:", "Trámites",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, Ticket.Tramite.values(), null);

            if (seleccion == JOptionPane.CLOSED_OPTION) {
                return listaTramites;
            }
            listaTramites.add(Ticket.Tramite.values()[seleccion]);

            int otro = JOptionPane.showConfirmDialog(null,
                    "¿Desea agregar otro trámite?", "Trámites", JOptionPane.YES_NO_OPTION);
            if (otro != JOptionPane.YES_OPTION) {
                return listaTramites;
            }
        }
    }

    private static Ticket.TipoTicket pedir_tipo_tramite(int edad, int cantTramites) {
        if (edad >= 65) {
            return Ticket.TipoTicket.P;
        }

        int res = JOptionPane.showConfirmDialog(null,
                "¿El cliente tiene alguna condición preferencial? (Embarazo, discapacidad o cliente preferencial)",
                "Trámites", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            return Ticket.TipoTicket.P;
        }
        return cantTramites == 1 ? Ticket.TipoTicket.A : Ticket.TipoTicket.B;
    }

    private static void crearTicket() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre del cliente");
        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }
        int edad = pedirEdad();
        if (edad < 0) {
            return;
        }
        ArrayList<Ticket.Tramite> tramites = pedirTramites();
        if (tramites.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar al menos un trámite");
            return;
        }
        Ticket.TipoTicket tipo = pedir_tipo_tramite(edad, tramites.size());
        Ticket ticket = new Ticket(config.actualizarContTiquet(), nombre.trim(), edad, tramites, tipo);
        enviarTicketParaCaja(ticket);
    }

    private static void enviarTicketParaCaja(Ticket ticket) {
        if (ticket.getTipo() == Ticket.TipoTicket.P) {
            cajas.get(config.getIndexCajaPreferencial()).agregar(ticket);
        } else if (ticket.getTipo() == Ticket.TipoTicket.A) {
            cajas.get(config.getIndexCajaRapida()).agregar(ticket);
        } else {
            Caja cajaMasVacia = null;
            for (Caja caja : cajas) {
                if (caja.getTipo() == Ticket.TipoTicket.B
                        && (cajaMasVacia == null || caja.getCantTickets() < cajaMasVacia.getCantTickets())) {
                    cajaMasVacia = caja;
                }
            }
            if (cajaMasVacia != null) {
                cajaMasVacia.agregar(ticket);
            }
        }
    }

    private static void verEstadoCajas() {
        for (Caja caja : cajas) {
            JOptionPane.showMessageDialog(null, caja.toString());
        }
    }

    private static void atenderTicket() {
        while (true) {
            String entrada = JOptionPane.showInputDialog("¿Cuál caja desea atender?");
            if (entrada == null) {
                return;
            }
            try {
                int numeroCaja = Integer.parseInt(entrada.trim());
                if (numeroCaja > 0 && numeroCaja <= config.getCantCajas()) {
                    cajas.get(numeroCaja - 1).atender();
                    return;
                }
                JOptionPane.showMessageDialog(null,
                        "Debe ingresar un número entre 1 y " + config.getCantCajas());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido");
            }
        }
    }

    private static void MenuPrincipal() {
        String res;
        do {
            res = JOptionPane.showInputDialog(" ******* MENU PRINCIPAL *******\n"
                    + "Usuario: " + login.getUsuarioActual() + " (" + login.getRolActual() + ")\n"
                    + "Bienvenido a " + config.getNomBanco() + "\n\n"
                    + "1) Agregar un tiquete\n2) Atender ticket\n3) Ver estado de las cajas\n"
                    + "4) Reportes\n5) Tipo de cambio del dólar\n0) SALIR\n\n"
                    + "Ingrese una opción (0-5)");
            if (res == null) {
                JOptionPane.showMessageDialog(null, "Cerrando el programa");
                return;
            }
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
                case "4":
                case "5":
                    JOptionPane.showMessageDialog(null, "Funcionalidad en desarrollo");
                    break;
                case "0":
                    JOptionPane.showMessageDialog(null, "Cerrando el programa");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Error: opción no válida");
            }
        } while (!res.equals("0"));
    }
}
