package penpalsprova;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class PenPalsMain extends Application {
	
	public static Stage stage;
	public static Scene main_scene;
	public static Scene login_scene;
	public static BorderPane border_pane_main;
     
     @Override
     public void start(Stage primary_stage) throws Exception {
    	 
          border_pane_main = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
          stage = primary_stage;
          
          GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
          border_pane_main.setCenter(pantalla_principal);
  		
          main_scene = new Scene(border_pane_main);
          main_scene.getStylesheets().add(PenPalsMain.class.getResource("main_style.css").toExternalForm());
          
          primary_stage.setTitle("PenPals");
          primary_stage.setScene(main_scene);
          primary_stage.show();
     }

     /**
      * @param args the command line arguments
      */
     public static void main(String[] args) {
          launch(args);
     }
     
}
