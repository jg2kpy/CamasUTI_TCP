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
    
    public static String menuLogin(){   
        System.out.println("Que usuario desea usar?");
        Scanner sc = new Scanner(System.in);
        nombreHospital = sc.nextLine();
        System.out.println("Contrasena:");
        String pass = sc.nextLine();
        return nombreHospital + ":" + pass;
    }
    
    public static int[] Menu(){
	//retorna un vector de 2 elementos, 
	//	el primero es el tipo_operacion del mensaje que se va a enviar
	//	el segundo es el cuerpo del mensaje que se va a enviar
        
        int[] retorno = new int[2];
		retorno[0] = 1;
		retorno[1] = 1;
		
		
        return retorno;
    }
}
