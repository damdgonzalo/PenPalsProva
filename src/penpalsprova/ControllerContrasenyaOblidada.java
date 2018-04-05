package penpalsprova;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerContrasenyaOblidada {
	
	@FXML TextField correu;
	
	/*@FXML public void enviarCorreu(ActionEvent e) throws Exception {
		String de = "damdgonzalo@gmail.com";
		String per = correu.getText();
		String host = "localhost";
		
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		
		Session session = Session.getDefaultInstance(properties);
		
		MimeMessage missatge = new MimeMessage(session);
		missatge.setFrom(new InternetAddress(de));
		missatge.addRecipient(Message.RecipientType.TO, new InternetAddress(per));
		missatge.setSubject("Recuperació de la contrasenya");
		missatge.setText("La teva contrasenya és: " + ControllerLogIn.connexio.getContrasenyaPerCorreu(per));
		//missatge.setContent("<html>....","text/html");
		
		//enviar el missatge
		Transport.send(missatge);
		
		ControllerLogIn.contrasenyaOblidadaStage.close();
	}*/
	
	@FXML public void enviarCorreu(ActionEvent e) throws Exception {
		// SMTP server information
	    String host = "localhost";
	    String port = "25";
	    String mailFrom = "damdgonzalo@gmail.com";
	    String password = "DD32843Ph";

	    // outgoing message information
	    String mailTo = correu.getText();
	    String subject = "Recuperació de la contrasenya";
	    
	    sendHtmlEmail(host, port, mailFrom, password, mailTo, subject);
	}
	
	public void sendHtmlEmail(String host, String port,
	        final String userName, final String password, String toAddress,
	        String subject) throws Exception {

	    // sets SMTP server properties
	    Properties properties = new Properties();
	    properties.put("mail.man.com", host);
	    properties.put("mail.25", port);
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.ssl.trust","mail.man.com");

	    // creates a new session with an authenticator
	    Authenticator auth = new Authenticator() {
	        public PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(userName, password);
	        }
	    };

	    Session session = Session.getInstance(properties, auth);

	    // creates a new e-mail message
	    Message msg = new MimeMessage(session);

	    msg.setFrom(new InternetAddress(userName));
	    msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());
	    // set plain text message
	    msg.setText("La teva contrasenya és: " + ControllerLogIn.connexio.getContrasenyaPerCorreu(toAddress));

	    // sends the e-mail
	    Transport.send(msg);

	}
	
}
