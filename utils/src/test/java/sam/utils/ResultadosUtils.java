package sam.utils;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;

public class ResultadosUtils {
	public static void Ok(String paso, List<ListasUtils> listaPasos) {
		String mensaje = "El test logró " + paso + " correctamente";
		listaPasos.add(new ListasUtils("", mensaje, "", null, null, null));
		System.out.println(mensaje);
	}
	public static void Error(Exception exception, String paso, String ventana, 
		String photoDirectoryPath, List<ListasUtils> listaPasos, WebDriver driver) throws IOException {
		
		String fecha = FechaUtils.obtenerFechaSimple();
		String hora = FechaUtils.obtenerFechaActual(FechaUtils.DurationFormatter);
		
		String mensaje = "El test falló al " + paso;
		System.out.println(mensaje);
		String obtenerURL = driver.getCurrentUrl();
		String photoFilePath = ScreenshotsUtils.TomarCapturaDePantalla(ventana, photoDirectoryPath, driver);
		listaPasos.add(new ListasUtils(obtenerURL, "", mensaje,
				photoFilePath, "Portal Corporativo", exception));
		fail(" " + fecha + " " + hora + exception);
		System.out.println(exception);
	}
	
	public static void ErrorIntentos(String paso, int intento, List<ListasUtils> listaPasos, WebDriver driver) throws IOException {
			String mensaje = "El test falló el intento #" + Integer.toString(intento)+ " al " + paso;
			System.out.println(mensaje);
			listaPasos.add(new ListasUtils("", mensaje, "", null, null, null));
		}
}
