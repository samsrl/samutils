package sam.utils;

import java.util.List;
import java.util.function.Consumer;

import org.openqa.selenium.WebDriver;

public class ReintentosUtils {
	/* CÓMO SE USA:
	 * Consumer<Void> variableQueLLamaAlMetodo = x -> metodo(arg1, arg2);
	 * ReintentosUtils.Reintentar(variableQueLLamaAlMetodo, numeroDeIntentos, nombreDePaso, listaPasos, driver);
	 */
	public static void Reintentar(Consumer<Void> action, int intentos,
			String paso, List<ListasUtils> listaPasos, WebDriver driver) throws Exception {
			int contador = 0;
			try {ReintentoAux(action, intentos, contador, paso, listaPasos, driver);}
			catch(Exception e) {throw e;}
	}
	
	private static void ReintentoAux(Consumer<Void> action, int intentos, int contador,
			String paso, List<ListasUtils> listaPasos, WebDriver driver) throws Exception {
		if(contador < intentos) {
			try {
				action.accept(null);
				ResultadosUtils.Ok(paso, listaPasos);
			}catch(Exception e) {
				ResultadosUtils.ErrorIntentos(paso, contador, listaPasos, driver);
				contador++;
				ReintentoAux(action, intentos, contador, paso, listaPasos, driver);
			}
		}else throw new Exception("Límite de intentos excedido");
	}
}
