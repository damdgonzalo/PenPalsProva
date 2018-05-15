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
	public List<Grup> getGrupsUsuari() throws Exception {
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT g.*, gu.\"color\" FROM \"Grups\" as g LEFT JOIN \"GrupsUsuaris\" as gu ON g.\"idGrup\"=gu.\"idGrup\" WHERE \"idUsuari\" ='"+ Connexio.getUsuari() + "'");
		
		List<Grup> grups = new LinkedList<>();
		while (rs.next()) {
			Grup grupNou = new Grup();
			grupNou.setId(Integer.parseInt(rs.getString("idGrup")));
			grupNou.setNom(rs.getString("nom"));
			grupNou.setAdministrador(rs.getString("administrador"));
			grupNou.setDataCreacio(rs.getString("dataCreacio"));
			grupNou.setColorGrup(rs.getString("color"));
			
			//llista de participants
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery("SELECT \"idUsuari\" FROM \"GrupsUsuaris\" WHERE \"idGrup\"=" + grupNou.getId());
			while (rs2.next()) grupNou.getParticipants().add(rs2.getString("idUsuari"));
			rs2.close();
			stmt2.close();
			
			grups.add(grupNou);
		}
		
		rs.close();
				
		return grups;
	}
	
	public static Grup getGrup(int idGrup) {
		return Connexio.getGrups().get(idGrup);
	}
	
	public static void getGrups() {
		
	}
	
}
