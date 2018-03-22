package penpalsprova;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerVeureGrup {
	
	static Stage administrar_participants_stage;

	@FXML public void administrar_participants(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLAdministrarParticipantsGrup.fxml")); //finestra que volem obrir
   	 
		Scene scene = new Scene(root);
		administrar_participants_stage = new Stage();
    	 
		administrar_participants_stage.setScene(scene);
		administrar_participants_stage.setTitle("Administrar participants");
		administrar_participants_stage.setResizable(false);
		administrar_participants_stage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		administrar_participants_stage.initOwner(PenPalsMain.main_scene.getWindow()); 
         
		administrar_participants_stage.show();
	}
	
	@FXML public void veure_administrador(MouseEvent event) throws IOException {
		GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("FXMLVeureUsuari.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
	
}
