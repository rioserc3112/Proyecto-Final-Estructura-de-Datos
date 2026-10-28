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
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Emmanuel
 */

/* Login del sistema. Sin login válido no se accede a ningún módulo.
 * Los usuarios se guardan en usuarios.txt con el formato:
 * usuario:clave:rol
 * Si el archivo no existe se crea con dos usuarios por defecto.
 */

public class Login {
    public static final String ARCHIVO = "usuarios.txt";
    public static final int MAX_INTENTOS = 3;
 
    private String usuarioActual;
    private String rolActual;
 
    public Login() {
        crearUsuariosPorDefecto();
    }
 
    /** Crea usuarios.txt con 2 usuarios si todavía no existe. */
    private void crearUsuariosPorDefecto() {
        Path ruta = Paths.get(ARCHIVO);
        if (Files.exists(ruta)) {
            return;
        }
        List<String> usuarios = Arrays.asList(
                "admin:admin123:ADMIN",
                "cajero:cajero123:CAJERO");
        try {
            Files.write(ruta, usuarios, StandardCharsets.UTF_8);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo crear " + ARCHIVO + ": " + e.getMessage());
        }
    }
 
    /**
     * Pide usuario y contraseña (máximo 3 intentos). 
     * // Usuario: admin Passw:admin123 Rol admin 
     * // Usuraio: cajero Passw:cajero123 Rol cajero  
     * @return true si el acceso fue concedido, false si canceló o agotó los intentos.
     */
    public boolean autenticar() {
        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            JTextField campoUsuario = new JTextField();
            JPasswordField campoClave = new JPasswordField();
            Object[] formulario = {"Usuario:", campoUsuario, "Contraseña:", campoClave};
 
            int opcion = JOptionPane.showConfirmDialog(null, formulario,
                    "Inicio de sesión", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (opcion != JOptionPane.OK_OPTION) {
                return false; // canceló
            }
 
            String usuario = campoUsuario.getText().trim();
            String clave = new String(campoClave.getPassword());
 
            if (validar(usuario, clave)) {
                return true;
            }
            JOptionPane.showMessageDialog(null,
                    "Usuario o contraseña incorrectos. Intento " + intento + " de " + MAX_INTENTOS);
        }
        JOptionPane.showMessageDialog(null, "Demasiados intentos fallidos. Acceso denegado");
        return false;
    }
 
    /** Compara contra usuarios.txt; si coincide guarda el usuario y su rol. */
    private boolean validar(String usuario, String clave) {
        if (usuario.isEmpty() || clave.isEmpty()) {
            return false;
        }
        try {
            for (String linea : Files.readAllLines(Paths.get(ARCHIVO), StandardCharsets.UTF_8)) {
                String[] partes = linea.split(":", 3);
                if (partes.length < 3) {
                    continue;
                }
                if (partes[0].trim().equals(usuario) && partes[1].equals(clave)) {
                    usuarioActual = partes[0].trim();
                    rolActual = partes[2].trim();
                    return true;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer " + ARCHIVO + ": " + e.getMessage());
        }
        return false;
    }
 
    public String getUsuarioActual() {
        return usuarioActual;
    }
 
    public String getRolActual() {
        return rolActual;
    }
    
    
}
