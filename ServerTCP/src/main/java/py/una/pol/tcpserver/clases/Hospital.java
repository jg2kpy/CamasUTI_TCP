package py.una.pol.tcpserver.clases;

import java.util.ArrayList;

/**
 *
 * @author jg2kpy https://github.com/jg2kpy https://juniorgutierrez.com/
 */
public class Hospital {
    private String nombre;
    private ArrayList<Cama> camas;

    public Hospital(String nombre,int cantidadTotalCamas) {
        this.nombre = nombre;
        this.camas = new ArrayList<>(cantidadTotalCamas);
        for(int i=0;i<cantidadTotalCamas;i++){
            this.camas.add(new Cama());
        }
    }

    public Hospital(String nombre, String camas) {
        int n = camas.length();
        this.nombre = nombre;
        this.camas = new ArrayList<>(n);        
        for(int i=0;i<n;i++){
            if(camas.charAt(i)=='T'){
                this.camas.add(new Cama(true));
            }else{
                this.camas.add(new Cama(false));
            }            
        }
    }
    
    public String verEstado(){        
        String retorno = new String();
        for(int i=0;i<camas.size();i++){
            if(camas.get(i).getOcupado()){
                retorno = retorno + "T";
            }else{
                retorno = retorno + "F";
            }
        }
        return retorno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int crearCama(int n){
        for(int i=0;i<n;i++){
            camas.add(new Cama());
        }        
        return camas.size();
    }
    
    public int eliminarCama(int n){
        if(camas.size() < n){
            return -1;
        }
        for(int i=0;i<n;i++){
            camas.remove(camas.size() - 1);
        }   
        return camas.size();
    }
    
    public boolean ocuparCama(int index){
        return camas.get(index).ocupar();
    }
    
    public boolean desOcuparCama(int index){
        return camas.get(index).desOcupar();
    }
}
