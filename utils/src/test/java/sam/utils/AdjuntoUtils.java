package sam.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AdjuntoUtils {

	String pathAdjunto;
	String tipoAdjunto;

	public AdjuntoUtils(String pathAdjunto, String tipoAdjunto) {
		super();
		this.pathAdjunto = pathAdjunto;
		this.tipoAdjunto = tipoAdjunto;

	}

	public String getPathAdjunto() {
		return pathAdjunto;
	}

	public void setPathAdjunto(String pathAdjunto) {
		this.pathAdjunto = pathAdjunto;
	}

	public String getTipoAdjunto() {
		return tipoAdjunto;
	}

	public void setTipoAdjunto(String tipoAdjunto) {
		this.tipoAdjunto = tipoAdjunto;
	}

	public String getNombreAdjunto() {
		Path p = Paths.get(this.pathAdjunto);
		return p.getFileName().toString();
	}
}
