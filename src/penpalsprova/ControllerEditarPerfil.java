package penpalsprova;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ControllerEditarPerfil {
	@FXML public void veure_usuari(ActionEvent event) throws IOException {
		GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("FXMLVeureUsuari.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
}
