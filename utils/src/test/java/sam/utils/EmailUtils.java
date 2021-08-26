package sam.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtils {

	// ---------------LISTA DE EMAIL---------------\\
	// Lista de Mails, aca agregamos las direcciones que declaramos anteriormente
	public static List<String> ArmarListaMails(String emailsInternos, String emailsClientes,
			Boolean notificarClientes) {
		String emails = emailsInternos;
		if (notificarClientes) {
			emails += "," + emailsClientes;
		}
		return Arrays.asList(emails.split(","));
	}

	public static void NotificarPorMail(String nombreGrupo, String nombreTest, String emailsInternos,
			String emailsClientes, List<ListasUtils> listaPasos, List<ListasUtils> listaSkip,
			Boolean notificarDeTodosModos, Boolean notificarClientes)
			throws UnsupportedEncodingException, MessagingException {
		String Subject;
		Boolean hayErrores = false;
		Boolean hayErroresSkip = false;
		List<String> listaEmailSkip = ArmarListaMails(emailsInternos, emailsClientes, false);
		List<String> listaEmail = ArmarListaMails(emailsInternos, emailsClientes, notificarClientes);

		for (int i = 0; i < listaPasos.size(); i++) {
			if (listaPasos.get(i).getMensajeERR() != null && listaPasos.get(i).getMensajeERR() != "") {
				hayErrores = true;
				break;
			}
		}
		if (listaSkip != null) {
			for (int i = 0; i < listaSkip.size(); i++) {
				if (listaSkip.get(i).getMensajeERR() != null && listaSkip.get(i).getMensajeERR() != "") {
					hayErroresSkip = true;
					break;
				}
			}
		}
		if (hayErrores) {
			Subject = nombreGrupo + " - " + nombreTest;
			ArmarMail(Subject, listaEmail, listaPasos, true);
		}
		if (hayErroresSkip) {
			Subject = "Skip " + nombreGrupo + "-" + nombreTest;
			ArmarMail(Subject, listaEmailSkip, listaSkip, true);
		}
		if (!hayErrores && !hayErroresSkip && notificarDeTodosModos) {
			Subject = nombreGrupo + " - " + nombreTest;
			ArmarMail(Subject, listaEmail, listaPasos, false);
		}
	}

	private static void ArmarMail(String prefijoAsunto, List<String> emails, List<ListasUtils> listaPasos,
			Boolean huboError) throws UnsupportedEncodingException, MessagingException {
		String asunto = "";
		String cuerpoMail = "";
		cuerpoMail = "<H2>" + " " + FechaUtils.obtenerFechaActual(FechaUtils.fechaHoraArchivo) + "</H2>" + "<b>";
		cuerpoMail = cuerpoMail + "<br>" + listaPasos.toString() + "<br>";
		if (!huboError) {
			asunto = prefijoAsunto + " - NO HUBO FALLAS";
		} else {
			for (int i = 0, j = 1; i < listaPasos.size(); i++) {
				if (listaPasos.get(i).getMensajeERR() != null && listaPasos.get(i).getMensajeERR() != "") {
					asunto = prefijoAsunto + " - HUBO " + j + " FALLAS";
					j++;
				}
			}
		}
		List<AdjuntoUtils> adjuntos = new ArrayList<AdjuntoUtils>();
		for (int i = 0; i < listaPasos.size(); i++) {
			if (listaPasos.get(i).getScreenshots() != null && listaPasos.get(i).getScreenshots().trim() != "") {
				adjuntos.add(new AdjuntoUtils(listaPasos.get(i).getScreenshots(), "PNG"));

			}
		}
		EnviaMail(asunto, cuerpoMail, emails, adjuntos);
	}

	private static void EnviaMail(String asunto, String cuerpoMail, List<String> emails, List<AdjuntoUtils> adjuntos)
			throws UnsupportedEncodingException, MessagingException {

		final String from = "os-certificacionoperaciones@osde.com.ar";

		InternetAddress[] address = new InternetAddress[emails.size()];
		for (int i = 0; i < emails.size(); i++) {
			address[i] = new InternetAddress(emails.get(i));
		}
		final String host = "mail.osde.ar";
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			Session session = Session.getInstance(props);

			try {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.setRecipients(Message.RecipientType.TO, address);

				message.setSubject(asunto);
				MimeMultipart multipart = new MimeMultipart("related");
				BodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart.setContent(cuerpoMail, "text/html");
				multipart.addBodyPart(messageBodyPart);
				for (int i = 0; i < adjuntos.size(); i++) {

					messageBodyPart = new MimeBodyPart();
					BodyPart messageBodyPart2 = new MimeBodyPart();

					DataSource source = new FileDataSource(adjuntos.get(i).getPathAdjunto());
					messageBodyPart2.setDataHandler(new DataHandler(source));

					messageBodyPart2.setFileName(adjuntos.get(i).getNombreAdjunto());

					multipart.addBodyPart(messageBodyPart2);
					message.setContent(multipart);
				}
				message.setContent(multipart);
				Transport.send(message);
				System.out.println("Se envió el mail correctamente");

			} catch (MessagingException exce) {

				System.out.println("Mail no enviado correctamente " + exce);
			}
		} catch (Exception exce) {
			exce.printStackTrace();
		}
	}
}