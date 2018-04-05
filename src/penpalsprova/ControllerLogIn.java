package penpalsprova;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

import penpalsprova.ConnexioLogIn;

public class ControllerLogIn implements Initializable {
	
	ConnexioLogIn connexio;
	static Stage contrasenyaOblidadaStage;

	@FXML Label missatgeError;
	@FXML TextField usuari;
	@FXML TextField contrasenya;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			connexio = new ConnexioLogIn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML public void iniciar_sessio(ActionEvent event) throws Exception {
		String respostaLogIn = connexio.intentarLogIn(usuari.getText(), contrasenya.getText());
		
		if (respostaLogIn.equals("OK")) {
			
			BorderPane root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml")); //finestra que volem obrir
			PenPalsMain.border_pane_main = root;
			
			Stage main_stage = LogInMain.stage;
			Scene scene = new Scene(root);
			PenPalsMain.main_scene = scene;
			
			//al centre mostrarà la pantalla principal
			GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
	        root.setCenter(pantalla_principal);
			
			main_stage.setScene(scene);
		}
		
		else missatgeError.setText(respostaLogIn);
	}
	
	@FXML public void registrarUsuari(ActionEvent e) throws Exception {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLRegistrat.fxml")); //finestra que volem obrir
		
		Stage main_stage = LogInMain.stage;
		Scene scene = new Scene(root);
		PenPalsMain.main_scene = scene;
		LogInMain.main_scene = scene;
		
		main_stage.setScene(scene);
	}
	
	@FXML public void contrasenyaOblidada(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLContrasenyaOblidada.fxml")); //finestra que volem obrir
	   	 
		Scene scene = new Scene(root);
		contrasenyaOblidadaStage = new Stage();
    	 
		contrasenyaOblidadaStage.setScene(scene);
		contrasenyaOblidadaStage.setTitle("Recuperació de la contrasenya");
		contrasenyaOblidadaStage.setResizable(false);
		contrasenyaOblidadaStage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		contrasenyaOblidadaStage.initOwner(LogInMain.main_scene.getWindow()); 
         
		contrasenyaOblidadaStage.show();
	}
}
