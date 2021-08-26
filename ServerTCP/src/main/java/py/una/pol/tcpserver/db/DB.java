package py.una.pol.tcpserver.db;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import py.una.pol.tcpserver.TCPServer;
import py.una.pol.tcpserver.clases.Hospital;


public class DB {
    //Coneccion con la Base de Datos
    private static Connection connect(){
        try {
            Connection conn = null;
            //String directorio = System.getProperty("user.dir");
            TCPServer test = new TCPServer();
            String directorio = URLDecoder.decode(test.url.getFile(), "UTF-8");
            int pos = directorio.indexOf("/target/classes/", 0);
            directorio = directorio.substring(0, pos);
            directorio = "jdbc:sqlite:" + directorio + File.separator + "DB" + File.separator + "dataBase.db";
            System.out.println(directorio);
            try {
                conn = DriverManager.getConnection(directorio);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return conn;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static Hospital login(String user,String pass){
        String sql = "SELECT id_hospital, nombre, password, camas FROM hospital WHERE nombre = ?";
        
        try (Connection conn = DB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            pstmt.setString(1, user);
            ResultSet rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }else{                
                if(pass.equals(rs.getString("password"))){
                    String camas = rs.getString("camas");
                    Hospital hospital = new Hospital(user,camas);
                    return hospital;
                }        
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;        
    }
    
    public static boolean escrituraDB(String user,String camas){
        String sql = "UPDATE hospital SET camas = ? WHERE nombre = ?";
        
        try (Connection conn = DB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,camas);
            pstmt.setString(2,user);
            pstmt.executeUpdate();
            return true; 
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false; 
        }               
    }
    
    public static String informe(){
        String retorno = "";
        String sql = "SELECT * FROM hospital";
        
        try (Connection conn = DB.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) { 
            ResultSet rs = pstmt.executeQuery();
            if (rs == null) {
                return null;
            }else{   
                retorno = retorno + "<html>";
                while(rs.next()){
                    String camasString = rs.getString("camas");
                    retorno = retorno + "<br/>" + rs.getString("nombre") + ":<br/>";
                    for(int i=0;i<camasString.length();i++){
                        if(camasString.charAt(i)=='T'){
                            retorno = retorno + "Cama "+i + ": Ocupado<br/>";
                        }else{
                            retorno = retorno + "Cama "+i + ": Desocupado<br/>";
                        }
                    }
                    
                }
                retorno = retorno + "</html>";
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }   
        return retorno;
    }
}