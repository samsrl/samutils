package sam.utils;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


public class JSONUtils {

	public static enum resultadoTest {
		FAILED, SKIPPED, PASSED;
	}

	public static JSONObject baseJSON = new JSONObject();
	public static JSONObject suitesJSON = new JSONObject();
	public static JSONArray testsJSON = new JSONArray();
	public static JSONArray pasosJSON = new JSONArray();

	public static String JSONKeyAppName = "Aplicacion";
	public static String JSONKeySuite = "Suite";
	public static String JSONKeyDescripcion = "Descripcion";
	public static String JSONKeyFechaInicial = "Fecha_de_inicio";
	public static String JSONKeyFechaFinal = "Fecha_de_fin";
	public static String JSONKeyTiempoTotal = "Tiempo_transcurrido";
	public static String JSONKeyPass = "Passed";
	public static String JSONKeyFail = "Failed";
	public static String JSONKeySkip = "Skipped";
	public static String JSONKeyTests = "Tests";
	public static String JSONKeyPasos = "Pasos";
	public static String JSONKeyResultadoEsperado = "Resultado_esperado";
	public static String JSONKeyResultadoObtenido = "Resultado_obtenido";
	public static String JSONKeyNombreTest = "Prueba";
	public static String JSONKeyFuncion = "Funcion";

	// ---------------METODOS JSON---------------\\
	public static void inicializarJSON (String appName, String appDescription){
		JSONUtils.baseJSON.put(JSONUtils.JSONKeyAppName, appName);
		JSONUtils.suitesJSON.put(JSONUtils.JSONKeyDescripcion, appDescription);
		JSONUtils.suitesJSON.put(JSONUtils.JSONKeyFechaInicial, FechaUtils.obtenerFechaActual());
	}

	public static void finalizarJSON (String pathArchivoTiempos) throws IOException{
		JSONUtils.suitesJSON.put(JSONUtils.JSONKeyFechaFinal, FechaUtils.obtenerFechaActual());
		JSONUtils.suitesJSON.put(JSONUtils.JSONKeyTests, JSONUtils.testsJSON);
		JSONUtils.CalcularTiempoObject(JSONUtils.suitesJSON);
		JSONUtils.CalcularTiempoArray(JSONUtils.suitesJSON, JSONUtils.JSONKeyTests);
		JSONUtils.CalcularResultadoFinal(JSONUtils.suitesJSON,JSONUtils.JSONKeyTests);
		JSONUtils.baseJSON.put(JSONUtils.JSONKeySuite, JSONUtils.suitesJSON);
		JSONUtils.ImprimirArchivoJSON(JSONUtils.baseJSON, pathArchivoTiempos);
	}
	public static JSONObject crearTestJSON(String descripcionTest) {
		JSONObject testJSON = new JSONObject();
		testJSON.put(JSONKeyDescripcion, descripcionTest);
		testJSON.put(JSONKeyFechaInicial, FechaUtils.obtenerFechaActual());
		return testJSON;
	}

	public static JSONObject crearPasoJSON(String descripcionPaso, String resultadoEsperadoPaso) {
		JSONObject pasoJSON = new JSONObject();
		pasoJSON.put(JSONKeyDescripcion, descripcionPaso);
		pasoJSON.put(JSONKeyResultadoEsperado, resultadoEsperadoPaso);
		pasoJSON.put(JSONKeyFechaInicial, FechaUtils.obtenerFechaActual());
		return pasoJSON;
	}

	public static void resultadoPasoJSON(JSONObject testJSON, JSONArray arrayJSON, JSONObject objectJSON,
			String resultadoObtenidoPaso) {

		objectJSON.put(JSONKeyResultadoObtenido, resultadoObtenidoPaso);
		objectJSON.put(JSONKeyFechaFinal, FechaUtils.obtenerFechaActual());
		arrayJSON.put(objectJSON);
		CalcularTiempoObject(objectJSON);
		testJSON.put(JSONKeyPasos, arrayJSON);
	}

	public static void registrarResultadoJSON(JSONArray arrayJSON, JSONObject objectJSON, resultadoTest resultadoTest) {
		switch (resultadoTest) {
			case PASSED:
				objectJSON.put(JSONKeyFail, 0);
				objectJSON.put(JSONKeyPass, 1);
				objectJSON.put(JSONKeySkip, 0);
				break;
			case FAILED:
				objectJSON.put(JSONKeyFail, 1);
				objectJSON.put(JSONKeyPass, 0);
				objectJSON.put(JSONKeySkip, 0);
				break;
			case SKIPPED:
				objectJSON.put(JSONKeyFail, 0);
				objectJSON.put(JSONKeyPass, 0);
				objectJSON.put(JSONKeySkip, 1);
				break;
		}

		objectJSON.put(JSONKeyFechaFinal, FechaUtils.obtenerFechaActual());
		arrayJSON.put(objectJSON);
		CalcularTiempoObject(objectJSON);
	}

	public static void ImprimirArchivoJSON(JSONObject JSON, String pathArchivo) throws IOException {
		String jsonString = JSON.toString();
		try {
			ArchivosUtils.limpiarArchivo(pathArchivo);
			ArchivosUtils.actualizarArchivo(pathArchivo, jsonString);
		} catch (Exception ex) {
			System.out.println("ImprimirArchivoJSON error: " + ex.toString());
		}
	}

	public static String calcularTiempoEntreFechas(Temporal fechaInicial, Temporal fechaFinal) {
		// Calcula el tiempo final de ejecucion
		Duration resultado = Duration.between(fechaInicial, fechaFinal);
		String resultadoFormateado = LocalTime.MIDNIGHT.plus(resultado).format(FechaUtils.DurationFormatter);

		return String.valueOf(resultadoFormateado);
	}

	public static String calcularTiempoEntreFechas(String fechaInicial, String fechaFinal) {
		ZonedDateTime dtFechaInicial = ZonedDateTime.parse(fechaInicial, FechaUtils.ISODateTimeFormatter);
		ZonedDateTime dtFechaFinal = ZonedDateTime.parse(fechaFinal, FechaUtils.ISODateTimeFormatter);
		// Calcula el tiempo final de ejecucion
		Duration resultado = Duration.between(dtFechaInicial, dtFechaFinal);
		String resultadoFormateado = LocalTime.MIDNIGHT.plus(resultado).format(FechaUtils.DurationFormatter);

		return String.valueOf(resultadoFormateado);
		// System.out.println(resultado);
	}

	public static String TiempoEsperado(String mensajeSLASuperado, Float SLAEsperado, Float tiempoTranscurrido,
			ListasUtils pasos, List<ListasUtils> listaPasos, String url) throws IOException {
		if (tiempoTranscurrido > SLAEsperado) {
			System.out.println(mensajeSLASuperado + tiempoTranscurrido + "\n");
			String mensajeError = mensajeSLASuperado + tiempoTranscurrido.toString();
			pasos = new ListasUtils(url, "", mensajeError, null, null, null);
			listaPasos.add(pasos);

			return "OK";
		} else {
			System.out.println("Se encuentra dentro del tiempo esperado" + "\n");
			return "FAIL";
		}
	}

	public static void CalcularTiempoObject(JSONObject JSON) {
		try {
			String fechaInicialTest = (String) JSON.get(JSONKeyFechaInicial);
			String fechaFinalTest = (String) JSON.get(JSONKeyFechaFinal);

			String tiempoTotalTest = calcularTiempoEntreFechas(fechaInicialTest, fechaFinalTest);

			JSON.put(JSONKeyTiempoTotal, tiempoTotalTest);

		} catch (Exception e) {
			System.out.println("Fallo al intentar imprimir el archivo JSON. Error: " + e);
			// System.out.println("JSON " + JSON.toString());
		}

	}

	public static void CalcularTiempoArray(JSONObject JSON, String keyCollection) {
		try {
			JSONArray pasosJSONLocal = JSON.getJSONArray(keyCollection);

			for (int i = 0; i < pasosJSONLocal.length(); i++) {
				JSONObject paso = pasosJSONLocal.getJSONObject(i);
				String fechaInicialPaso = (String) paso.get(JSONKeyFechaInicial);
				String fechaFinalPaso = (String) paso.get(JSONKeyFechaFinal);

				String tiempoTotalPaso = calcularTiempoEntreFechas(fechaInicialPaso, fechaFinalPaso);
				paso.put(JSONKeyTiempoTotal, tiempoTotalPaso);
			}

			// System.out.println("JSON " + JSON.toString());
		} catch (Exception e) {
			System.out.println("CATCH TIEMPO ARRAY: " + e);
		}
	}

	public static void CalcularResultadoFinal(JSONObject JSON, String keyCollection) {
		try {
			JSONArray pasosJSONLocal = JSON.getJSONArray(keyCollection);
			Integer cantFailPaso = 0;
			Integer cantPassPaso = 0;
			Integer cantSkipPaso = 0;
			for (int i = 0; i < pasosJSONLocal.length(); i++) {
				JSONObject paso = pasosJSONLocal.getJSONObject(i);
				cantFailPaso = cantFailPaso + (Integer) paso.get(JSONKeyFail);
				cantPassPaso = cantPassPaso + (Integer) paso.get(JSONKeyPass);
				cantSkipPaso = cantSkipPaso + (Integer) paso.get(JSONKeySkip);

				JSONUtils.suitesJSON.put(JSONKeyFail, cantFailPaso);
				JSONUtils.suitesJSON.put(JSONKeyPass, cantPassPaso);
				JSONUtils.suitesJSON.put(JSONKeySkip, cantSkipPaso);

				System.out.println("FAIL: " + cantFailPaso + " PASS: " + cantPassPaso + " SKIP: " + cantSkipPaso);

			}

			// System.out.println("JSON " + JSON.toString());
		} catch (Exception e) {
			System.out.println("CATCH RESULTADO FINAL: " + e);
		}
	}

	public static Boolean CalcularSLA(JSONObject JSON) {
		Boolean result = false;
		Integer TiempoTotalLocal = (Integer) JSON.get(JSONKeyTiempoTotal);
		try {
			if (TiempoTotalLocal > 6) {
				result = true;
			}
			System.out.println("RESULTADO IF DEL SLA: " + result + "TIEMPO TOTAL LOCAL: " + TiempoTotalLocal);
		} catch (Exception e) {
			System.out.println("RESULTADO CATCH DEL SLA: " + result + "TIEMPO TOTAL LOCAL: " + TiempoTotalLocal);
			throw e;
		}
		return result;
	}
}
