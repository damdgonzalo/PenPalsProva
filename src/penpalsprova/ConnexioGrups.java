package penpalsprova;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ConnexioGrups {

	private Connection conn;
	private Statement stmt;
	
	public ConnexioGrups() throws Exception {
		conn = Connexio.conn;
		stmt = Connexio.stmt;
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Retorna una llista amb el nom dels grups als qual pertany l'usuari connectat
	 * @return Llista amb els grups als que està l'usuari
	 */
	public List<String> getGrupsUsuari() throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT nom FROM \"Grups\" as g LEFT JOIN \"GrupsUsuaris\" as gu ON g.\"idGrup\"=gu.\"idGrup\" WHERE \"idUsuari\" ='"+ Connexio.getUsuari() + "'");
		
		List<String> contactes = new LinkedList<>();
		while (rs.next()) {
			contactes.add(rs.getString("nom"));
		}
		
		//stmt = conn.createStatement();
		
		return contactes;
	}
	
}
