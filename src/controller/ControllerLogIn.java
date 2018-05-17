package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connexio.Connexio;
import connexio.ConnexioLogIn;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import penpalsprova.LogInMain;
import penpalsprova.PenPalsMain;

public class ControllerLogIn implements Initializable {
	
	static ConnexioLogIn connexio;
	static Stage contrasenyaOblidadaStage;

	@FXML Label missatgeError;
	@FXML TextField usuari;
	@FXML TextField contrasenya;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			connexio = new ConnexioLogIn();
		} catch (Exception ignored) {}
	}
	
//----------------------------------------------------------------------------------------------------------------------	
	
	/**
	 * Pregunta al servidor si l'usuari i contrasenya són correctes i, si ho són, permet el log in.
	 * Si no ho són, mostra per pantalla un missatge d'error
	 * @param event Click
	 * @throws Exception
	 */
	@FXML public void iniciar_sessio(ActionEvent event) throws Exception {
		String respostaLogIn = connexio.intentarLogIn(usuari.getText(), contrasenya.getText());
		
		if (respostaLogIn.equals("OK")) {
			Connexio.setUsuariConnectat(usuari.getText());
			
			BorderPane root = FXMLLoader.load(getClass().getResource("/view/FXMLMain.fxml")); //finestra que volem obrir
			PenPalsMain.border_pane_main = root;
			
			Stage main_stage = LogInMain.stage;
			Scene scene = new Scene(root);
			PenPalsMain.main_scene = scene;
			
			//al centre mostrar� la pantalla principal
			GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
	        root.setCenter(pantalla_principal);
			
			main_stage.setScene(scene);
		}
		else missatgeError.setText(respostaLogIn);
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Obre la pantalla per registrar un usuari
	 * @param e Click
	 * @throws Exception
	 */
	@FXML public void registrarUsuari(ActionEvent e) throws Exception {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLRegistrat.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		LogInMain.main_scene = scene;
		
		main_stage.setScene(scene);
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Obre un PopUp on s'introdueix el correu de l'usuari per recuperar la contrasenya
	 * @param e Click
	 * @throws IOException
	 */
	@FXML public void contrasenyaOblidada(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLContrasenyaOblidada.fxml")); //finestra que volem obrir
	   	 
		Scene scene = new Scene(root);
		contrasenyaOblidadaStage = new Stage();
    	 
		contrasenyaOblidadaStage.setScene(scene);
		contrasenyaOblidadaStage.setTitle("Recuperaci� de la contrasenya");
		contrasenyaOblidadaStage.setResizable(false);
		contrasenyaOblidadaStage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		contrasenyaOblidadaStage.initOwner(LogInMain.main_scene.getWindow()); 
         
		contrasenyaOblidadaStage.show();
	}
}
