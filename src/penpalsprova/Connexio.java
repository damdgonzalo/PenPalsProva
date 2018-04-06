package penpalsprova;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
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
		if (conn!=null) System.out.println("-> Connexió establerta amb la base de dades.");
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
	
	public static List<String> veureNotificacions() {
		List<String> llistaNotificacions = new LinkedList<>();

		try {
			stmt = conn.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT \"idUsuari1\" FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + Connexio.getUsuari() + "' AND mutual=false");		
	
		while (rs.next()) {
			llistaNotificacions.add(rs.getString("idUsuari1"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return llistaNotificacions;
	}
	
	

}
