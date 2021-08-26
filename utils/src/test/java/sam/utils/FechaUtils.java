package sam.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FechaUtils {
    public static DateTimeFormatter ISODateTimeFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static DateTimeFormatter SimpleDateFormatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DurationFormatter = java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter fechaConBarrasFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static DateTimeFormatter fechaHoraArchivo = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss");

    public static String obtenerFechaActual() {
        return ZonedDateTime.now().format(ISODateTimeFormatter);
    }

    public static String obtenerFechaActual(DateTimeFormatter formatter) {
        return ZonedDateTime.now().format(formatter);
    }

    public static String obtenerFechaSimple() {
        return ZonedDateTime.now().format(SimpleDateFormatter);
    }

}
