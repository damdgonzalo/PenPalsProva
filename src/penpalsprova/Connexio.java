package penpalsprova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Connexio {
	
	public static Connection conn;
	public static Statement stmt;
	private static String usuariConnectat;
	
	public Connexio(String ip, String port, String usuari, String contrasenya) throws Exception {
		Class.forName("org.postgresql.Driver");
		
		conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/penpals", usuari, contrasenya);
		conn.setAutoCommit(true);
		if (conn!=null) System.out.println("-> Connexi� establerta amb la base de dades.");
		else System.out.println("baiabaia");
	}
	
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//----------------------------------------------------------------------------------------------------------------------	
	
	public static void setUsuariConnectat(String usuariConnectat) {
		Connexio.usuariConnectat = usuariConnectat;
	}
	
	public static String getUsuari() {
		return Connexio.usuariConnectat;
	}
	
	

}
