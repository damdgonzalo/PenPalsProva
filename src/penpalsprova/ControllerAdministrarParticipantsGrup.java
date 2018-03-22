package penpalsprova;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

public class ControllerAdministrarParticipantsGrup {

	@FXML public void veure_principal(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
		
		ControllerVeureGrup.administrar_participants_stage.close();
	}
	
	
}
