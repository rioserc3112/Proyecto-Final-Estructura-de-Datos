/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectofinalestdatos;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author emma
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //Sin login valido no se puede acceder 
        
        Login login = new Login();
        if(!login.autenticar()){
            JOptionPane.showMessageDialog(null, "Acceso denegado. Cerrando el programa");
            return;
        }
            
        
        Configuracion config = new Configuracion();
        
        MenuPrincipal(config,login);
    }
    
    private static void MenuPrincipal(Configuracion config, Login login) {

        String res;

        do {
            res = JOptionPane.showInputDialog(" ******* MENU PRINCIPAL *******\n"
                    + "Usuario:" +login.getUsuarioActual() + " (" + login.getRolActual() + ")\n\n"
                    + "1) Creacion de tiquetes\n2) Atencion de tiquetes\n3) Reportes\n0) SALIR\n\n"
                    + "Ingrese una opcion (0-3)");
            if (res == null){ //Elusuraio salio o cancelo 
                res = "0";
            }
            
            switch (res) {
                case "1":
                case "2":
                case "3":
                    JOptionPane.showMessageDialog(null,
                            "En desarollo");
                    break;
                case "0":
                    JOptionPane.showMessageDialog(null,
                            "Cerrando el programa");
                    break;
                default:
                    JOptionPane.showMessageDialog(null,
                            "Error opcion no valida");
            }
        } while (!res.equals("0"));

    }

}
