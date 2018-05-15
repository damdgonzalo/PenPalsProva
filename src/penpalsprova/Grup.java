package penpalsprova;

import java.util.LinkedList;
import java.util.List;

public class Grup {

	private int id;
	private String nom;
	private String administrador;
	private String dataCreacio;
	private String colorGrup;
	private List<String> participants;
	
	
	public Grup() {
		nom = "";
		administrador = "";
		dataCreacio = "";
		colorGrup = "";
		participants = new LinkedList<>();
	}
	
	
	public String getAdministrador() {
		return administrador;
	}
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	public String getDataCreacio() {
		return dataCreacio;
	}
	public void setDataCreacio(String dataCreacio) {
		this.dataCreacio = dataCreacio;
	}
	public void setColorGrup(String colorGrup) {
		this.colorGrup = colorGrup;
	}

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getColorGrup() {return colorGrup;}
	public void setColorGrup() {this.colorGrup = colorGrup;}
	
	public List<String> getParticipants() {
		return participants;
	}
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
}
