package py.una.pol.servertcp;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;



public class TCPServer {

	//variables compartidas
	boolean listening = true;
	List<TCPServerHilo> hilosClientes; //almacenar los hilos (no se utiliza en el ejemplo, se deja para que el alumno lo utilice)
	List<String> usuarios; //almacenar una lista de usuarios (no se utiliza, se deja para que el alumno lo utilice)

    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(6969);
        } catch (IOException e) {
            System.err.println("No se puede abrir el puerto: 6969.");
            System.exit(1);
        }
        System.out.println("Puerto abierto: 6969.");

        while (listening) {
        	
        	TCPServerHilo hilo = new TCPServerHilo(serverSocket.accept(), this);
            hilosClientes.add(hilo);
            hilo.start();
        }

        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException {
    	
    	TCPServer tms = new TCPServer();
    	
    	tms.hilosClientes = new ArrayList<TCPServerHilo>();
    	tms.usuarios = new ArrayList<String>();
    	
    	tms.ejecutar();
    	
    }
}
