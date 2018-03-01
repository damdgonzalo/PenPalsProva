package penpalsprova;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ControllerLogIn implements Initializable {
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML public void iniciar_sessio(ActionEvent event) throws IOException {
		BorderPane root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml")); //finestra que volem obrir
		PenPalsMain.border_pane_main = root;
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
        root.setCenter(pantalla_principal);
		
		main_stage.setScene(scene);
		        
	}
}
