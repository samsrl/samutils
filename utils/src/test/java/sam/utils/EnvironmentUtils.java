package sam.utils;

import io.github.cdimascio.dotenv.Dotenv;


public class EnvironmentUtils {
    public static Dotenv getEnvironment(){
		try {
			String env = System.getProperty("environment");
			if(env != null && !env.isEmpty()){
				return Dotenv.configure().directory("conf").filename(env).load();
			}else{
				String fallo = "No existe la system property \"environment\"";
				System.out.println(fallo);
				System.exit(1);
				return null;
			}
		} catch (Exception e) {
			String fallo = "No se pudo cargar el archivo de configuracion de entorno "+ e.getMessage();
			System.out.println(fallo);
			System.exit(1);
			return null;
		} 

	}

}
