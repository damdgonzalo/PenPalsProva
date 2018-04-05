package penpalsprova;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LogInMain extends Application {
	
	static Scene main_scene;
	static Stage stage;
	public static Connexio connexio;
	     
     @Override
     public void start(Stage stage) throws Exception {
    	 connexio = new Connexio("localhost", "5432", "postgres", "root");
    	 
    	 LogInMain.stage = stage;
    	 Parent login_root = FXMLLoader.load(getClass().getResource("FXMLLogIn.fxml"));
            		
    	 main_scene = new Scene(login_root);
          
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