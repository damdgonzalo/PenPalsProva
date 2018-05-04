package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import penpalsprova.ControlNota;


public class ControllerPantallaPrincipal implements Initializable {
	@FXML GridPane gridNotes;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ControlNota nota = new ControlNota();
		nota.setTitol("Titol");
		nota.setText("Cos de la nota jhaskdfhsdf");
		nota.setDataCreacio("Avui.");
		
		gridNotes.add(nota,0,0);
		//gridNotes.getChildren().add(1, nota);
	}
	
}