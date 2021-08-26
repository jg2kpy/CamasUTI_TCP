package py.una.pol.tcpserver;

import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class TCPServer {
    
    static int port = 6969;
    
    public URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
    
    //variables compartidas
    boolean listening = true;
    public List<TCPServerHilo> hilosClientes; //almacenar los hilos (no se utiliza en el ejemplo, se deja para que el alumno lo utilice)
    List<String> usuarios; //almacenar una lista de usuarios (no se utiliza, se deja para que el alumno lo utilice)

    public void ejecutar() throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(6969);
        } catch (IOException e) {
            loger("","",0,"No se puede abrir el puerto: " + port);
            System.exit(1);
        }
        System.out.println("Puerto abierto: " + port);

        while (listening) {
        	
            TCPServerHilo hilo = new TCPServerHilo(serverSocket.accept(), this);
            hilosClientes.add(hilo);
            hilo.start();
        }

        serverSocket.close();
    }
    
    public void detener() throws IOException{
        listening = false;
        try{
            throw new Exception();            
        }catch(Exception ex){
            for(int i=0;i<hilosClientes.size();i++){
                hilosClientes.get(i).detenerHilo();
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("Desea renovar el servicio? [Y/n]");
            String respuesta = sc.nextLine();  
            if(respuesta.equals("Y")){
                listening = true;                
            }else{
                System.exit(0);
            } 
         }
         ejecutar();      
    }
    
    public void killProcess(){
        System.exit(0);
    }
    
    public void apagar(){
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec("shutdown -h");
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        }
         System.exit(0);
    }
    
    
    public static void loger(String ip, String ip2,int tipo_operacion,String error){
        FileWriter fw = null;
        try {
            if(!error.equals("")){
                error = ", "+ error;
            }            
            String log = LocalDateTime.now()+", origen("+ip+"), destino("+ip2+"), tipo_operacion:"+tipo_operacion +error;           
            System.out.println(log);
            fw = new FileWriter("log.txt",true); //the true will append the new data
            fw.write(log+"\n");//appends the string to the file
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPServerHilo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(TCPServerHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
    	
    	if(args.length >0){
            port = Integer.parseInt(args[0]);
        }
        
        TCPServer tms = new TCPServer();
    	
    	tms.hilosClientes = new ArrayList<TCPServerHilo>();
    	tms.usuarios = new ArrayList<String>();
    	
    	tms.ejecutar();
    	
    }
    
}
