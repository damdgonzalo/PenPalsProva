package controller;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import penpalsprova.ConnexioLogIn;
import penpalsprova.LogInMain;
import penpalsprova.PenPalsMain;

public class ControllerRegistrat {
	ConnexioLogIn connexio = ControllerLogIn.connexio;
	
	@FXML TextField correu;
	@FXML TextField usuari;
	@FXML TextField contrasenya1;
	@FXML TextField contrasenya2;
	
	@FXML Label missatgeError;
	
	/**
	 * Intenta afegir un compte nou a la base de dades
	 * @param e
	 * @throws Exception
	 */
	@FXML public void intentarRegistrarUsuari(ActionEvent e) throws Exception {
		
		String resposta = connexio.intentarRegistrarUsuari(correu.getText(), usuari.getText(), contrasenya1.getText(), contrasenya2.getText());
		
		if (resposta.equals("OK")) iniciarSessio();
		else missatgeError.setText(resposta);
	}
	
	private void iniciarSessio() throws Exception {
		BorderPane root = FXMLLoader.load(getClass().getResource("/view/FXMLMain.fxml")); //finestra que volem obrir
		PenPalsMain.border_pane_main = root;
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		
		//al centre mostrarà la pantalla principal
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
        root.setCenter(pantalla_principal);
		
		main_stage.setScene(scene);
	}
	
	
	@FXML private void veureLogIn(ActionEvent e) throws Exception {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLLogIn.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		LogInMain.main_scene = scene;
		
		main_stage.setScene(scene);
	}
}