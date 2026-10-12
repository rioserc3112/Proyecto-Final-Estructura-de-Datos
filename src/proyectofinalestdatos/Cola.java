/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectofinalestdatos;

import javax.swing.JOptionPane;

/**
 *
 * @author emma
 */
public class Cola {
    private Nodo prim, ult;
    
    public boolean esVacia(){
        return prim==null;
    }
    
    //encolar
    public void agregar(Ticket t){
        Nodo nuevo = new Nodo(t);
        
        if(esVacia()){
            prim = ult = nuevo;
        }else{
            ult.setSig(nuevo);
            ult=nuevo;
        }
    }
    
    //desencolar
    public void desencolar(){
        if(!esVacia()){
            prim=prim.getSig();
            if(prim==null){
                ult=null;
            }
        }else{
            JOptionPane.showMessageDialog(null, "Cola vacia");
        }
    }
    
    //toString
    @Override
    public String toString() {
        String r = "Cola:\n";
        Nodo aux = prim;
        while (aux != null) {
            r += aux + "\n";
            aux = aux.getSig();
        }
        return r;
    }
    
    
    //Eliminar
    public void eliminar(int id) {//INICIAMOS AQUI LA PROX
        if (!esVacia()) {
            if (prim.getDato().getId() == id) {
                this.desencolar();
            }else{
                Nodo aux = prim;
                while (aux.getSig() != ult) {
                    if (aux.getSig().getDato().getId() == id) {
                        //Brincarse al siguiente
                        aux.setSig(aux.getSig().getSig());
                        break;
                    }
                    aux = aux.getSig();
                }
                if(aux.getSig()==ult){
                    aux.setSig(null);
                    ult=aux;
                }
            }
        }
    }
   
    
    //Existe
    public boolean existe(int id) {
        if (!esVacia()) {
            if (prim.getDato().getId() == id ||
                    ult.getDato().getId() == id) {
                return true;
            } else {
                Nodo aux = prim;
                while (aux != null) {
                    if (aux.getDato().getId() == id) {
                        return true;
                    }
                    aux = aux.getSig();
                }
            }
        }
        return false;
    }
    
    
    //contarNombre
    public void contarNom(String nom) {
        int cont = 0;
        if (!esVacia()) {
            Nodo aux = prim;
            while (aux != null) {
                if (aux.getDato().getNombre().equals(nom)) {
                    cont++;
                }
                aux = aux.getSig();
            }
        }
        JOptionPane.showMessageDialog(null, "Existen " + cont
                + " de personas que se llaman " + nom);
    }
    
    
    public Ticket getMayor(){
        //Recorren buscan el id mayor y lo devuelven 
        
        return null;
    }
    
    public void ordenarMayores(){
        Cola aux=new Cola();
        
        while(!esVacia()){
            aux.agregar(getMayor());
            eliminar(getMayor().getId());
        }
        
        prim=aux.prim;
        ult=aux.ult;
    }
}

