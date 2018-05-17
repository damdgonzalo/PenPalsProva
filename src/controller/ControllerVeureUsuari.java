package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import connexio.Connexio;
import connexio.ConnexioContactes;
import connexio.ConnexioGrups;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Usuari;
import penpalsprova.PenPalsMain;

public class ControllerVeureUsuari implements Initializable{
	
	ConnexioGrups connexioGrups;
	static Usuari usuariActual;
	boolean tornarAPantallaPrincipal; //per saber si s'ha fet click a la pantalla d'inici o a un altre lloc (desde un grup)
	
	@FXML Label idUsuari;
	@FXML Label nomUsuari;
	@FXML Label dataNaixement;
	@FXML Label correuElectronic;
	@FXML Label mobil;
	@FXML Label telefon;
	@FXML Label grupsEnComu;
	@FXML Label grupsEnComuTitol;
	
	@FXML Button btEditarPerfil; //deixa editar el perfil si és el nostre
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			connexioGrups = new ConnexioGrups(); //per veure els grups en comú
			
			ConnexioContactes connexioContactes = new ConnexioContactes();
			usuariActual = connexioContactes.getUsuari(usuariActual.getId());
			
			if (usuariActual.getId().equals(Connexio.getUsuari())) {
				btEditarPerfil.setVisible(true);
				grupsEnComu.setVisible(false);
				grupsEnComuTitol.setVisible(false);
			}
			
			else {
				btEditarPerfil.setVisible(false);
				grupsEnComu.setVisible(true);
				grupsEnComuTitol.setVisible(true);
			}
			
			posarDades();
			
		} catch (Exception ignored) {ignored.printStackTrace();}
		
	}
	
	public ControllerVeureUsuari(String idUsuari, boolean tornarAPantallaPrincipal) {
		usuariActual = new Usuari();
		usuariActual.setId(idUsuari);
		this.tornarAPantallaPrincipal = tornarAPantallaPrincipal;
	}
	
	
	public void posarDades() throws Exception {
		idUsuari.setText(usuariActual.getId());
		nomUsuari.setText(usuariActual.getNom());
		dataNaixement.setText(usuariActual.getDataNaixement());
		correuElectronic.setText(usuariActual.getCorreu());
		mobil.setText(usuariActual.getMobil());
		telefon.setText(usuariActual.getTelefon());
		
		if (grupsEnComu.isVisible()) {
			List<String> llistaGrupsEnComu = connexioGrups.getGrupsEnComuAmb(usuariActual.getId());
			String textGrupsEnComu = "";
			
			if (llistaGrupsEnComu.size() > 0) {
				for (int i=0; i<llistaGrupsEnComu.size(); i++) {
					if (i==llistaGrupsEnComu.size()-1) textGrupsEnComu += llistaGrupsEnComu.get(i);
					else textGrupsEnComu += llistaGrupsEnComu.get(i) + ", s";
				}
			}
			
			else grupsEnComu.setText("No teniu cap grup en comú");
			
			System.out.println("Grups en comú: --" + textGrupsEnComu + "--");
			grupsEnComu.setText(textGrupsEnComu);
		}
	}
	
	
	@FXML public void tornarEnrere(ActionEvent event) throws IOException {
		
		if (tornarAPantallaPrincipal) {
			GridPane pantallaAnterior = FXMLLoader.load(getClass().getResource("/view/FXMLPantallaPrincipal.fxml"));
			PenPalsMain.border_pane_main.setCenter(pantallaAnterior);
		}
		else {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FXMLVeureGrup.fxml"));
			loader.setController(new ControllerVeureGrup(ControllerVeureGrup.grupActual));
			
			GridPane pantallaAnterior = loader.load();
			PenPalsMain.border_pane_main.setCenter(pantallaAnterior);

		}
		
	}
	
	@FXML public void editar_perfil(ActionEvent event) throws IOException {
		GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("/view/FXMLEditarPerfil.fxml"));
		PenPalsMain.border_pane_main.setCenter(pantalla_principal);
	}
}