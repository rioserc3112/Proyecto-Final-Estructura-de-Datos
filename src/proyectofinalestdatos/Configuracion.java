package proyectofinalestdatos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Configuración persistente del banco.
 * Lee y guarda prod.txt y conserva los métodos utilizados por la versión MAIN.
 */
public class Configuracion {

    public static final String ARCHIVO = "prod.txt";
    public static final int MIN_CAJAS = 3;

    private int cantCajas;
    private String nomBanco;
    private int min_edad = 10;
    private int max_edad = 120;
    private int ultTicket = 0;
    private int indexCajaPreferencial = 0;
    private int indexCajaRapida = 1;
    private Ticket.TipoTicket[] tiposCajas;

    public Configuracion() {
        if (!cargar()) {
            pedirDatos();
            asignarTipos();
            guardar();
        }
    }

    /** Constructor auxiliar para pruebas automatizadas. */
    Configuracion(String nomBanco, int cantCajas) {
        if (nomBanco == null || nomBanco.trim().isEmpty() || cantCajas < MIN_CAJAS) {
            throw new IllegalArgumentException("Configuración inválida");
        }
        this.nomBanco = nomBanco.trim();
        this.cantCajas = cantCajas;
        asignarTipos();
        guardar();
    }

    private boolean cargar() {
        Path ruta = Paths.get(ARCHIVO);
        if (!Files.exists(ruta)) {
            return false;
        }

        try {
            String banco = null;
            int cantidad = 0;
            Ticket.TipoTicket[] tipos = null;

            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                String[] partes = linea.split(":", 2);
                if (partes.length != 2) {
                    continue;
                }
                String clave = partes[0].trim();
                String valor = partes[1].trim();

                if (clave.equals("nom_banco")) {
                    banco = valor;
                } else if (clave.equals("cant_cajas")) {
                    cantidad = Integer.parseInt(valor);
                    if (cantidad < MIN_CAJAS) {
                        return false;
                    }
                    tipos = new Ticket.TipoTicket[cantidad];
                } else if (clave.startsWith("caja_") && tipos != null) {
                    int numero = Integer.parseInt(clave.substring("caja_".length()));
                    if (numero < 1 || numero > tipos.length) {
                        return false;
                    }
                    tipos[numero - 1] = Ticket.TipoTicket.valueOf(valor);
                }
            }

            if (banco == null || banco.isEmpty() || cantidad < MIN_CAJAS || tipos == null) {
                return false;
            }
            for (Ticket.TipoTicket tipo : tipos) {
                if (tipo == null) {
                    return false;
                }
            }

            // La lógica de MAIN requiere caja 1 preferencial, caja 2 rápida,
            // y las restantes para varios trámites.
            if (tipos[0] != Ticket.TipoTicket.P || tipos[1] != Ticket.TipoTicket.A) {
                return false;
            }
            for (int i = 2; i < tipos.length; i++) {
                if (tipos[i] != Ticket.TipoTicket.B) {
                    return false;
                }
            }

            this.nomBanco = banco;
            this.cantCajas = cantidad;
            this.tiposCajas = tipos;
            return true;
        } catch (IOException | RuntimeException ex) {
            System.err.println("No se pudo cargar prod.txt: " + ex.getMessage());
            return false;
        }
    }

    private void pedirDatos() {
        String nombre;
        do {
            nombre = JOptionPane.showInputDialog("Primera ejecución\nIngrese el nombre del banco");
            if (nombre == null) {
                cancelar();
                return;
            }
            nombre = nombre.trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
            }
        } while (nombre.isEmpty());
        this.nomBanco = nombre;

        while (true) {
            String entrada = JOptionPane.showInputDialog(
                    "Ingrese la cantidad de cajas del banco (mínimo " + MIN_CAJAS + ")");
            if (entrada == null) {
                cancelar();
                return;
            }
            try {
                int cantidad = Integer.parseInt(entrada.trim());
                if (cantidad < MIN_CAJAS) {
                    JOptionPane.showMessageDialog(null,
                            "Debe ingresar un número igual o mayor a " + MIN_CAJAS);
                    continue;
                }
                this.cantCajas = cantidad;
                return;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido");
            }
        }
    }

    private void cancelar() {
        JOptionPane.showMessageDialog(null, "Configuración cancelada. Cerrando el programa");
        System.exit(0);
    }

    private void asignarTipos() {
        tiposCajas = new Ticket.TipoTicket[cantCajas];
        for (int i = 0; i < cantCajas; i++) {
            if (i == 0) {
                tiposCajas[i] = Ticket.TipoTicket.P;
            } else if (i == 1) {
                tiposCajas[i] = Ticket.TipoTicket.A;
            } else {
                tiposCajas[i] = Ticket.TipoTicket.B;
            }
        }
    }

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        lineas.add("nom_banco:" + nomBanco);
        lineas.add("cant_cajas:" + cantCajas);
        for (int i = 0; i < cantCajas; i++) {
            lineas.add("caja_" + (i + 1) + ":" + tiposCajas[i]);
        }
        try {
            Files.write(Paths.get(ARCHIVO), lineas, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar " + ARCHIVO + ": " + ex.getMessage());
        }
    }

    public int getIndexCajaPreferencial() { return indexCajaPreferencial; }
    public void setIndexCajaPreferencial(int indexCajaPreferencial) { this.indexCajaPreferencial = indexCajaPreferencial; }
    public int getIndexCajaRapida() { return indexCajaRapida; }
    public void setIndexCajaRapida(int indexCajaRapida) { this.indexCajaRapida = indexCajaRapida; }
    public int actualizarContTiquet() { return ++ultTicket; }
    public int getMin_edad() { return min_edad; }
    public void setMin_edad(int min_edad) { this.min_edad = min_edad; }
    public int getMax_edad() { return max_edad; }
    public void setMax_edad(int max_edad) { this.max_edad = max_edad; }
    public int getCantCajas() { return cantCajas; }
    public String getNomBanco() { return nomBanco; }

    /** @param numeroCaja caja numerada desde 1 */
    public Ticket.TipoTicket getTipoCaja(int numeroCaja) {
        if (numeroCaja < 1 || numeroCaja > cantCajas) {
            throw new IllegalArgumentException("Número de caja fuera de rango");
        }
        return tiposCajas[numeroCaja - 1];
    }

    public Ticket.TipoTicket[] getTiposCajas() { return tiposCajas.clone(); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nomBanco + " - " + cantCajas + " cajas\n");
        for (int i = 0; i < cantCajas; i++) {
            sb.append("Caja ").append(i + 1).append(": ").append(tiposCajas[i]).append('\n');
        }
        return sb.toString();
    }
}
