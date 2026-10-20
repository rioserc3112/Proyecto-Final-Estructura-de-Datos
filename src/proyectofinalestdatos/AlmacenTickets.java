/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalestdatos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Emmanuel
 */
public class AlmacenTickets {
    
    public static final String ARCHIVO_DEFECTO = "tiquetes.txt";
    private static final String SEP = "|";
    private static final String SIN_HORA = "-";
 
    private final Path ruta;
 
    public AlmacenTickets() {
        this(ARCHIVO_DEFECTO);
    }
 
    public AlmacenTickets(String nombreArchivo) {
        this.ruta = Paths.get(nombreArchivo);
    }
 
    // ------------------------------------------------ conversión tiquete <-> línea
 
    /** Tiquete -> línea de texto. */
    public static String ticketALinea(Ticket t) {
        String atencion = (t.getHora_atencion() == null) ? SIN_HORA : t.getHora_atencion().toString();
        String nombre = t.getNombre().replace(SEP, " "); // evita romper el formato
        return t.getId() + SEP + nombre + SEP + t.getEdad() + SEP
                + t.getHora_creacion() + SEP + atencion + SEP
                + t.getTramite() + SEP + t.getTipo();
    }
 
    /** Línea de texto -> tiquete. Devuelve null si la línea es inválida. */
    public static Ticket lineaATicket(String linea) {
        String[] p = linea.split("\\|", -1);
        if (p.length != 7) {
            return null;
        }
        try {
            Ticket t = new Ticket(
                    Integer.parseInt(p[0].trim()),
                    p[1],
                    Integer.parseInt(p[2].trim()),
                    Ticket.Tramite.valueOf(p[5].trim()),
                    Ticket.TipoTiquete.valueOf(p[6].trim()));
            t.setHora_creacion(LocalDateTime.parse(p[3].trim()));
            if (!p[4].trim().equals(SIN_HORA)) {
                t.setHora_atencion(LocalDateTime.parse(p[4].trim()));
            }
            return t;
        } catch (RuntimeException e) { // formato de número, enum o fecha inválido
            return null;
        }
    }
 
    // --------------------------------------------------------- operaciones de archivo
 
    /** Agrega un tiquete al final del archivo (sin borrar lo anterior). */
    public boolean agregarLinea(Ticket t) {
        try {
            Files.write(ruta, Collections.singletonList(ticketALinea(t)), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el tiquete: " + e.getMessage());
            return false;
        }
    }
 
    /** Lee todas las líneas y las convierte en tiquetes (en orden de fila). */
    public List<Ticket> leerTodos() {
        List<Ticket> tiquetes = new ArrayList<>();
        if (!Files.exists(ruta)) {
            return tiquetes;
        }
        try {
            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                if (linea.trim().isEmpty()) {
                    continue;
                }
                Ticket t = lineaATicket(linea);
                if (t != null) {
                    tiquetes.add(t);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer " + ruta + ": " + e.getMessage());
        }
        return tiquetes;
    }
 
    /** Reescribe el archivo completo con la lista recibida (p. ej. después de atender un tiquete). */
    public boolean sobrescribir(List<Ticket> tiquetes) {
        List<String> lineas = new ArrayList<>();
        for (Ticket t : tiquetes) {
            lineas.add(ticketALinea(t));
        }
        try {
            Files.write(ruta, lineas, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar " + ruta + ": " + e.getMessage());
            return false;
        }
    }
    
}
