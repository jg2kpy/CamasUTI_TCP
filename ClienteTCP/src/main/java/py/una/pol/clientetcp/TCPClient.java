package py.una.pol.clientetcp;

import java.io.*;
import java.net.*;

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
            System.err.println("Fallo de Timeout de conexion en " + TimeOutConexion);
            System.err.println("Duracion " + (fin-ini));
            System.exit(1);
        }catch (UnknownHostException e) {
            System.err.println("Host desconocido");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de I/O en la conexion al host");
            System.exit(1);
        }
        
        

        
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;
        
	try{
            while ((fromServer = in.readLine()) != null) {
		System.out.println("Servidor: " + fromServer);
		fromUser = stdIn.readLine();
		if (fromUser != null) {
                    System.out.println("Cliente: " + fromUser);
                        //escribimos al servidor
			out.println(fromUser);
                    }
		}
	}catch(SocketTimeoutException exTime){
		System.out.println("Tiempo de espera agotado para recepcion de datos del servidor " );
	}
		
        
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
}
