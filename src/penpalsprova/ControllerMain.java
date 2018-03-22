package penpalsprova;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerMain implements Initializable {
	
	static Stage about_stage;
	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {}
	
	@FXML public void veure_nota(MouseEvent event) throws IOException {
		GridPane pantalla_veure_nota = FXMLLoader.load(getClass().getResource("FXMLVeureNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_nota);
	}
	
	@FXML public void veure_principal(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
	@FXML public void tencar_sessio(ActionEvent event) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLLogIn.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		
		main_stage.setScene(scene); 
	}
	
	@FXML public void veure_grup(MouseEvent event) throws IOException {
		GridPane pantalla_veure_grup = FXMLLoader.load(getClass().getResource("FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_veure_grup);
	}
     
     
	/**
	 * Obre en una finestra diferent a l'actual la pantalla "About" amb informació sobre el programa
	 * @param event
	 * @throws Exception
	 */
	@FXML public void obrir_about(ActionEvent event) throws Exception {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLAbout.fxml")); //finestra que volem obrir
    	 
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
	 * Surt del programa
	 */
	@FXML public void sortir_penpals() {
		Platform.exit();
		System.exit(0);
	}
}
