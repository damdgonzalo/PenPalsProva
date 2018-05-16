package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import penpalsprova.PenPalsMain;

public class ControllerCrearNota {

	@FXML public void veure_grup(Event event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		loader.setController(new ControllerVeureGrup(ControllerVeureGrup.grupActual));
		
		GridPane pantalla_grup = loader.load();
		
		//GridPane pantalla_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_grup);
	}
	
	/*@FXML public void veure_grup(MouseEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}*/
}
