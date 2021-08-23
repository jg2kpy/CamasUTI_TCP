package py.una.pol.servertcp;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import py.una.pol.servertcp.clases.Mensaje;

public class TCPServerHilo extends Thread {

    private Socket socket = null;

    TCPServer servidor;
    
    public TCPServerHilo(Socket socket, TCPServer servidor ) {
        super("TCPServerHilo");
        this.socket = socket;
        this.servidor = servidor;
    }

    public void run() {

        try {
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            
            String inputLine;
            
            Mensaje enviarCliente = new Mensaje(0,"ok",6,"Tratado de iniciar conexion TCP");//6 iniciar comunicacion
            out.println(enviarCliente.toJSON());
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                Mensaje recibidoServidor = new Mensaje(inputLine); 
                //enviarCliente = procesarMensaje(recibidoServidor);
                out.println(enviarCliente.toJSON());           
            }
            
            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(TCPServerHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
