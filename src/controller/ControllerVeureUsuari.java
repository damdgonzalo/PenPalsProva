package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import penpalsprova.Connexio;
import penpalsprova.ConnexioContactes;
import penpalsprova.PenPalsMain;
import penpalsprova.Usuari;

public class ControllerVeureUsuari implements Initializable{
	Usuari usuariActual;
	
	@FXML Label idUsuari;
	@FXML Label nomUsuari;
	@FXML Label dataNaixement;
	@FXML Label correuElectronic;
	@FXML Label mobil;
	@FXML Label telefon;
	@FXML Label grupsEnComu;
	
	@FXML Button btEditarPerfil; //deixa editar el perfil si és el nostre
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ConnexioContactes connexioContactes = new ConnexioContactes();
			usuariActual = connexioContactes.getUsuari(usuariActual.getId());
			posarDades();
			
			if (usuariActual.getId().equals(Connexio.getUsuari())) btEditarPerfil.setVisible(true);
			else btEditarPerfil.setVisible(false);
			
		} catch (Exception ignored) {}

		
	}
	
	public ControllerVeureUsuari(String idUsuari) {
		usuariActual = new Usuari();
		usuariActual.setId(idUsuari);
	}
	
	
	public void posarDades() {
		idUsuari.setText(usuariActual.getId());
		nomUsuari.setText(usuariActual.getNom());
		dataNaixement.setText(usuariActual.getDataNaixement());
		correuElectronic.setText(usuariActual.getCorreu());
		mobil.setText(usuariActual.getMobil());
		telefon.setText(usuariActual.getTelefon());
	}
	
	
	@FXML public void veure_grup(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
	@FXML public void editar_perfil(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLEditarPerfil.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
}