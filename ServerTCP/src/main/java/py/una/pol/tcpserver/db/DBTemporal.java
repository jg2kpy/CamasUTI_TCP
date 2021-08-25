package py.una.pol.tcpserver.db;

import py.una.pol.tcpserver.clases.Hospital;



public class DBTemporal {
    
    public static Hospital[] hospitalX = {new Hospital("HospitalX1",10),new Hospital("HospitalX2",20),new Hospital("HospitalX3",30),new Hospital("HospitalX4",40)};
    
    public static int loginViejo(String user){
        for(int i=0;i<4;i++){
             if(hospitalX[i].getNombre().equals(user)){
                 return i;
             }   
        }
        return -1;
    }      
}
