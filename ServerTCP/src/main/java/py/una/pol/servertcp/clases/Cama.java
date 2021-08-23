package py.una.pol.servertcp.clases;

/**
 *
 * @author jg2kpy https://github.com/jg2kpy https://juniorgutierrez.com/
 */
public class Cama {
    private boolean ocupado = false;
    
    public Cama() {
    }

    public Cama(boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    public boolean getOcupado(){
        return ocupado;
    }
    
    public boolean ocupar(){//Retrun false, cama actualmente ocupado
        if(ocupado==true){
            return false;
        }
        return ocupado = true;
    }
    
    public boolean desOcupar(){//Retrun false, cama ya desocupada
        if(ocupado==false){
            return false;
        }
        ocupado = false;
        return true;
    }
}
