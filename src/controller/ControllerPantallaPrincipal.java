package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import penpalsprova.ConnexioNotes;
import penpalsprova.Nota;
import penpalsprova.PenPalsMain;


public class ControllerPantallaPrincipal implements Initializable {
	
	ConnexioNotes connexioNotes;
	@FXML GridPane gridNotes;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		try {
			connexioNotes = new ConnexioNotes();
			
			carregarNotes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		
		/*ControlNota nota = new ControlNota();
		nota.setTitol("Titol");
		nota.setText("Cos de la nota jhaskdfhsdf");
		nota.setDataCreacio("Avui.");
		
		//gridNotes.add(nota,0,0);
		gridNotes.getChildren().add(0, nota);*/
	}
	
	private void carregarNotes() throws Exception {
		List<Nota> llistaNotes = connexioNotes.getNotes();
		
		for (int i=0; i<llistaNotes.size(); i++) {
			int idNota = llistaNotes.get(i).getId();
			
			GridPane vistaNota = FXMLLoader.load(getClass().getResource("/view/FXMLNotaMenu.fxml"));
			Label colorGrup = null;
			Label titolNota = null;
			Label textNota = null;
		
			//agafa cada element de la vista nota (color identificador del grup, titol i text)
			for (Node node : vistaNota.getChildren()) {
		        if (GridPane.getColumnIndex(node)!=null) {
		        	if (GridPane.getColumnIndex(node)==1) {
		        		colorGrup = (Label) node;
		        		colorGrup.setStyle("-fx-text-fill: " + llistaNotes.get(i).getColorNota());
		        	}
		        	else if (GridPane.getColumnIndex(node)==2) {
		        		titolNota = (Label) node;
		        		titolNota.setText(llistaNotes.get(i).getTitol());
		        	}
		        	else if (GridPane.getColumnIndex(node)==2) {
		        		textNota = (Label) node;
		        		textNota.setText(llistaNotes.get(i).getText());
		        	}
		        }
			}
			
			vistaNota.setOnMouseClicked(e->veureNota(e, idNota));
			
			gridNotes.add(vistaNota, 0, i);
		}
	}
	
	private void veureNota(Event e, int idNota) {
		try {
			System.out.println("Veient nota: " + idNota);
			GridPane pantalla_veure_nota = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
			PenPalsMain.border_pane_main.setCenter(pantalla_veure_nota);
		} catch (Exception ignored) {}
	}
}