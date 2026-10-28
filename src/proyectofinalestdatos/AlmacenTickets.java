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

/** Persistencia de tiquetes compatible con el modelo MAIN (varios trámites por tiquete). */
public class AlmacenTickets {

    public static final String ARCHIVO_DEFECTO = "tiquetes.txt";
    private static final String SEP = "|";
    private static final String SIN_HORA = "-";
    private final Path ruta;

    public AlmacenTickets() { this(ARCHIVO_DEFECTO); }

    public AlmacenTickets(String nombreArchivo) { this.ruta = Paths.get(nombreArchivo); }

    public static String ticketALinea(Ticket t) {
        String atencion = t.getHora_atencion() == null ? SIN_HORA : t.getHora_atencion().toString();
        String nombre = t.getNombre().replace(SEP, " ");
        StringBuilder tramites = new StringBuilder();
        for (Ticket.Tramite tramite : t.getTramite()) {
            if (tramites.length() > 0) tramites.append(',');
            tramites.append(tramite.name());
        }
        return t.getId() + SEP + nombre + SEP + t.getEdad() + SEP
                + t.getHora_creacion() + SEP + atencion + SEP
                + tramites + SEP + t.getTipo().name();
    }

    public static Ticket lineaATicket(String linea) {
        String[] p = linea.split("\\|", -1);
        if (p.length != 7) return null;
        try {
            ArrayList<Ticket.Tramite> tramites = new ArrayList<>();
            for (String item : p[5].split(",")) {
                String valor = item.trim();
                if (!valor.isEmpty()) tramites.add(Ticket.Tramite.valueOf(valor));
            }
            if (tramites.isEmpty()) return null;
            Ticket t = new Ticket(Integer.parseInt(p[0].trim()), p[1],
                    Integer.parseInt(p[2].trim()), tramites,
                    Ticket.TipoTicket.valueOf(p[6].trim()));
            t.setHora_creacion(LocalDateTime.parse(p[3].trim()));
            if (!p[4].trim().equals(SIN_HORA)) {
                t.setHora_atencion(LocalDateTime.parse(p[4].trim()));
            }
            return t;
        } catch (RuntimeException ex) {
            return null;
        }
    }

    public boolean agregarLinea(Ticket t) {
        try {
            Files.write(ruta, Collections.singletonList(ticketALinea(t)), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar el tiquete: " + ex.getMessage());
            return false;
        }
    }

    public List<Ticket> leerTodos() {
        List<Ticket> resultado = new ArrayList<>();
        if (!Files.exists(ruta)) return resultado;
        try {
            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                if (!linea.trim().isEmpty()) {
                    Ticket ticket = lineaATicket(linea);
                    if (ticket != null) resultado.add(ticket);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo leer " + ruta + ": " + ex.getMessage());
        }
        return resultado;
    }

    public boolean sobrescribir(List<Ticket> tiquetes) {
        List<String> lineas = new ArrayList<>();
        for (Ticket ticket : tiquetes) lineas.add(ticketALinea(ticket));
        try {
            Files.write(ruta, lineas, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar " + ruta + ": " + ex.getMessage());
            return false;
        }
    }
}
