package py.una.pol.tcpserver.clases;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author jg2kpy https://github.com/jg2kpy https://juniorgutierrez.com/
 */
public class Mensaje {
    int estado;
    String mensaje;
    int tipo_operacion;
    String cuerpo;

    public Mensaje(int estado, String mensaje, int tipo_operacion, String cuerpo) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.tipo_operacion = tipo_operacion;
        this.cuerpo = cuerpo;
    }

    public Mensaje(String strJSON) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(strJSON.trim());
        JSONObject jsonObject = (JSONObject) obj;
        
        this.estado = (int) (long) jsonObject.get("estado");
        this.mensaje = (String) jsonObject.get("mensaje");
        this.tipo_operacion = (int) (long) jsonObject.get("tipo_operacion");
        this.cuerpo = (String) jsonObject.get("cuerpo");
    }
    
    
    public String toJSON() {
    	
	JSONObject obj = new JSONObject();
        obj.put("estado", this.estado);
        obj.put("mensaje", this.mensaje);
        obj.put("tipo_operacion", this.tipo_operacion);
        obj.put("cuerpo", this.cuerpo);

        return obj.toJSONString();
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getTipo_operacion() {
        return tipo_operacion;
    }

    public void setTipo_operacion(int tipo_operacion) {
        this.tipo_operacion = tipo_operacion;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    
}
