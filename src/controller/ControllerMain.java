package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import connexio.Connexio;
import connexio.ConnexioContactes;
import connexio.ConnexioGrups;
import connexio.ConnexioNotes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Grup;
import model.Nota;
import penpalsprova.ControlNota;
import penpalsprova.LogInMain;
import penpalsprova.PenPalsMain;

public class ControllerMain implements Initializable {
	
	static ConnexioGrups connexioGrups;
	static ConnexioContactes connexioContactes;
	ConnexioNotes connexioNotes;
	static Stage about_stage;
	
	@FXML GridPane llistaContactes;
	@FXML GridPane llistaGrups;
	
	@FXML ImageView menuNotaRapida;
	@FXML ImageView menuAfegirUsuari;
	@FXML ImageView menuNotificacions;

	
	@FXML GridPane gridPaneMenuAfegirUsuari;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			connexioContactes = new ConnexioContactes();
			connexioGrups = new ConnexioGrups();
			carregarLlistaContactes();
			carregarGrups();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
	/**
	 * Mostra una llista amb tots els contactes que l'usuari connectat ha afegit
	 * @throws Exception
	 */
	public void carregarLlistaContactes() {
		try {
			List<String> contactes;
			contactes = connexioContactes.getContactesUsuari();
			
			for (int i=0; i<contactes.size(); i++) {
				Label contacte = new Label(contactes.get(i));
				llistaContactes.add(contacte, 0, i);
				
				contacte.setOnMouseClicked(e->veureUsuari(e, contacte.getText())); //quan es fa click a un element de la llista (un usuari), el mostrai
			}
			
			//l'últim element de la llista permet afegir un contacte nou
			llistaContactes.add(new Label("   + Afegir contacte"), 0, contactes.size());
		} catch (Exception ignored) {}
	}
	
	
	
	/**
	 * Obre en la finestra actual la vista amb els detalls d'un usuari
	 * @param e Click
	 * @param idUsuari ID de l'usuasri que es vol veure
	 */
	public void veureUsuari(Event e, String idUsuari) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
			loader.setController(new ControllerVeureUsuari(idUsuari, true));
			
			GridPane pantallaUsuari = loader.load();
			
			PenPalsMain.border_pane_main.setCenter(pantallaUsuari);
		} catch (Exception ignored) {ignored.printStackTrace();}
	}
	
	
	/**
	 * Mostra una llista amb totes les notificacions que hi han:
	 * sol·licituds d'amistat, notes noves, notes editades...
	 * @param e
	 * @throws Exception
	 */
	@FXML public void mostrarNotificacions(Event e) throws Exception {
		VBox llista = new VBox();
		llista.setSpacing(10);
		
		mostrarNotificacionsSolicitudsAmistat(llista);
		mostrarNotificacionsNotesNoves(llista);
			
				PopOver popover = new PopOver(llista);
		popover.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
		popover.show(menuNotificacions);
	}
	
	
	public void mostrarNotificacionsNotesNoves(VBox llista) throws Exception {
		if (connexioNotes.hiHaNotesNoves()) {
			List<Nota> llistaNotesNoves = connexioNotes.getNotesNoves();
			
			for(Nota notaNova : llistaNotesNoves) {
				
				String textNotificacio = notaNova.getAutor() + " ha afegit la nota \"" + notaNova.getTitol() + "\" al grup " + notaNova.getGrup().getNom();
				Label notificacioNotaNova = new Label(textNotificacio);
				
				llista.getChildren().add(notificacioNotaNova);
			}
		}
	}
	
	
	/**
	 * Afegeix a la llista de notificacions aquelles que informen d'una sol·licitud d'amistat nova.
	 * La sol·licitud d'amistat es podrà acceptar o declinar
	 * @param llista Llista amb totes les notificacions
	 */
	public void mostrarNotificacionsSolicitudsAmistat(VBox llista) {
		//mostra si hi ha alguna sol·licitud d'amistat
		List<String> llistaNotificacions = connexioContactes.veureSolicitudsAmistat();
				
		for(String notificacio : llistaNotificacions) {
			VBox solicitudAmistat = new VBox();
			
			Label text = new Label(notificacio);
			text.setPadding(new Insets(10, 10, 30, 10));
			
			HBox botons = new HBox(); //botons d'acceptar i declinar
			Button btAcceptar = new Button("Acceptar");
			Button btDeclinar = new Button("Declinar");
			botons.getChildren().addAll(btAcceptar, btDeclinar);
			
			solicitudAmistat.getChildren().addAll(text, botons);
			
			llista.getChildren().add(solicitudAmistat);
			
			//quan es prem el botó acceptar, s'afegeix l'usuari a la llista de contactes
			//i s'elimina de notificacions
			btAcceptar.setOnAction(new EventHandler() {
				@Override
				public void handle(Event e) {
					connexioContactes.acceptarSolicitudAmistat(text.getText());
					llistaContactes.getChildren().clear();
					carregarLlistaContactes();
					llista.getChildren().remove(solicitudAmistat);
				}
			});
			
			//elimina la notificació, pero NO afegeix l'usuari a la llista de contactes
			btDeclinar.setOnAction(new EventHandler() {
				@Override
				public void handle(Event e) {
					connexioContactes.declinarSolicitudAmistat(text.getText());
					llista.getChildren().remove(solicitudAmistat);
				}
			});
		}
	}
	
	/**
	 * Mostra al Menú Lateral/Grups un llistat amb els grups als qual pertany l'usuari
	 * Com a últim element de la llista, hi ha l'opció de crear un nou grup
	 * @param event
	 * @throws IOException
	 */
	public void carregarGrups() throws Exception {
		List<Grup> grups = connexioGrups.getGrupsUsuari();
		
		for(int i = 0; i<grups.size(); i++) {
			Label grup = new Label(grups.get(i).getNom());
			llistaGrups.add(grup, 0, i);
			int j = i;
			grup.setOnMouseClicked(e->veureGrup(e, grups.get(j)));
		}
		
		Connexio.setGrups(grups); //ara la llista de grups està disponible per totes les classes
		
		//l'últim element de la llista permet crear un grup nou
		llistaGrups.add(new Label("   + Crear grup"), 0, grups.size());
	}
	
	
	/**
	 * Obre en la mateixa finestra una vista amb els detalls d'un grup
	 * @param e
	 * @param grup Grup que es vol veure
	 */
	private void veureGrup(Event e, Grup grup) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureGrup.fxml"));
			loader.setController(new ControllerVeureGrup(grup));
			
			GridPane pantallaGrup = loader.load();
			
			PenPalsMain.border_pane_main.setCenter(pantallaGrup);
		} catch (Exception ignored) {ignored.printStackTrace();}
	}
	
	
	/**
	 * Obre en la mateixa finestra una vista amb els detalls d'una nota
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_nota(MouseEvent event) throws IOException {
		GridPane pantalla_veure_nota = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_nota);
	}
	
	
	
	/**
	 * Mostra la pantalla principal
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_principal(Event event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
	/**
	 * Tenca la sessió actual i torna a la pantalla d'inici
	 * @param event
	 * @throws IOException
	 */
	@FXML public void tencar_sessio(Event event) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLLogIn.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		LogInMain.main_scene = scene;
		
		main_stage.setScene(scene); 
	}
	
	
	
	/**
	 * Mostra informació sobre un grup en el que s'ha fet click
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_grup(Event event) throws IOException {
		GridPane pantalla_veure_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_grup);
	}
     
     
	/**
	 * Obre en una finestra diferent a l'actual la pantalla "About" amb informació sobre el programa
	 * @param event
	 * @throws Exception
	 */
	@FXML public void obrir_about(Event event) throws Exception {
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
	 * Obre un PopUp que permet afegir una nota de manera ràpida
	 * @param event
	 * @throws Exception
	 */
	@FXML public void obrir_notaRapida(Event event) throws Exception {
	
    	//Components del popOver
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
	

	/**
	 * Obre un Popup que permet enviar una sol·licitud d'amistat a un usuari, introduïnt la seva ID
	 * @param event
	 * @throws IOException
	 */
	@FXML public void afegirUsuariRapid (Event event) throws IOException {
		GridPane gridpane = FXMLLoader.load(getClass().getResource("/view/FXMLMenuAfegirUsuari.fxml"));

		PopOver popover = new PopOver();
		popover.setContentNode(gridpane);
		//Components del PopOver
		TextField campo = new TextField();   
		Label text = new Label("Afegir usuari");
		text.setPadding(new Insets(10, 10, 30, 10)); 
		campo.setPadding(new Insets(10, 10, 10, 10));
		
		Button popoverButton = new Button("Enviar sol·licitud");
	
		VBox vbox = new VBox(text,campo,popoverButton);
		vbox.setSpacing(10);
		
		popover.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
		popover.show(menuAfegirUsuari);
	}
	
	@FXML TextField idUsuariEnviarSolicitud;
	
	
	@FXML public void enviarSolicitudAmistat(ActionEvent e) throws Exception {
		connexioContactes.enviarSolicitudAmistat(idUsuariEnviarSolicitud.getText());
	}
	
     
	/**
	 * Surt del programa
	 */
	@FXML public void sortir_penpals() {
		Platform.exit();
		System.exit(0);
	}
}
