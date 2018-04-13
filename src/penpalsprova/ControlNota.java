package penpalsprova;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ControlNota extends BorderPane {

	@FXML private Text titol;
	@FXML private Text dataCreacio;
	@FXML private Text text;
	@FXML private VBox colorIdentificador;
	
	public ControlNota() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ControlNota.fxml"));
		loader.setRoot(this);
		loader.setController(this);
	}
	
	public String getTitol() {return titol.getText();}
	public void setTitol(String titol) {this.titol.setText(titol);}
	
	public String getDataCreacio() {return dataCreacio.getText();}
	public void setDataCreacio(String dataCreacio) {this.dataCreacio.setText(dataCreacio);}
	
	public String getText() {return text.getText();}
	public void setText(String text) {this.text.setText(text);
	
	}
}
