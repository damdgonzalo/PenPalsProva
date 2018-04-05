package penpalsprova;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.validator.routines.EmailValidator;

import penpalsprova.Connexio;

public class ConnexioLogIn {
	private Connection conn;
	private Statement stmt;
	
	public ConnexioLogIn() throws Exception {
		conn = Connexio.conn;
		stmt = Connexio.stmt;
	}
	
<<<<<<< HEAD
	public String getContrasenyaPerCorreu(String correu) throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"contrasenya\" FROM \"Usuaris\" WHERE \"correu\"='" + correu + "'");
		rs.next();
		
		return rs.getString("contrasenya");
=======
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
   	 	try {
			connexio = new Connexio("192.168.17.219", "5432", "postgres", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
>>>>>>> 958fa56c2cccb27990ec9bf3e8ea250621f38643
	}
	
	/**
	 * Comprova si un usuari pot fer o no login, i retorna un missatge indicant el resultat.
	 * Es pot fer login quan l'ID d'usuari existeix i la contrasenya est� b�.
	 * @param usuari ID de l'usuari que vol fer login
	 * @param contrasenya Contrasenya que l'usuari ha escrit
	 * @return "OK" si l'usuari existeix i la contrasenya �s correcta. Sino, retorna un missatge indicant perqu� no es pot fer login.
	 * @throws SQLException
	 */
	public String intentarLogIn(String usuari, String contrasenya) throws SQLException {
		if (!usuariExisteix(usuari)) return "L'usuari no existeix.";
		
<<<<<<< HEAD
		if (!contrasenyaEsCorrecte(usuari, contrasenya)) return "La contrasenya �s incorrecte.";
		
		return "OK";
	}
	
	/**
	 * Comprova si un usuari t� les dades correctes per registrar-se o no, i retorna un missatge indicant el resultat.
	 * @param correu Correu de l'usuari que es vol registrar
	 * @param usuari ID de l'usuari que es vol registrar
	 * @param contrasenya1 Contrasenya per el nou compte
	 * @param contrasenya2 Contrasenya per el nou compte
	 * @return "OK" si el correu no t� associat cap compte, si l'ID d'usuari �s v�lid, i si les contrasenyes coincideixen.</br>
	 * 		   En cas contrari, retorna un missatge amb informaci� sobre errors al registrar-se.
	 * @throws SQLException
	 */
	public String intentarRegistrarUsuari(String correu, String usuari, String contrasenya1, String contrasenya2) throws SQLException {
		String missatgeSortida = "OK";
		
		if (correu.equals("") || usuari.equals("") || contrasenya1.equals("") || contrasenya2.equals("")) missatgeSortida = "No poden haver camps buits.";
		else if (!correuEsValid(correu)) missatgeSortida = "El correu introdu�t no �s v�lid.";
		else if (correuExisteix(correu)) missatgeSortida = "Ja ha hi ha un compte creat amb aquest correu."; 
		else if (usuariExisteix(usuari)) missatgeSortida = "Aquest nom d'usuri ja est� en us.";
		else if (!usuariEsValid(usuari)) missatgeSortida = "El nom d'usuari ha de comen�ar per una lletra i contenir nom�s entre 3 i 20 car�cters alfanum�rics.";
		else if (!contrasenyesCoincideixen(contrasenya1, contrasenya2)) missatgeSortida = "Les contrasenyes no coincideixen.";
		
		else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
			LocalDate localDate = LocalDate.now();
=======
		if (respostaLogIn.equals("OK")) {
			
			BorderPane root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml")); //finestra que volem obrir
			PenPalsMain.border_pane_main = root;
			
			Stage main_stage = LogInMain.stage;
			Scene scene = new Scene(root);
			PenPalsMain.main_scene = scene;
			
			//al centre mostrar� la pantalla principal
			GridPane pantalla_principal = FXMLLoader.load(getClass().getResource("FXMLPantallaPrincipal.fxml"));
	        root.setCenter(pantalla_principal);
>>>>>>> 958fa56c2cccb27990ec9bf3e8ea250621f38643
			
			stmt = conn.createStatement();
			String query = "INSERT INTO \"Usuaris\" (\"correu\",\"idUsuari\",\"contrasenya\",\"dataRegistre\")"
					     + " VALUES ('" + correu + "','" + usuari + "','" + contrasenya1 + "','" + dtf.format(localDate) + "')";
			stmt.executeQuery(query);
		}
		
		return missatgeSortida;
	}
	
//---------------------------------------------------------------------------------------------------------------------	
	
	/**
	 * Comproba si l'ID d'un usuari t� el format correcte.
	 * @param usuari ID d'un usuari a verificar
	 * @return TRUE si l'ID �s alfanum�ric (sense car�cters especials), comen�a per una lletra i t� entre 3 i 20 car�cters
	 */
	private boolean usuariEsValid(String usuari) {
		return usuari.matches("^[a-zA-Z][a-zA-Z0-9]{2,19}");
	}
	
	/**
	 * Comproba si un correu existeix.
	 * @param correu Correu a verificar
	 * @return TRUE si el correu existeix
	 */
	private boolean correuEsValid(String correu) {
		return EmailValidator.getInstance().isValid(correu);
	}
	
	
	/**
	 * Retorna TRUE si un usuari existeix a la base de dades
	 * @param usuari ID de l'usuari a verificar
	 * @return TRUE si 'usuari' es troba a la base de dades
	 * @throws SQLException
	 */
	private boolean usuariExisteix(String usuari) throws SQLException {
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM \"Usuaris\" WHERE \"idUsuari\"='" + usuari + "'");
		rs.next();
		
		return (rs.getInt("count")!=0) ? true:false;
	}
	
	
	/**
	 * Comprova que no hi hagi cap compte creat amb un correu espec�fic
	 * @param correu Correu a comprovar
	 * @return TRUE si el correu ja t� associat un compte (ja existeix a la base de dades)
	 * @throws SQLException
	 */
	private boolean correuExisteix(String correu) throws SQLException {
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM \"Usuaris\" WHERE \"correu\"='" + correu + "'");
		rs.next();
		
		return (rs.getInt("count")!=0) ? true:false;
	}
	
	
	/**
	 * Comprova si dos contrasenyes s�n iguals
	 * @param contrasenya1 Contrasenya 1
	 * @param contrasenya2 Contrasenya 2
	 * @return TRUE si les dos contrasenyes coincideixen
	 */
	private boolean contrasenyesCoincideixen(String contrasenya1, String contrasenya2) {
		return (contrasenya1.equals(contrasenya2)) ? true : false;
	}
	
<<<<<<< HEAD
	
	/**
	 * Retorna TRUE si una contrasenya introdu�da per un usuari �s correcte.
	 * @param usuari ID de l'usuari
	 * @param contrasenya Contrasenya que ha escrit l'usuari
	 * @return TRUE si 'contrasenya' �s la mateixa que l'usuari t� assignada a la base de dades
	 * @throws SQLException
	 */
	private boolean contrasenyaEsCorrecte(String usuari, String contrasenya) throws SQLException {
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"contrasenya\" FROM \"Usuaris\" WHERE \"idUsuari\"='" + usuari + "'");
		rs.next();
		
		String contrasenyaCorrecta = rs.getString("contrasenya");
		return (contrasenyaCorrecta.equals(contrasenya)) ? true:false;
=======
	@FXML public void contrasenyaOblidada(ActionEvent e) throws IOException {
		GridPane root = FXMLLoader.load(getClass().getResource("FXMLContrasenyaOblidada.fxml")); //finestra que volem obrir
	   	 
		Scene scene = new Scene(root);
		contrasenyaOblidadaStage = new Stage();
    	 
		contrasenyaOblidadaStage.setScene(scene);
		contrasenyaOblidadaStage.setTitle("Recuperaci� de la contrasenya");
		contrasenyaOblidadaStage.setResizable(false);
		contrasenyaOblidadaStage.initModality(Modality.WINDOW_MODAL); //impedeix que es clicki la finestra pare
		contrasenyaOblidadaStage.initOwner(LogInMain.main_scene.getWindow()); 
         
		contrasenyaOblidadaStage.show();
>>>>>>> 958fa56c2cccb27990ec9bf3e8ea250621f38643
	}
}
