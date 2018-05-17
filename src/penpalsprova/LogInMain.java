package penpalsprova;

import connexio.Connexio;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class LogInMain extends Application {
	
	public static Scene main_scene;
	public static Stage stage;
	public static Connexio connexio;
	     
     @Override
     public void start(Stage stage) throws Exception {
    	 connexio = new Connexio("localhost", "5432", "postgres", "root");
    	 
    	 LogInMain.stage = stage;
    	 Parent login_root = FXMLLoader.load(getClass().getResource("/view/FXMLLogIn.fxml"));
            		
    	 main_scene = new Scene(login_root);
          
    	 stage.setTitle("PenPals");
    	 stage.setScene(main_scene);
    	 stage.show(); //comença mostrant la pantalla de log in
     }
     

     public static void main(String[] args) {
          launch(args);
     }  
}