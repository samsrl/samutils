package sam.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotsUtils {

	public static String fechaHora = FechaUtils.obtenerFechaActual(FechaUtils.fechaHoraArchivo);

	// ---------------METODOS SCREENSHOTS---------------\\
	public static void ScreenFail(String nombreArchivo, String pathArchivo, WebDriver driver) throws IOException {
		// Metodo que saca capturas de pantalla en caso de que se produzca un error
		File photo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		pathArchivo = "src" + File.separator + "resources" + File.separator + "screenshotsFail" + File.separator
				+ fechaHora + "-" + nombreArchivo + ".png";
		FileUtils.copyFile(photo, new File(pathArchivo));

	}

	public static String TomarCapturaDePantalla(String nombreArchivo, String path, WebDriver driver)
			throws IOException {
		// Metodo que saca capturas de pantalla en caso de que se produzca un error
		File photo = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String pathArchivo = path + File.separator + fechaHora + "-" + nombreArchivo + ".png";
		FileUtils.copyFile(photo, new File(pathArchivo));
		return pathArchivo;

	}

	public static String TomarCapturaDePantalla(File screenshot, String pathScreenshots, String nombreArchivo)
			throws IOException {
		String nuevoPath = pathScreenshots + File.separator + fechaHora + "-" + nombreArchivo + ".png";
		FileUtils.copyFile(screenshot, new File(nuevoPath));
		return nuevoPath;
	}
}
