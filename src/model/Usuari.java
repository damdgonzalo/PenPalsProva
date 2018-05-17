package model;

public class Usuari {

	private String id;
	private String nom;
	private String correu;
	private String telefon;
	private String mobil;
	private String dataNaixement;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCorreu() {
		return correu;
	}
	public void setCorreu(String correu) {
		this.correu = correu;
	}
	public String getTelefon() {
		return telefon;
	}
	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}
	public String getMobil() {
		return mobil;
	}
	public void setMobil(String mobil) {
		this.mobil = mobil;
	}
	public String getDataNaixement() {
		return dataNaixement;
	}
	public void setDataNaixement(String dataNaixement) {
		this.dataNaixement = dataNaixement;
	}
	
}
