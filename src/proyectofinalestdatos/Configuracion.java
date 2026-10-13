package proyectofinalestdatos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class Configuracion {

    private int cantCajas;
    private String nomBanco;

    public Configuracion() {
        iniciarConfig();
    }

    private void iniciarConfig() {
        try (BufferedReader br = new BufferedReader(new FileReader("prod.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                //Se divide la linea en dos partes separado por ":"
                String[] partes = linea.split(":", 2);
                if (partes.length < 2) {
                    continue; // Si la linea no tiene 2 partes se ignora
                }
                String clave = partes[0].trim(); //Primera parte antes del :
                String valor = partes[1].trim(); //Segunda parte despues del :

                if (clave.equals("cant_cajas")) {
                    this.cantCajas = Integer.parseInt(valor);
                } else if (clave.equals("nom_banco")) {
                    this.nomBanco = valor;
                }
            }
            return;
        } catch (IOException e) {
            System.out.println("Error leyendo el archivo: " + e.getMessage());
        }
        
        //En caso de que no pueda leer el archivo:
        
        String Nombre = "";
        int cantidad = 0;
        
        do{
            try{
                Nombre = JOptionPane.showInputDialog("Ingrese el nombre del banco");
                cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad de cajas que tiene el banco (Minimo 3)"));

            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero valido mayor a 3");
            }
            
            if(cantidad < 3){
                JOptionPane.showMessageDialog(null, "Debe ingresar un numero mayor a 3");
                
            }
            
        }while(cantidad >= 3);
    }

    public int getCantCajas() { return cantCajas; }
    public String getNomBanco() { return nomBanco; }
}