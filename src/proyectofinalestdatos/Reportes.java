
package proyectofinalestdatos;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;

public class Reportes {

    private RegistroAtenciones registro;

    public Reportes(RegistroAtenciones registro) {
        this.registro = registro;
    }

    public void mostrarMenu() {

        String opcion;

        do {
            opcion = JOptionPane.showInputDialog(null,
                    "******* REPORTES *******\n"
                    + "1. Caja con mas clientes atendidos\n"
                    + "2. Total de clientes atendidos\n"
                    + "3. Caja con mejor tiempo promedio\n"
                    + "4. Tiempo promedio general\n"
                    + "0. Volver al menu principal\n\n"
                    + "Seleccione una opcion:");

            if (opcion == null || opcion.trim().equals("0")) {
                return;
            }

            List<RegistroAtenciones.Atencion> datos = registro.leer();

            if (datos.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No hay clientes atendidos registrados.");
                continue;
            }

            Map<Integer, long[]> porCaja = new TreeMap<>();
            long suma = 0;

            for (RegistroAtenciones.Atencion a : datos) {

                long[] valores = porCaja.computeIfAbsent(
                        a.caja, k -> new long[2]);

                valores[0]++;
                valores[1] += a.milisegundos;
                suma += a.milisegundos;
            }

            String resultado = "";

            switch (opcion.trim()) {

                case "1":
                    long mayor = 0;

                    for (long[] valores : porCaja.values()) {
                        if (valores[0] > mayor) {
                            mayor = valores[0];
                        }
                    }

                    String cajasMayor = "";

                    for (Map.Entry<Integer, long[]> e : porCaja.entrySet()) {
                        if (e.getValue()[0] == mayor) {
                            if (!cajasMayor.isEmpty()) {
                                cajasMayor += ", ";
                            }
                            cajasMayor += e.getKey();
                        }
                    }

                    resultado = "Caja(s) con mas clientes: "
                            + cajasMayor
                            + "\nClientes atendidos: " + mayor;
                    break;

                case "2":
                    resultado = "Total de clientes atendidos: "
                            + datos.size();
                    break;

                case "3":
                    double mejor = Double.MAX_VALUE;
                    String cajasMejor = "";

                    for (Map.Entry<Integer, long[]> e : porCaja.entrySet()) {

                        long[] valores = e.getValue();

                        double promedio = (double) valores[1]
                                / valores[0];

                        if (promedio < mejor) {
                            mejor = promedio;
                            cajasMejor = "" + e.getKey();
                        } else if (promedio == mejor) {
                            cajasMejor += ", " + e.getKey();
                        }
                    }

                    resultado = "Caja(s) con mejor promedio: "
                            + cajasMejor
                            + "\nTiempo promedio: " + formato(mejor);
                    break;

                case "4":
                    double promedioGeneral = (double) suma
                            / datos.size();

                    resultado = "Tiempo promedio general: "
                            + formato(promedioGeneral)
                            + "\nClientes atendidos: " + datos.size();
                    break;

                default:
                    JOptionPane.showMessageDialog(null,
                            "Opcion incorrecta");
                    continue;
            }

            JOptionPane.showMessageDialog(null,
                    resultado, "Reportes",
                    JOptionPane.INFORMATION_MESSAGE);

        } while (true);
    }

    private String formato(double tiempo) {

        double segundos = tiempo / 1000.0;
        double minutos = tiempo / 60000.0;

        return String.format(java.util.Locale.US,
                "%.2f segundos (%.2f minutos)",
                segundos, minutos);
    }
}
