package penpalsprova;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LogInMain extends Application {
	     
     @Override
     public void start(Stage stage) throws Exception {
          Parent border_pane_main = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
          
          GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
          //border_pane_main.setCenter(pantalla_principal);
  		
          Scene main_scene = new Scene(border_pane_main);
          main_scene.getStylesheets().add(PenPalsMain.class.getResource("main_style.css").toExternalForm());
          
          stage.setTitle("PenPals");
          stage.setScene(main_scene);
          stage.show();
     }

     /**
      * @param args the command line arguments
      */
     public static void main(String[] args) {
          launch(args);
     }
     
}