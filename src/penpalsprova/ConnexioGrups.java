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
			ResultSet rs2 = stmt2.executeQuery("SELECT \"idUsuari\" FROM \"GrupsUsuaris\" WHERE \"idGrup\"=" + grupNou.getId() + "AND \"idUsuari\"!='" + grupNou.getAdministrador() + "'");
			while (rs2.next()) grupNou.getParticipants().add(rs2.getString("idUsuari"));
			rs2.close();
			stmt2.close();
			
			grups.add(grupNou);
		}
		
		rs.close();
				
		return grups;
	}
	
	
	
	/**
	 * Retorna un grup donada la seva ID per paràmetres
	 * @param idGrup ID del grup que volem obtenir
	 * @return Grup
	 */
	public static Grup getGrup(int idGrup) {
		return Connexio.getGrups().get(idGrup);
	}
	
	
	
	/**
	 * Retorna una llista amb el nom dels grups en comú que té l'usuari connectat amb un altre usuari
	 * @param idAltreUsuari ID de l'usuari amb el que es volen veure els grups en comú
	 * @return Llista amb nombs de grup en comú
	 * @throws Exception
	 */
	public List<String> getGrupsEnComuAmb(String idAltreUsuari) throws Exception {
		List<String> grupsEnComu = new LinkedList<>();
		List<Integer> llistaIdGrups = Connexio.getIdGrups();
		
		
		String query = "SELECT g.\"nom\" FROM \"GrupsUsuaris\" gu"
					 + " INNER JOIN \"Grups\" g ON g.\"idGrup\"=gu.\"idGrup\""
					 + " WHERE gu.\"idUsuari\"='" + idAltreUsuari + "' AND (";
		
		
		for (int i=0; i<llistaIdGrups.size(); i++) {
			
			if (i==llistaIdGrups.size()-1) query += "gu.\"idGrup\" = " + llistaIdGrups.get(i) + ")";
			else query += "gu.\"idGrup\" =" + llistaIdGrups.get(i) + " OR ";
		}

		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		while (rs.next()) grupsEnComu.add(rs.getString("nom"));
		
		return grupsEnComu;
	}
	
	
	
	/**
	 * Elimina un participant d'un grup particular
	 * @param idUsuari ID de l'usuari que es vol eliminar
	 * @param idGrup ID del grup d'on es vol eliminar el participant
	 */
	public void eliminarParticipantGrup(String idUsuari, int idGrup){
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("DELETE FROM \"GrupsUsuaris\" WHERE \"idUsuari\"='" + idUsuari + "' AND \"idGrup\"=" + idGrup);
			rs.close();
		} catch (Exception ignored) {}
	}
	
	public void afegirParticipantAlGrup(String idUsuari, int idGrup) {
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO \"GrupsUsuaris\" VALUES (" + idGrup + ",'" + idUsuari + "', '#000000')");
		} catch (Exception ignored) {ignored.printStackTrace();}
	}
	
}
