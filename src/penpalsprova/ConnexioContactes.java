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
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	public List<String> getContactesUsuari(String usuari) throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT \"idUsuari1\" FROM \"ContactesUsuari\" WHERE \"idUsuari2\"='" + usuari + "'");
		
		List<String> contactes = new LinkedList<String>;
		
		while (rs.next()) {
			
		}
		return null;
	}
	
}
