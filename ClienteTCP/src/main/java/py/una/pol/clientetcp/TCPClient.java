package py.una.pol.clientetcp;

import java.io.*;
import java.net.*;
import static py.una.pol.clientetcp.Menu.MenuGUI;
import static py.una.pol.clientetcp.Menu.menuError;
import static py.una.pol.clientetcp.Menu.menuLogin;
import static py.una.pol.clientetcp.Menu.menuRoot;
import py.una.pol.clientetcp.clases.Mensaje;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        int TimeOutConexion = 7000; //milisegundos
        int TimeOutRecepcion = 5000; //milisegundos
        long ini = 0;
        long fin = 0;


        try {

            SocketAddress sockaddr = new InetSocketAddress("localhost", 6969);
            //SocketAddress sockaddr = new InetSocketAddress("200.10.229.161", 8080);
            kkSocket = new Socket();

           ini = System.currentTimeMillis();
            kkSocket.connect(sockaddr, TimeOutConexion);
            kkSocket.setSoTimeout(TimeOutRecepcion);
            
            // enviamos nosotros
            out = new PrintWriter(kkSocket.getOutputStream(), true);

            //viene del servidor
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        }catch (SocketTimeoutException e){
            fin = System.currentTimeMillis();
            menuError("Fallo de Timeout de conexion en " + TimeOutConexion + ", duracion " + (fin-ini));
            System.exit(1);
        }catch (UnknownHostException e) {
            menuError("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            menuError("Error de I/O en la conexion al host");
            System.exit(1);
        }
        
        

        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        Mensaje enviaraServidor = null;
        Mensaje recibidoServidor = null;
        
        ////COMUNICACION////////////////////////////////////////////////////////
        try{
                while ((fromServer = in.readLine()) != null) {
                    // recibimos el mensaje del servidor
                    recibidoServidor = new Mensaje(fromServer);

                    //procesamos lo recibido y preparamos nuestra respuesta
                    enviaraServidor = procesarMensaje(recibidoServidor);

                    //mandamos la respuesta al servidor
                    if(enviaraServidor!=null){
                        out.println(enviaraServidor.toJSON());
                        if(enviaraServidor.getCuerpo().equals("Desconexion")){
                            break;
                        }
                    }else{
                        System.out.println("ERROR");
                    }
                }
        }catch(SocketTimeoutException exTime){
            System.out.println("Tiempo de espera agotado para recepcion de datos del servidor " );
        }
        ////FIN DE COMUNICACION/////////////////////////////////////////////////
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }


    public static Mensaje procesarMensaje(Mensaje recibidoServidor){
    //procesa lo recibido del servidor y la entrada del menú para saber
    //que responder de vuelta al servidor
        Mensaje retorno = null;
        
        // solo si es 0 es operacion exitosa
        // si el estado es !=0 el cuerpo es el mensaje de error
        if (recibidoServidor.getEstado()!=0){
            menuError(recibidoServidor.getMensaje());
        }


        int[] opcion;
        int tipo = recibidoServidor.getTipo_operacion();
        
        // para los casos comunes de tipo
        if (tipo>=1 && tipo<=5){
            //tipo 1 es ver_estado
            if((tipo==1) && !(recibidoServidor.getCuerpo().equals(""))){
                // ###imprimir el cuerpo####
                Menu.estadoActual = recibidoServidor.getCuerpo();
                //Menu.imprimirEstado();
            }
            
            opcion = MenuGUI(); //retorna lo que eligio el cliente
            String cuerpo;
                
            //por defecto
                cuerpo = Integer.toString(opcion[1]);
            if (opcion[1] == -1){
                cuerpo = "Deslogueo";
            }
            if (opcion[1] == -2){
                cuerpo = "Desconexion";
            }
            
            retorno = new Mensaje(0,"ok",opcion[0],cuerpo);
        } else {
        //otros tipos a considerar

            switch (tipo) {
                case 6: // conexion TCP establecida
                    retorno = new Mensaje(0,"ok",6,"Conexion TCP establecida");
                    break;
                case 7: // inicio se sesion 
                    String login = menuLogin();
                    if(login.equals("root:root")){
                        return new Mensaje(0,"ok",69,login);
                    }
                    retorno = new Mensaje(0,"ok",7,login);
                    break;
                case 8:
                    retorno = new Mensaje(0,"ok",1,"0");
                    break;
                case 69:
                    retorno = new Mensaje(0,"ok",69,menuRoot(recibidoServidor.getCuerpo()));
                    break; 
                default:
                    retorno = new Mensaje(1,"Ultimo mensaje recibido del servidor no esta dentro del protocolo",6,"");
                    break;
            }
        }

        return retorno;    
    }
    
}
