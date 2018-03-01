package penpalsprova;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class ControllerVeureNota {

	@FXML public void veure_principal(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
}
