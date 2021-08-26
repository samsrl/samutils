package sam.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WSUtils {
    
    public static String[] validarPorSOAP(String endpoint, String requestXML, String metodo) throws IOException {
		String[] respuestaWS = new String[3];

		try {
			URL obj = new URL(endpoint);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(metodo); //POST - GET
			con.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(requestXML);
			wr.flush();
			wr.close();
			String responseMessage = con.getResponseMessage();
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer responseXML = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				responseXML.append(inputLine);
			}
			in.close();

			respuestaWS[0] = String.valueOf(responseCode);
			respuestaWS[1] = String.valueOf(responseMessage);
			respuestaWS[2] = String.valueOf(responseXML);		
		} catch (Exception e) {
			System.out.println("Ha ocurrido un Error " + e);
			respuestaWS[0] = "500";
			respuestaWS[1] = "ERROR";
			respuestaWS[2] = "";
		}

		return respuestaWS;
	}
}
