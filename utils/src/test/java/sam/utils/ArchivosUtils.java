package sam.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;

public class ArchivosUtils {
	// ---------------METODOS PARA MANEJAR EL ARCHIVO---------------\\
	public static void limpiarArchivo(String path) throws IOException {
		try {
			abrirArchivo(path);

			// GUARDAR VALOR DEL CONTADOR
			FileWriter bw = new FileWriter(path, false);
			bw.write("");
			bw.close();
		} catch (Exception e) {
			System.out.println("Fallo al intentar limpiar el archivo. Error: " + e);
		}
	}

	public static String leerArchivo(String path) throws IOException {
		abrirArchivo(path);

		FileInputStream fis = new FileInputStream(path);
		byte[] buffer = new byte[1];
		StringBuilder sb = new StringBuilder();
		while (fis.read(buffer) != -1) {
			sb.append(new String(buffer));
			buffer = new byte[1];
		}
		fis.close();

		String contenidoDelArchivo = String.valueOf(sb.toString());
		return contenidoDelArchivo;
	}

	public static void actualizarArchivo(String path, String valor) throws IOException {
		try {
			abrirArchivo(path);

			// GUARDAR VALOR DEL CONTADOR
			FileWriter bw = new FileWriter(path, true);
			bw.write(valor);
			bw.close();
		} catch (Exception e) {
			System.out.println("Fallo al intentar actualizar el archivo. Error: " + e);
		}
	}

	public static void abrirArchivo(String path) throws IOException {
		// Devuelve el control del archivo para que lo puedan usar los otros m�todos
		// VERIFICAR QUE EXISTA EL ARCHIVO, EN CASO CONTRARIO CREAR UNO NUEVO
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.createNewFile();
				FileWriter FW = new FileWriter(path);
				BufferedWriter BW = new BufferedWriter(FW);
				BW.write("");
				BW.close();
			}
		} catch (Exception e) {
			System.out.println("Fallo al intentar abrir el archivo. Error: " + e);
		}
	}

	public static String crearRutaRelativa(String path){
		String rutaDelProyecto = Paths.get("").toAbsolutePath().toString();
		Path rutaAbsoluta = Paths.get(rutaDelProyecto, path);

		File f = new File(rutaAbsoluta.toString());
		f.mkdir();

		return rutaAbsoluta.toString();
	}
	
	public static Boolean existenArchivosEnDirectorio(String path) throws IOException {
		Boolean existeArchivo = false;
		File dir = new File(path);
		try (Stream<Path> files = Files.list(Paths.get(path))) {
			long count = files.count();
			if (count > 0) {
				existeArchivo = true;
				FileUtils.cleanDirectory(dir);
			}
		}
		return existeArchivo;
	}
	
}
