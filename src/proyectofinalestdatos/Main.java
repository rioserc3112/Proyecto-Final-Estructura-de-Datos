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

        Configuracion config = new Configuracion();
        
        MenuPrincipal();

    private static void MenuPrincipal() {

        String res;

        do {
            res = JOptionPane.showInputDialog(" ******* MENU PRINCIPAL *******\n"
                    + "1) Opcion 1\n2) Opcion 2\n3) Opcion 3\n0) SALIR\n\n"
                    + "Ingrese una opcion (0-3)");
            switch (res) {
                case "1":
                    JOptionPane.showMessageDialog(null,
                            "En desarollo");
                    break;
                case "2":
                    JOptionPane.showMessageDialog(null,
                            "En desarollo");
                    break;
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
