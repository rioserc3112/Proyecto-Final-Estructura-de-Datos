
package proyectofinalestdatos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class RegistroAtenciones {

    private final Path archivo = Paths.get("atenciones.csv");

    public static class Atencion {
        public final int caja;
        public final int ticket;
        public final long milisegundos;

        public Atencion(int caja, int ticket, long milisegundos) {
            this.caja = caja;
            this.ticket = ticket;
            this.milisegundos = milisegundos;
        }
    }

    public boolean guardar(int caja, Ticket ticket, LocalDateTime fin) {

        LocalDateTime inicio = ticket.getHora_atencion();

        if (inicio == null) {
            return false;
        }

        long tiempo = Math.max(0,
                Duration.between(inicio, fin).toMillis());

        String linea = caja + "," + ticket.getId() + "," + tiempo;

        try {
            Files.write(archivo,
                    java.util.Collections.singletonList(linea),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            return true;

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al guardar: " + ex.getMessage());
            return false;
        }
    }

    public List<Atencion> leer() {

        List<Atencion> datos = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return datos;
        }

        try {
            for (String linea : Files.readAllLines(
                    archivo, StandardCharsets.UTF_8)) {

                String[] partes = linea.split(",");

                if (partes.length != 3) {
                    continue;
                }

                try {
                    int caja = Integer.parseInt(partes[0].trim());
                    int ticket = Integer.parseInt(partes[1].trim());
                    long tiempo = Long.parseLong(partes[2].trim());

                    if (caja > 0 && tiempo >= 0) {
                        datos.add(new Atencion(caja, ticket, tiempo));
                    }

                } catch (NumberFormatException ex) {
                  
                }
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al leer: " + ex.getMessage());
        }

        return datos;
    }
}
