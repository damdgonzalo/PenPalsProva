package penpalsprova;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ConnexioContactes {

	private Connection conn;
	private Statement stmt;
	
	public ConnexioContactes() throws Exception {
		conn = Connexio.conn;
		stmt = Connexio.stmt;
		System.out.println("Connexio contactes");
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Retorna una llista amb el nom dels contactes que té un usuari
	 * @param usuari ID de l'usuari del qual volem veure els contactes
	 * @return Llista amb els ID d'usuari dels contactes
	 * @throws Exception
	 */
	public List<String> getContactesUsuari() throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"idUsuari1\" FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + Connexio.getUsuari() + "' AND mutual=true");
		
		List<String> contactes = new LinkedList<>();
		while (rs.next()) {
			contactes.add(rs.getString("idUsuari1"));
		}
		
		stmt = conn.createStatement();
		ResultSet rs2 = stmt.executeQuery("SELECT \"idUsuari2\" FROM \"ContactesUsuari\" WHERE \"idUsuari1\"='" + Connexio.getUsuari() + "' AND mutual=true");
				
		while (rs2.next()) {
			contactes.add(rs2.getString("idUsuari2"));
		}
		
		return contactes;
	}
	
	
	/**
	 * Envia a un usuari una sol·licitud d'amistat
	 * @param idUsuari ID de l'usuari al que es vol agregar com a contacte
	 * @throws Exception
	 */
	public void enviarSolicitudAmistat(String idUsuari) throws Exception {
		stmt = conn.createStatement();
		stmt.executeQuery("INSERT INTO \"ContactesUsuari\" VALUES ('" + Connexio.getUsuari() + "','" + idUsuari + "',false)");
	}
	
	
	/**
	 * Retorna una llista amb sol·licituds d'amistat
	 * @return Llista amb el nom de qui ha enviat la sol·licitud
	 */
	public List<String> veureSolicitudsAmistat() {
		List<String> llistaNotificacions = new LinkedList<>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT \"idUsuari1\" FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + Connexio.getUsuari() + "' AND mutual=false");		
	
		while (rs.next()) {
			llistaNotificacions.add(rs.getString("idUsuari1"));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
		return llistaNotificacions;
	}
	
	public void acceptarSolicitudAmistat(String usuari) {
		System.out.println("--" + usuari + "--");
		try {
			stmt = conn.createStatement();
			
			stmt.executeQuery("UPDATE \"ContactesUsuari\" SET \"mutual\"=true WHERE \"mutual\"=false AND \"idUsuari2\"='" + Connexio.getUsuari()
			+ "' AND \"idUsuari1\"='" + usuari + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void declinarSolicitudAmistat(String usuari) {
		try {
			stmt = conn.createStatement();
			
			stmt.executeQuery("DELETE FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + Connexio.getUsuari()+ "' AND \"idUsuari1\"='" + usuari + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
