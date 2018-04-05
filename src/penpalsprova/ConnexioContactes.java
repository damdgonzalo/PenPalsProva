package penpalsprova;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

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
	 * Retorna una llista amb el nom dels contactes que t� un usuari
	 * @param usuari ID de l'usuari del qual volem veure els contactes
	 * @return Llista amb els ID d'usuari dels contactes
	 * @throws Exception
	 */
	public List<String> getContactesUsuari() throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"idUsuari1\" FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + Connexio.getUsuari() + "'");
		
		List<String> contactes = new LinkedList<>();
		while (rs.next()) {
			contactes.add(rs.getString("idUsuari1"));
		}
		
		stmt = conn.createStatement();
		ResultSet rs2 = stmt.executeQuery("SELECT \"idUsuari2\" FROM \"ContactesUsuari\" WHERE \"idUsuari1\"='" + Connexio.getUsuari() + "'");
				
		while (rs2.next()) {
			contactes.add(rs2.getString("idUsuari2"));
		}
		
		return contactes;
	}
	
}
