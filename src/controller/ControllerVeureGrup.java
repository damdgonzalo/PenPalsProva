package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import penpalsprova.Connexio;
import penpalsprova.Grup;
import penpalsprova.PenPalsMain;

public class ControllerVeureGrup implements Initializable {
	
	static Stage administrar_participants_stage;
	static String administrador;
	static Grup grupActual;
	
	@FXML Label nomGrup;
	@FXML Label administradorGrup;
	@FXML Label numeroParticipants;
	@FXML Label colorGrup;
	
	@FXML Button btAdministrarParticipants; //només visible si sóm qui ha del grup
	
	public ControllerVeureGrup(Grup grupActual) {
		this.grupActual = grupActual;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {		
		nomGrup.setText(grupActual.getNom());
		administradorGrup.setText(grupActual.getAdministrador());
		numeroParticipants.setText(grupActual.getParticipants().size() + " participants");
		colorGrup.setStyle("-fx-background-color: " + grupActual.getColorGrup());
		
		if (grupActual.getAdministrador().equals(Connexio.getUsuari())) btAdministrarParticipants.setVisible(true);
		else btAdministrarParticipants.setVisible(false);
	}
	

	@FXML public void administrar_participants(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLAdministrarParticipantsGrup.fxml")); //finestra que volem obrir
   	 
		Scene scene = new Scene(root);
		administrar_participants_stage = new Stage();
    	 
		administrar_participants_stage.setScene(scene);
		administrar_participants_stage.setTitle("Administrar participants");
		administrar_participants_stage.setResizable(false);
		administrar_participants_stage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		administrar_participants_stage.initOwner(PenPalsMain.main_scene.getWindow()); 
         
		administrar_participants_stage.show();
	}
	
	@FXML public void veure_administrador(MouseEvent event) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
		loader.setController(new ControllerVeureUsuari(grupActual.getAdministrador()));
		
		GridPane pantalla_administrador = loader.load();
		//GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
	
	@FXML public void veure_nota(ActionEvent event) throws IOException {
		GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
	
	@FXML public void crear_nota(ActionEvent event) throws IOException {
		GridPane crear_nota = FXMLLoader.load(getClass().getResource("/view/FXMLCrearNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(crear_nota);
	}

	
	
}
