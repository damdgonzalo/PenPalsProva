package connexio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Grup;
import model.Nota;

public class ConnexioNotes{
	private Connection conn;
	private Statement stmt;
	
	private int comptadorNotes;
	private int comptadorNotesVell;
	
	private List<Integer> grups;
	
	public ConnexioNotes() throws Exception {
		conn = Connexio.conn;
		stmt = Connexio.stmt;
		
		grups = Connexio.getIdGrups();
	
		comptadorNotes = comptarNotesActuals();
		comptadorNotesVell = comptadorNotes;
	}
	
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Retorna tota la informació d'una nota donada la seva ID
	 * @param idNota ID de la nota que es vol
	 * @return Dades de la nota
	 */
	public Nota getNota(String idNota) throws Exception {
		String query = "SELECT * FROM \"Notes\" WHERE \"idNota\"='" + idNota + "'";
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		
		Nota nota = new Nota();
		nota.setId(Integer.parseInt(rs.getString("idNota")));
		nota.setTitol(rs.getString("titol"));
		nota.setDataPublicacio(rs.getString("dataCreacio"));
		nota.setDataUltModificacio(rs.getString("ultimaModificacio"));
		nota.setText(rs.getString("cos"));
		nota.setAutor(rs.getString("autor"));
		
		return nota;
	}
	
//----------------------------------------------------------------------------------------------------------------------

	/**
	 * Retorna una llista amb totes les notes que hi ha als grups que pertany l'usuari
 	 * @return Llista de notes
	 * @throws Exception
	 */
	public List<Nota> getNotes() throws Exception {
				
		String query = "SELECT g.\"idGrup\", gr.\"nom\" AS \"nomGrup\", n.*, gu.\"color\" FROM \"GrupsNota\" g"
					 + " INNER JOIN \"Notes\" n ON g.\"idNota\" = n.\"idNota\""
					 + " INNER JOIN \"GrupsUsuaris\" gu ON gu.\"idGrup\"=g.\"idGrup\""
					 + " INNER JOIN \"Grups\" gr ON gr.\"idGrup\"=g.\"idGrup\""
					 + " WHERE gu.\"idUsuari\"='" + Connexio.getUsuari() + "' AND (";
	
		for (int i=0; i<grups.size(); i++) {
			
			if (i==grups.size()-1) query += "g.\"idGrup\" = " + grups.get(i) + ")";
			else query += "g.\"idGrup\" =" + grups.get(i) + "OR ";
		}
					
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		List<Nota> llistaNotes = new LinkedList<>();
		
		while (rs.next()) {
			Nota nota = new Nota();
			nota.setId(Integer.parseInt(rs.getString("idNota")));
			nota.setGrup(new Grup());
			nota.setTitol(rs.getString("titol"));
			nota.setDataPublicacio(rs.getString("dataCreacio"));
			nota.setDataUltModificacio(rs.getString("ultimaModificacio"));
			nota.setText(rs.getString("cos"));
			nota.setAutor(rs.getString("autor"));
			nota.setGrup(ConnexioGrups.getGrup(Integer.parseInt(rs.getString("idGrup"))));
			
			llistaNotes.add(nota);
		}
		
		return llistaNotes;
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Retorna una llista només amb les notes noves que s'han penjat als grups que pertany l'usuari.
	 * Si no hi ha cap nota nova, retorna una llista buida.
	 * Es recomana utilitzar sempre després de la funció hiHaNotesNoves() , si aquesta retorna TRUE.
	 * @return Llista amb les notes noves que t� l'usuari
	 */
	public List<Nota> getNotesNoves() throws Exception {
		String query = "SELECT g.\"idGrup\", n.* FROM \"GrupsNota\" g";
			  query += " INNER JOIN \"Notes\" n ON g.\"idNota\" = n.\"idNota\" WHERE ";
		
		//busca notes noves a cada grup
		for (int i=0; i<grups.size(); i++) {
			
			if (i==grups.size()-1) query += "g.\"idGrup\" =" + grups.get(i);
			else query += "g.\"idGrup\"=" + grups.get(i) + " OR ";
		}
		
		query += " ORDER BY n.\"idNota\" DESC LIMIT " + (comptarNotesActuals() - comptadorNotesVell);
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		
		List<Nota> llistaNotesNoves = new LinkedList<>();
		
		comptadorNotesVell = comptadorNotes;
		
		comptadorNotes = 0;
		while (rs.next()) {
			Nota notaNova = new Nota();
			notaNova.setTitol(rs.getString("titol"));
			notaNova.setDataPublicacio(rs.getString("dataCreacio"));
			notaNova.setDataUltModificacio(rs.getString("ultimaModificacio"));
			notaNova.setText(rs.getString("cos"));
			notaNova.setAutor(rs.getString("autor"));
			
			notaNova.setGrup(ConnexioGrups.getGrup(Integer.parseInt(rs.getString("idGrup"))));
			
			llistaNotesNoves.add(notaNova);
			comptadorNotes++;
		}
		
		return llistaNotesNoves;
	}
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Comprova si hi ha notes noves en algun dels grups als que pertany l'usuari
	 * @return TRUE si l'usuari t� notes noves
	 */
	public boolean hiHaNotesNoves() throws Exception {		
		int notesActuals = comptarNotesActuals();
		
		if (notesActuals != 0) {
			if (notesActuals > comptadorNotes) return true;
		}
		
		return false;
	}
	
	
//----------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Compta les notes que hi ha a la base de dades als grups als que pertany l'usuari
	 * @return Número de notes que hi ha en un moment determinat
	 */
	public int comptarNotesActuals() throws Exception {
		String query = "SELECT COUNT(*) AS \"comptNotes\" FROM \"GrupsNota\" WHERE ";
		
		for (int i=0; i<grups.size(); i++) {
			
			if (i==grups.size()-1) query += "\"idGrup\" = " + grups.get(i);
			else query += "\"idGrup\" =" + grups.get(i) + " OR ";
		}
		
		stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		rs.next();
		
		int notesInicials = rs.getInt("comptNotes");
		
		return notesInicials;
	}
	

}