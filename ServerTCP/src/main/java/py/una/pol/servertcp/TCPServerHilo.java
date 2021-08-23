package py.una.pol.servertcp;

import java.net.*;
import java.io.*;

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
            
            out.println("Bienvenido!");
            String inputLine, outputLine;
            
            
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Mensaje recibido: " + inputLine);
                outputLine = stdIn.readLine();
                if (outputLine != null) {
                    System.out.println("Server: " + outputLine);
                    //escribimos al cliente
                    out.println(outputLine);
		}
            }
            
            out.close();
            in.close();
            socket.close();
            System.out.println("Finalizando Hilo");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
