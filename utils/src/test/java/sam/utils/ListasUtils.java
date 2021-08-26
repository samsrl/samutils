package sam.utils;

public class ListasUtils {
	public String urlError;
	String mensajeOK;
	String mensajeERR;
	Exception fallo;
	String screenshots;
	String screenName;

	public ListasUtils(String urlError, String mensajeOK, String mensajeERR , String screenshots, String screenName, Exception fallo) {
		super();
		this.urlError = urlError;
		this.mensajeOK = mensajeOK;
		this.mensajeERR = mensajeERR;
		this.screenshots = screenshots;
		this.screenName = screenName;
		this.fallo = fallo;
	}

	public String getUrlError() {
		return urlError;
	}

	public void setUrlError(String urlError) {
		this.urlError = urlError;
	}

	public String getMensajeOK() {
		return mensajeOK;
	}

	public void setMensajeOK(String mensajeOK) {
		this.mensajeOK = mensajeOK;
	}

	public String getMensajeERR() {
		return mensajeERR;
	}

	public void setMensajeERR(String mensajeERR) {
		this.mensajeERR = mensajeERR;
	}

	public Exception getFallo() {
		return fallo;
	}

	public void setFallo(Exception fallo) {
		this.fallo = fallo;
	}

	public String getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(String screenshots) {
		this.screenshots = screenshots;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	@Override
	public String toString() {
		if (mensajeERR == "") {
			return ("Paso: " + mensajeOK + "\n" + "<br>");
		} else {
			return ("Paso: " + mensajeERR + "\n " + "<br>" + "URL del error: " + urlError + "\n" + "<br>" + fallo + "\n"
					+ "<br>");

		}
	}
}

