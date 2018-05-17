package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import connexio.ConnexioGrups;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Grup;
import penpalsprova.PenPalsMain;

public class ControllerAdministrarParticipantsGrup implements Initializable {

	@FXML VBox llistaParticipants;
	@FXML Button btAfegirParticipant;
	
	Grup grupActual;
	ConnexioGrups connexioGrups;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			grupActual = ControllerVeureGrup.grupActual;
			connexioGrups = new ConnexioGrups();
			carregarLlista();
		} catch (Exception ignored) {}
	}
	
	
	
	/**
	 * Torna a la vista del grup
	 * @param event
	 * @throws IOException
	 */
	@FXML public void veure_principal(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		loader.setController(new ControllerVeureGrup(ControllerVeureGrup.grupActual));
		
		GridPane pantalla_grup = loader.load();
		
		//GridPane pantalla_grup = FXMLLoader.load(getClass().getResource("/view/FXMLVeureGrup.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_grup);
		
		ControllerVeureGrup.administrar_participants_stage.close();
	}
	
	
	
	/**
	 * Mostra un PopUp per introduïr l'ID de l'usuari que es vol afegir al grup
	 * @param e
	 * @throws Exception
	 */
	@FXML public void afegirParticipant(Event e) throws Exception {
		
		//Components del PopOver
		TextField nomUsuari = new TextField();   
		nomUsuari.setStyle("-fx-font: 15 arial;");
		nomUsuari.setPromptText("ID de l'usuari");
		
		nomUsuari.setMinHeight(40);
		nomUsuari.setMaxHeight(40);
		
		Button btAfegir = new Button("Afegir al grup");
		btAfegir.setMinHeight(40);
		btAfegir.setMaxHeight(40);
		
		btAfegir.setOnAction(new EventHandler() {

			@Override
			public void handle(Event event) {
				connexioGrups.afegirParticipantAlGrup(nomUsuari.getText(), grupActual.getId());
				
			}
		});
	
		HBox hboxAfegirParticipant = new HBox();
		hboxAfegirParticipant.setPadding(new Insets(20,20,20,20));
		hboxAfegirParticipant.getChildren().addAll(nomUsuari, btAfegir);
		
		PopOver popover = new PopOver();
		popover.setContentNode(hboxAfegirParticipant);
		popover.setArrowLocation(PopOver.ArrowLocation.LEFT_CENTER);
		popover.show(btAfegirParticipant);
	}
	
	
	
	/**
	 * Mostra per pantalla la llista amb els participants del grup que s'està administrant
	 */
	public void carregarLlista() {
		
		try {
			for (int i=0; i<grupActual.getParticipants().size(); i++) {
				HBox vistaParticipant = FXMLLoader.load(getClass().getResource("/view/FXMLAdministrarParticipantsGrupRow.fxml"));
				
				Label nomParticipant = null;
				ImageView eliminarParticipant = null;
							
				nomParticipant = (Label) vistaParticipant.getChildren().get(1);
				nomParticipant.setText(grupActual.getParticipants().get(i));
				
				eliminarParticipant = (ImageView) vistaParticipant.getChildren().get(2);
			
				int idEliminar = i;
				eliminarParticipant.setOnMouseClicked(e-> eliminarParticipant(e, idEliminar));
				
				llistaParticipants.getChildren().add(vistaParticipant);
			}
			
		} catch (Exception ignored) {ignored.printStackTrace();}
	}
	
	
	
	/**
	 * Elimina un participant seleccionat d'un grup
	 * @param e Event cick a un participant
	 * @param idUsuari ID del participant que es vol eliminar del grup
	 */
	public void eliminarParticipant(Event e, int idUsuari) {
		try {
			connexioGrups.eliminarParticipantGrup(grupActual.getParticipants().get(idUsuari), grupActual.getId());
			grupActual.getParticipants().remove(idUsuari);
			llistaParticipants.getChildren().remove(idUsuari);
		} catch (Exception ignored) {ignored.printStackTrace();}
	}
}
