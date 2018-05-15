package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import penpalsprova.Nota;
import penpalsprova.PenPalsMain;

public class ControllerVeureNota implements Initializable {
	
	Nota notaActual;
	
	@FXML Label titolNota;
	@FXML Label grupNota;
	@FXML Label dataCreacio;
	@FXML Label dataUltimaModificacio;
	@FXML Label textNota;
	
	
	/*public ControllerVeureNota(Nota notaActual) {
		this.notaActual = notaActual;
	}*/
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		notaActual = ControllerPantallaPrincipal.notaClickada;
		posarDades();
	}
	
	
	private void posarDades() {
		titolNota.setText(notaActual.getTitol());
		grupNota.setText(notaActual.getGrup().getNom());
		dataCreacio.setText("Data publicació: " + notaActual.getDataPublicacio());
		dataUltimaModificacio.setText("Última modificació: " + notaActual.getDataUltModificacio());
		textNota.setText(notaActual.getText());
	}
	

	@FXML public void veure_principal(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
	
	@FXML public void veureGrup(Event event) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		loader.setController(new ControllerVeureGrup(notaActual.getGrup()));
		
		GridPane pantalla_grup = loader.load();
		
		//GridPane pantalla_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_grup);
	}
}
