package py.una.pol.clientetcp;

import java.util.Scanner;

/**
 *
 * @author jg2kpy https://github.com/jg2kpy https://juniorgutierrez.com/
 */
public class Menu {
    
    public static void menuError(String error){
        System.err.println(error);
    }
    
    public static String nombreHospital;
    public static String estadoActual ="";
    
    public static String menuLogin(){   
        System.out.println("Que usuario desea usar?");
        Scanner sc = new Scanner(System.in);
        nombreHospital = sc.nextLine();
        System.out.println("Contrasena:");
        String pass = sc.nextLine();
        return nombreHospital + ":" + pass;
    }
    
    public static int variacion = 0;
    public static int aCambiar = -1;
    public static boolean ocupar = true;
    public static boolean tareaEnColada = false;
    public static boolean salir = false;

    // futura implementacion del menu en UI
    public static int[] Menu(){
    //retorna un vector de 2 elementos, 
    //    el primero es el tipo_operacion del mensaje que se va a enviar
    //    el segundo es el cuerpo del mensaje que se va a enviar
        
        int[] retorno = new int[2];
        retorno[0] = 1;
        retorno[1] = 1;
        
        
        return retorno;
    }
    
    public static int[] menuCLI(){

        int[] retorno = new int[2];

        System.out.println("Que desea hacer?");
        System.out.println("1: ver_estado");
        System.out.println("2: crear_cama");
        System.out.println("3: eliminar_cama");
        System.out.println("4: ocupar_cama");
        System.out.println("5: desocupar_cama");
        System.out.println("6: desloguear");
        System.out.println("7: salir");

        Scanner sc = new Scanner(System.in);
        int opt = sc.nextInt();

        retorno[0] = opt;

        switch (opt) {
            case 1:
                System.out.println("Estado actual de: "+nombreHospital);
                retorno[1] = 0;
                return retorno;      
            case 2:
                System.out.println("Cuantas camas desea agregar?");
                break;
            case 3:
                System.out.println("Cuantas camas desea eliminar?");
                break;
            case 4:
                System.out.println("Que cama desea ocupar?");
                break;
            case 5:
                System.out.println("Que cama desea desocupar?");
                break;  
            case 6:
                System.out.println("Deslogueando de seccion: "+nombreHospital);
                retorno[1] = -1;
                return retorno;
            case 7:
                System.out.println("Matando proceso cliente");
                retorno[0] = 6;
                retorno[1] = -2;
                return retorno;
            
            default:                
                break;
        }
        retorno[1] = sc.nextInt();
        return retorno;
    }  
    
    


    public static void imprimirEstado(){
        System.out.println(formateo(estadoActual));
    }

    public static String formateo(String estadoActual){
        String retorno = "";
        for(int i=0;i<estadoActual.length();i++){
            retorno = retorno + "Cama N "+i+": ";
            if(estadoActual.charAt(i) == 'T'){
                retorno = retorno + "Ocupado"+'\n';
            }else{
                retorno = retorno + "Desocupado"+'\n';
            }
        }
        return retorno;
    }
}

