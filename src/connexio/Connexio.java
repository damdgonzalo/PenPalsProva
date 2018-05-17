package connexio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Grup;

public class Connexio {
	
	public static Connection conn;
	public static Statement stmt;
	private static String usuariConnectat;
	private static HashMap<Integer,Grup> grups;
	
	public Connexio(String ip, String port, String usuari, String contrasenya) throws Exception {
		Class.forName("org.postgresql.Driver");
		
		conn = DriverManager.getConnection("jdbc:postgresql://" + ip + ":" + port + "/penpals", usuari, contrasenya);
		conn.setAutoCommit(true);
		if (conn!=null) System.out.println("-> Connexió establerta amb la base de dades.");
		
		grups = new HashMap<>();
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
	
	public static void setGrups(List<Grup> llistaGrups) {
		for (Grup grupNou: llistaGrups) {
			grups.put(grupNou.getId(), grupNou);
		}
	}
	
	public static HashMap<Integer,Grup> getGrups() {return grups;}
	
	public static List<String> getNomGrups() {
		List<String> llistaNomsGrups = new LinkedList<>();
		
		for (Grup grup : grups.values()) llistaNomsGrups.add(grup.getNom());
		
		return llistaNomsGrups;
	}
	
	public static List<Integer> getIdGrups() {
		List<Integer> llistaId = new LinkedList<>();
		
		for (Integer id : grups.keySet()) llistaId.add(id);
		
		return llistaId;
	}
}