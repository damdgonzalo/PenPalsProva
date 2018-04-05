package penpalsprova;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ControllerContrasenyaOblidada {
	
	@FXML TextField correu;
	
	@FXML public void enviarCorreu(ActionEvent e) throws Exception {
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
	}
	
}
