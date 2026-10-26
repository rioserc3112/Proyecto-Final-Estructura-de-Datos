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
 * Módulo 0: configuración inicial del banco.
 *
 * Primera ejecución (prod.txt no existe o está dañado): pide nombre del banco
 * y cantidad de cajas (mínimo 3), asigna los tipos y escribe prod.txt.
 * Ejecuciones siguientes: carga la configuración desde prod.txt.
 *
 * Distribución de cajas: caja 1 = P (preferencial), caja 2 = A (rápida,
 * un solo trámite) y el resto = B (dos o más trámites).
 *
 * Formato de prod.txt:
 *   nom_banco:Banco Nacional
 *   cant_cajas:5
 *   caja_1:P
 *   caja_2:A
 *   caja_3:B
 *   ...
 */
public class Configuracion {

    public static final String ARCHIVO = "prod.txt";
    public static final int MIN_CAJAS = 3;

    private String nomBanco;
    private int cantCajas;
    private Ticket.TipoTiquete[] tiposCajas; // posición 0 = caja 1

    public Configuracion() {
        if (!cargar()) {
            pedirDatos();
            asignarTipos();
            guardar();
        }
    }

    /** Constructor para pruebas: crea y guarda sin pedir datos por pantalla. */
    Configuracion(String nomBanco, int cantCajas) {
        if (nomBanco == null || nomBanco.trim().isEmpty() || cantCajas < MIN_CAJAS) {
            throw new IllegalArgumentException("Configuración inválida");
        }
        this.nomBanco = nomBanco.trim();
        this.cantCajas = cantCajas;
        asignarTipos();
        guardar();
    }

    // ---------------------------------------------------------------- carga

    /** @return true si prod.txt existe y su contenido es válido. */
    private boolean cargar() {
        Path ruta = Paths.get(ARCHIVO);
        if (!Files.exists(ruta)) {
            return false; // primera ejecución
        }
        try {
            String banco = null;
            int cant = 0;
            Ticket.TipoTiquete[] tipos = null;

            for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
                String[] partes = linea.split(":", 2);
                if (partes.length < 2) {
                    continue;
                }
                String clave = partes[0].trim();
                String valor = partes[1].trim();

                if (clave.equals("nom_banco")) {
                    banco = valor;
                } else if (clave.equals("cant_cajas")) {
                    cant = Integer.parseInt(valor);
                    tipos = new Ticket.TipoTiquete[cant];
                } else if (clave.startsWith("caja_") && tipos != null) {
                    int num = Integer.parseInt(clave.substring(5));
                    tipos[num - 1] = Ticket.TipoTiquete.valueOf(valor);
                }
            }

            // Validar que el archivo esté completo
            if (banco == null || banco.isEmpty() || cant < MIN_CAJAS || tipos == null) {
                return false;
            }
            for (Ticket.TipoTiquete t : tipos) {
                if (t == null) {
                    return false;
                }
            }
            this.nomBanco = banco;
            this.cantCajas = cant;
            this.tiposCajas = tipos;
            return true;

        } catch (IOException | RuntimeException e) {
            // Archivo ilegible o con datos inválidos: se vuelve a pedir la configuración
            System.out.println("prod.txt inválido, se pedirá la configuración: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------ primera ejecución

    private void pedirDatos() {
        // Nombre del banco
        String nombre;
        do {
            nombre = JOptionPane.showInputDialog("Primera ejecución\nIngrese el nombre del banco");
            if (nombre == null) {
                cancelar();
            }
            nombre = nombre.trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre no puede estar vacío");
            }
        } while (nombre.isEmpty());
        this.nomBanco = nombre;

        // Cantidad de cajas
        int cantidad = 0;
        boolean valido = false;
        do {
            String entrada = JOptionPane.showInputDialog(
                    "Ingrese la cantidad de cajas del banco (mínimo " + MIN_CAJAS + ")");
            if (entrada == null) {
                cancelar();
            }
            try {
                cantidad = Integer.parseInt(entrada.trim());
                if (cantidad < MIN_CAJAS) {
                    JOptionPane.showMessageDialog(null,
                            "Debe ingresar un número igual o mayor a " + MIN_CAJAS);
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido");
            }
        } while (!valido);
        this.cantCajas = cantidad;
    }

    private void cancelar() {
        JOptionPane.showMessageDialog(null, "Configuración cancelada. Cerrando el programa");
        System.exit(0);
    }

    /** Caja 1 = P, caja 2 = A, el resto = B. */
    private void asignarTipos() {
        tiposCajas = new Ticket.TipoTiquete[cantCajas];
        for (int i = 0; i < cantCajas; i++) {
            if (i == 0) {
                tiposCajas[i] = Ticket.TipoTiquete.P;
            } else if (i == 1) {
                tiposCajas[i] = Ticket.TipoTiquete.A;
            } else {
                tiposCajas[i] = Ticket.TipoTiquete.B;
            }
        }
    }

    // -------------------------------------------------------------- guardado

    private void guardar() {
        List<String> lineas = new ArrayList<>();
        lineas.add("nom_banco:" + nomBanco);
        lineas.add("cant_cajas:" + cantCajas);
        for (int i = 0; i < cantCajas; i++) {
            lineas.add("caja_" + (i + 1) + ":" + tiposCajas[i]);
        }
        try {
            Files.write(Paths.get(ARCHIVO), lineas, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar " + ARCHIVO + ": " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------- getters

    public String getNomBanco() {
        return nomBanco;
    }

    public int getCantCajas() {
        return cantCajas;
    }

    /** @param numeroCaja de 1 a cantCajas */
    public Ticket.TipoTiquete getTipoCaja(int numeroCaja) {
        return tiposCajas[numeroCaja - 1];
    }

    public Ticket.TipoTiquete[] getTiposCajas() {
        return tiposCajas.clone();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(nomBanco + " - " + cantCajas + " cajas\n");
        for (int i = 0; i < cantCajas; i++) {
            sb.append("Caja ").append(i + 1).append(": ").append(tiposCajas[i]).append("\n");
        }
        return sb.toString();
    }
}