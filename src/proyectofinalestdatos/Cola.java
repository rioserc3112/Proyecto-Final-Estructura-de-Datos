package proyectofinalestdatos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JOptionPane;

/** Cola enlazada de tiquetes. Conserva las operaciones usadas por MAIN y suma utilidades. */
public class Cola {
    private Nodo prim, ult;

    public boolean esVacia() { return prim == null; }

    /** Encola y devuelve el nodo creado, como requiere Caja.java de MAIN. */
    public Nodo agregar(Ticket t) {
        Nodo nuevo = new Nodo(t);
        if (esVacia()) {
            prim = ult = nuevo;
        } else {
            ult.setSig(nuevo);
            ult = nuevo;
        }
        return nuevo;
    }

    /** Retira el primer tiquete y registra la hora de atención. */
    public void atendido() {
        if (esVacia()) {
            JOptionPane.showMessageDialog(null, "Cola vacía");
            return;
        }
        prim.getDato().setHora_atencion(LocalDateTime.now());
        prim = prim.getSig();
        if (prim == null) ult = null;
    }

    /** Alias compatible con el nombre usado en la versión MENA. */
    public void desencolar() { atendido(); }

    @Override
    public String toString() {
        if (prim == null) return "No hay clientes en cola";
        StringBuilder resultado = new StringBuilder();
        Nodo actual = prim;
        while (actual != null) {
            if (!actual.getDato().isAtendido()) {
                resultado.append(actual.getDato().getNombre()).append('\n');
            }
            actual = actual.getSig();
        }
        return resultado.toString();
    }

    public boolean existe(int id) {
        Nodo actual = prim;
        while (actual != null) {
            if (actual.getDato().getId() == id) return true;
            actual = actual.getSig();
        }
        return false;
    }

    /** Elimina un tiquete por ID y actualiza correctamente el inicio y el final. */
    public void eliminar(int id) {
        Nodo anterior = null;
        Nodo actual = prim;
        while (actual != null) {
            if (actual.getDato().getId() == id) {
                if (anterior == null) prim = actual.getSig();
                else anterior.setSig(actual.getSig());
                if (actual == ult) ult = anterior;
                if (prim == null) ult = null;
                return;
            }
            anterior = actual;
            actual = actual.getSig();
        }
    }

    public void contarNom(String nombre) {
        int cantidad = 0;
        Nodo actual = prim;
        while (actual != null) {
            if (actual.getDato().getNombre().equalsIgnoreCase(nombre)) cantidad++;
            actual = actual.getSig();
        }
        JOptionPane.showMessageDialog(null, "Existen " + cantidad
                + " tiquete(s) de personas que se llaman " + nombre);
    }

    /** Devuelve el tiquete con el ID más alto, o null si la cola está vacía. */
    public Ticket getMayor() {
        Ticket mayor = null;
        Nodo actual = prim;
        while (actual != null) {
            if (mayor == null || actual.getDato().getId() > mayor.getId()) {
                mayor = actual.getDato();
            }
            actual = actual.getSig();
        }
        return mayor;
    }

    /** Ordena la cola por ID descendente. */
    public void ordenarMayores() {
        List<Ticket> tiquetes = new ArrayList<>();
        Nodo actual = prim;
        while (actual != null) {
            tiquetes.add(actual.getDato());
            actual = actual.getSig();
        }
        tiquetes.sort(Comparator.comparingInt(Ticket::getId).reversed());
        prim = ult = null;
        for (Ticket tiquete : tiquetes) agregar(tiquete);
    }
}
