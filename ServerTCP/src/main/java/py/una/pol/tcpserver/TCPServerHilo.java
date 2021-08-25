package py.una.pol.tcpserver;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import static py.una.pol.tcpserver.TCPServer.loger;
import py.una.pol.tcpserver.clases.Hospital;
import py.una.pol.tcpserver.clases.Mensaje;
import static py.una.pol.tcpserver.db.DB.escrituraDB;
import static py.una.pol.tcpserver.db.DB.informe;
import static py.una.pol.tcpserver.db.DB.login;

public class TCPServerHilo extends Thread {

    private Socket socket = null;

    TCPServer servidor;
    
    public static Hospital seccion;
    
    public TCPServerHilo(Socket socket, TCPServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }
    
    PrintWriter out;
    
    public void detenerHilo() {
        out.close();
    }
    
    String MyIp;
    String ipClient;

    public void run() {

        try {
            
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            Mensaje enviarCliente = new Mensaje(0,"ok",6,"Tratado de iniciar conexion TCP");
            out.println(enviarCliente.toJSON());
            String inputLine;

            MyIp = socket.getLocalSocketAddress().toString().substring(1);
            ipClient = socket.getRemoteSocketAddress().toString().substring(1);
            
            
            while ((inputLine = in.readLine()) != null) {
                Mensaje recibidoCliente;    
                recibidoCliente = new Mensaje(inputLine);
                loger(MyIp, ipClient,recibidoCliente.getTipo_operacion(),"");
                enviarCliente = procesarMensaje(recibidoCliente);
                if(enviarCliente == null){
                    break;
                }    
                loger(ipClient,MyIp,enviarCliente.getTipo_operacion(),"");
                out.println(enviarCliente.toJSON());
            }
            
            out.close();
            in.close();
            socket.close();
            //System.out.println("Finalizando Hilo");
            loger(ipClient,MyIp,6,"");

        } catch (IOException e) {
            loger(ipClient,MyIp,6,"");
        } catch (ParseException ex) {
            Logger.getLogger(TCPServerHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Mensaje procesarMensaje(Mensaje recibidoCliente){
        Mensaje retorno = null;
        int tipo = recibidoCliente.getTipo_operacion();        
        
        if(recibidoCliente.getEstado()!=0){
            loger(ipClient,MyIp,recibidoCliente.getTipo_operacion(),recibidoCliente.getMensaje());
        }  
        
        if((1<=tipo && tipo<=5)){
            retorno = procesarOperacion(tipo,Integer.parseInt(recibidoCliente.getCuerpo()));
        }else{
            switch (tipo) {//CASO ESPECIALES
                case 6:
                    seccion = null;
                    if(recibidoCliente.getCuerpo().equals("Desconexion")){
                        return null;
                    }
                    retorno = new Mensaje(0,"ok",7,"Tratando de iniciar seccion");
                    break;
                case 7:
                    int pos = recibidoCliente.getCuerpo().indexOf(':');
                    String user = recibidoCliente.getCuerpo().substring(0, pos);
                    String pass = recibidoCliente.getCuerpo().substring(pos+1);
                    seccion = login(user,pass);
                    if(seccion!=null){
                        retorno = new Mensaje(0,"ok",8,"Session logueada");
                    }else{
                        retorno = new Mensaje(1,"Seccion incorrecta",7,"");
                    }  
                    break;
                case 69:
                switch (recibidoCliente.getCuerpo()) {
                    case "Detener":
                        try {
                            servidor.detener();
                        } catch (IOException ex) {
                            Logger.getLogger(TCPServerHilo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Matar":
                        servidor.killProcess();
                        break;
                    case "Apagar":
                        servidor.apagar();
                        break;
                    default:
                        break;
                }
                    retorno = new Mensaje(0,"ok",69,informe());
                    break;

                default:
                    retorno = new Mensaje(1,"Ultimo mensaje recibido del cliente no esta dentro del protocolo",6,"");
                    break;
            }        
        }
        return retorno;
    }
    
    public static Mensaje procesarOperacion(int tipo,int cuerpo){
        Hospital backup = backUp(seccion);
        boolean flag = false;
        int camas = 0;
        String nuevoCuerpo = "Se completo la operacion con exito";
        
        switch (tipo) {
            case 1:
                nuevoCuerpo = seccion.verEstado();
                flag = nuevoCuerpo != null;
                break;
            case 2:
                camas = seccion.crearCama(cuerpo);
                flag = camas >= 0;
                nuevoCuerpo = nuevoCuerpo + ",se agregaron "+cuerpo+" nuevas camas, total: "+camas;
                break;
            case 3:
                camas = seccion.eliminarCama((cuerpo));
                flag = camas >= 0;
                nuevoCuerpo = nuevoCuerpo + ",se eliminaron "+cuerpo+" camas, total: "+camas;
                break;
            case 4:
                flag = seccion.ocuparCama(cuerpo);
                break;
            case 5:
                flag = seccion.desOcuparCama(cuerpo);
                break;
       }
                
       if(flag){
           if(escrituraDB(seccion.getNombre(),seccion.verEstado())){
               return new Mensaje(0,"ok",tipo,nuevoCuerpo);
           }    
       }
       seccion = backup;
       return new Mensaje(1,"No se logro realizar la operacion",tipo,"");
    }
    
    public static Hospital backUp(Hospital seccion){
        return new Hospital(seccion.getNombre(),seccion.verEstado());
    }
}