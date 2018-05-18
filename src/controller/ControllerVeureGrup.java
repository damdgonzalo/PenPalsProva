package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import connexio.Connexio;
import connexio.ConnexioNotes;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Grup;
import model.Nota;
import penpalsprova.PenPalsMain;

public class ControllerVeureGrup implements Initializable {
	
	static Stage administrar_participants_stage;
	static String administrador;
	static Grup grupActual;
	
	@FXML GridPane gridNotes;
	
	@FXML Label nomGrup;
	@FXML Label administradorGrup;
	@FXML Label numeroParticipants;
	@FXML Label colorGrup;
	
	@FXML Button btAdministrarParticipants; //només visible si sóm qui ha del grup
	
	public ControllerVeureGrup(Grup grupActual) {
		ControllerVeureGrup.grupActual = grupActual;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		
		
		nomGrup.setText(grupActual.getNom());
		administradorGrup.setText(grupActual.getAdministrador());
		numeroParticipants.setText(grupActual.getParticipants().size() + " participants");
		colorGrup.setStyle("-fx-background-color: " + grupActual.getColorGrup());
		
		if (grupActual.getAdministrador().equals(Connexio.getUsuari())) btAdministrarParticipants.setVisible(true);
		else btAdministrarParticipants.setVisible(false);
		
		carregarNotes();
	}
	

	/**
	 * Mostra per pantalla una llista amb les notes que hi ha al grup que estem veient
	 */
	public void carregarNotes() {
		try {
			ConnexioNotes connexio = new ConnexioNotes();
			List<Nota> llistaNotes = connexio.getNotesDelGrup(grupActual);
						
			for (int i=0; i<llistaNotes.size(); i++) {
				Nota notaActual = llistaNotes.get(i);
				
				GridPane vistaNota = FXMLLoader.load(getClass().getResource("/view/FXMLNotaMenu.fxml"));
				Label colorGrup = null;
				Label titolNota = null;
				Label textNota = null;
			
				//agafa cada element de la vista nota (color identificador del grup, titol i text)
				for (Node node : vistaNota.getChildren()) {
			        if (GridPane.getColumnIndex(node)!=null) {
			        	if (GridPane.getColumnIndex(node)==1) {
			        		colorGrup = (Label) node;
			        		colorGrup.setStyle("-fx-text-fill: " + llistaNotes.get(i).getGrup().getColorGrup());
			        	}
			        	else if (GridPane.getColumnIndex(node)==2) {
			        		titolNota = (Label) node;
			        		titolNota.setText(llistaNotes.get(i).getTitol() + ".");
			        	}
			        	else if (GridPane.getColumnIndex(node)==2) {
			        		textNota = (Label) node;
			        		textNota.setText(llistaNotes.get(i).getText());
			        	}
			        }
				}
				
				vistaNota.setOnMouseClicked(e->veureNota(e, notaActual));
				
				gridNotes.add(vistaNota, 0, i);
			}
		} catch (Exception ignored) {}
	}
	
	
	
	/**
	 * Mostra els detalls d'una nota a la que s'ha fet click
	 * @param e Click
	 * @param nota Nota que es vol visualitzar
	 */
	private void veureNota(Event e, Nota nota) {
		try {
			ControllerPantallaPrincipal.notaClickada = nota; //perque quan es fa click a una nota,  ControllerVeureNota pugui saber quina nota ha de mostrar
			GridPane pantalla_veure_nota = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
			PenPalsMain.border_pane_main.setCenter(pantalla_veure_nota);
			
		} catch (Exception ignored) {}
	}
	
	@FXML public void administrar_participants(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("/view/FXMLAdministrarParticipantsGrup.fxml")); //finestra que volem obrir
   	 
		Scene scene = new Scene(root);
		administrar_participants_stage = new Stage();
    	 
		administrar_participants_stage.setScene(scene);
		administrar_participants_stage.setTitle("Administrar participants");
		administrar_participants_stage.setResizable(false);
		administrar_participants_stage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		administrar_participants_stage.initOwner(PenPalsMain.main_scene.getWindow()); 
         
		administrar_participants_stage.show();
	}
	
	@FXML public void veure_administrador(MouseEvent event) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
		loader.setController(new ControllerVeureUsuari(grupActual.getAdministrador(), false));
		
		GridPane pantalla_administrador = loader.load();
		//GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("/view/FXMLVeureUsuari.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
	
	@FXML public void veure_nota(ActionEvent event) throws IOException {
		GridPane pantalla_administrador = FXMLLoader.load(getClass().getResource("/view/FXMLVeureNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_administrador);
	}
	
	@FXML public void crear_nota(ActionEvent event) throws IOException {
		GridPane crear_nota = FXMLLoader.load(getClass().getResource("/view/FXMLCrearNota.fxml"));
		PenPalsMain.border_pane_main.setCenter(crear_nota);
	}
	
}
