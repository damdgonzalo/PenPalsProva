package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import penpalsprova.PenPalsMain;

public class ControllerEditarPerfil {
	@FXML public void veure_usuari(ActionEvent event) throws IOException {
		//sGridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("/view/FXMLVeureUsuari.fxml"));


		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
		loader.setController(new ControllerVeureUsuari(ControllerVeureUsuari.usuariActual.getId(), false));
		
		GridPane pantalla_administrador = loader.load();
		
		//GridPane pantalla_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
}
