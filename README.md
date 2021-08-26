# Sistema informático Cliente/Servidor centralizado para Camas UTI por Socket TCP.
## Trabajo Grupal de Sistemas distribuidos de IIN, Facultad Politécnica de la UNA.

### Colaboradores
* Junior Gutierrez [@jg2kpy](https://github.com/jg2kpy)
* Diego Duarte [@diemanuel99](https://github.com/diemanuel99)
* Alejandro Cabral [@alcabvaldo](https://github.com/alcabvaldo)
* Jovana Alvarez [@jovananayeli](https://github.com/jovananayeli)

## Requerimientos
Mínimamente JDK 1.8 y un IDE que soporte proyectos Java Maven como por ejemplo Netbeans, Eclipse o IntelliJ.

### Debian-based OS (Debian, Ubuntu, etc)
   Actualizar repositorio
    
    sudo apt update
   Instalar el JDK (Java development kit)
    
    sudo apt install default-jdk
   Instalar un IDE de Java (en este ejemplo netbeans)
   
    sudo apt install netbeans

### Arch-based OS (Arch, Manjaro, etc)
   Seguir esta guía de la wiki de Arch: https://wiki.archlinux.org/title/netbeans

### WindowsNT-based OS (Windows 10, Windows 11, etc)
   Usar los instaladores
   
   JDK por Oracle:
   https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
   
   Netbeans por Apache:
   https://netbeans.apache.org/download/nb120/nb120.html
   
### macOS Family
   Seguir esta guía de Oracle:
   https://docs.oracle.com/en/java/javase/15/install/installation-jdk-macos.html#GUID-2FE451B0-9572-4E38-A1A5-568B77B146DE

## Base de datos SQLite
   El proyecto ServerTCP ya trae una base de datos poblada en el fichero CamasUTI_TCP\ServerTCP\DB\dataBase.db con el cual ya se puede hacer pruebas, por lo tanto no es necesario crear la base de datos y poblarla.
   
    CREATE TABLE hospital (
        id_hospital INTEGER PRIMARY KEY AUTOINCREMENT
                            UNIQUE
                            NOT NULL,
        nombre      TEXT    NOT NULL,
        password    TEXT    NOT NULL,
        camas       TEXT    NOT NULL
    );

### Poblar base de datos
    INSERT INTO hospital (nombre, password, camas) VALUES ("HospitalX1", "0001", "FFFF");
    INSERT INTO hospital (nombre, password, camas) VALUES ("HospitalX2", "0001", "TFTF");
    INSERT INTO hospital (nombre, password, camas) VALUES ("HospitalX3", "0001", "TTTT");
    
   
    
## Compilación y ejecución
   El primer paso es clonar el repositorio en un directorio local.
   
   ![imagen](https://user-images.githubusercontent.com/46907456/130639591-36d98244-2169-4067-800a-aa427e927f80.png)

    $ git clone https://github.com/jg2kpy/CamasUTI_TCP.git

   Una vez clonado, debemos abrir el directorio con el IDE.
   
   ![imagen](https://user-images.githubusercontent.com/46907456/130641151-5dd1b142-9d46-49eb-8257-533b878b9422.png)

   ![imagen](https://user-images.githubusercontent.com/46907456/130641166-69dfb9c0-d2f8-4629-a910-6f6f8811622b.png)

   Como son proyectos maven, un IDE de Java debería de reconocer los directorios CamasUTI_TCP\ClientTCP y CamasUTI_TCP\ServerTCP como proyectos.
   Finalmente para compilar y ejecutar se debe hacer click en el boton de play verde que dice "run project (F6)", la información de compilación y ejecución ya se encuentra en el archivo pom.xml
    
   ![imagen](https://user-images.githubusercontent.com/46907456/130646490-ee9b1922-e8a5-47a3-bb7f-0c60c0072af9.png)
   
## Instrucciones de utilización de la aplicación se encuentran en este PDF mediante este link:

https://drive.google.com/file/d/1P0Fe6GMGIxlGe9bi_2bRaYUC74yPRCvt/view?usp=sharing

## Documentación de la API para la creación de clientes:

https://drive.google.com/file/d/1Q2QYKUuMWkf6AEB1I9TTITJWy_aba1J6/view?usp=sharing
