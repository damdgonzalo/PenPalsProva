package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import penpalsprova.Connexio;
import penpalsprova.ConnexioContactes;
import penpalsprova.ConnexioGrups;
import penpalsprova.LogInMain;
import penpalsprova.PenPalsMain;

public class ControllerMain implements Initializable {
	
	static ConnexioGrups connexioGrups;
	static ConnexioContactes connexio;
	static Stage about_stage;
	
	@FXML GridPane llistaContactes;
	@FXML GridPane llistaGrups;
	
	@FXML Button menuNotaRapida;
	@FXML Button menuAfegirUsuari;
	@FXML Button menuNotificacions;
	
	
	@FXML GridPane gridPaneMenuAfegirUsuari;
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			connexio = new ConnexioContactes();
			connexioGrups = new ConnexioGrups();
			carregarLlistaContactes();
			carregarGrups();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	/**
	 * Mostra una llista amb tots els contactes que t� l'usuari connectat
	 * @throws Exception
	 */
	public void carregarLlistaContactes() throws Exception {
		List<String> contactes = connexio.getContactesUsuari();
		
		for (int i=0; i<contactes.size(); i++) {
			Label lb = new Label(contactes.get(i));
			llistaContactes.add(lb, 0, i);
		}
		
		llistaContactes.add(new Label("   + Afegir contacte"), 0, contactes.size());
	}
	
	@FXML public void mostrarNotificacions(ActionEvent e) throws Exception {
		List<String> llistaNotificacions = Connexio.veureNotificacions();
		
		VBox llista = new VBox();
		for(String notificacio : llistaNotificacions) {
			Label text = new Label(notificacio);
			//Button popoverButton = new Button("Guardar");
			text.setPadding(new Insets(10, 10, 30, 10));
			
			llista.getChildren().add(text);
		}
		llista.setSpacing(10);
		
		
		PopOver popover = new PopOver(llista);
		popover.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
		popover.show(menuNotificacions);
	}
	
	/**
	 * Mostra els detalls d'una nota en la que s'ha fet click
	 * @param event
	 * @throws IOException
	 */
	public void carregarGrups() throws Exception {
		List<String> contactes = connexioGrups.getContactesUsuari();
		
		for(int i = 0; i<contactes.size(); i++) {
			Label lb = new Label(contactes.get(i));
			llistaGrups.add(lb, 0, i);
		}
		
		llistaGrups.add(new Label("   + Afegir grup"), 0, contactes.size());
	}
	
	@FXML javafx.scene.control.Button notaRapida;
	
	@FXML public void veure_nota(MouseEvent event) throws IOException {
		GridPane pantalla_veure_nota = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_nota);
	}
	
	/**
	 * Mostra la pantalla principal
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_principal(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
	/**
	 * Tenca la sessi� actual i torna a la pantalla d'inici
	 * @param event
	 * @throws IOException
	 */
	@FXML public void tencar_sessio(ActionEvent event) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLLogIn.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		LogInMain.main_scene = scene;
		
		main_stage.setScene(scene); 
	}
	
	/**
	 * Mostra informaci� sobre un grup en el que s'ha fet click
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_grup(MouseEvent event) throws IOException {
		GridPane pantalla_veure_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_grup);
		
		
	}
     
     
	/**
	 * Obre en una finestra diferent a l'actual la pantalla "About" amb informaci� sobre el programa
	 * @param event
	 * @throws Exception
	 */
	@FXML public void obrir_about(ActionEvent event) throws Exception {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLAbout.fxml")); //finestra que volem obrir
    	 
		Scene scene = new Scene(root);
		about_stage = new Stage();
    	 
		about_stage.setScene(scene);
		about_stage.setTitle("About PenPals");
		about_stage.setResizable(false);
		about_stage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		about_stage.initOwner(PenPalsMain.main_scene.getWindow()); 
         
		about_stage.show();
	}
	
	/**
	 * Obre en una finestra diferent a l'actual la pantalla "About" amb informaci� sobre el programa
	 * @param event
	 * @throws Exception
	 */
	@FXML public void obrir_notaRapida(ActionEvent event) throws Exception {
	
    	/*Components del popOver*/
		TextField campo = new TextField();   
		Label text = new Label("Nova Nota");
		Button popoverButton = new Button("Guardar");
		text.setPadding(new Insets(10, 10, 30, 10)); 
		campo.setPadding(new Insets(10, 10, 10, 10));
		
		VBox vbox = new VBox(text,campo,popoverButton);
		vbox.setSpacing(10);
		
		
		PopOver popover = new PopOver(vbox);
		popover.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
		popover.show(menuNotaRapida);
	}
	

	@FXML public void afegirUsuariRapid (ActionEvent event) throws IOException {
		GridPane gridpane = FXMLLoader.load(getClass().getResource("/view/FXMLMenuAfegirUsuari.fxml"));

		PopOver popover = new PopOver();
		popover.setContentNode(gridpane);
		/*Components del popOver*/
		TextField campo = new TextField();   
		Label text = new Label("Afegir usuari");
		text.setPadding(new Insets(10, 10, 30, 10)); 
		campo.setPadding(new Insets(10, 10, 10, 10));
		
		Button popoverButton = new Button("Enviar sol�licitud");
	
		VBox vbox = new VBox(text,campo,popoverButton);
		vbox.setSpacing(10);
		
		popover.setArrowLocation(PopOver.ArrowLocation.TOP_LEFT);
		popover.show(menuAfegirUsuari);
	}
	
	@FXML TextField idUsuariEnviarSolicitud;
	
	@FXML public void enviarSolicitudAmistat(ActionEvent e) throws Exception {
		connexio.enviarSolicitudAmistat(idUsuariEnviarSolicitud.getText());
	}
	
     
	/**
	 * Surt del programa
	 */
	@FXML public void sortir_penpals() {
		Platform.exit();
		System.exit(0);
	}
}