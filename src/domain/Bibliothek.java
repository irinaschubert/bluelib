package domain;

/**
 * Die Klasse des Bibliothekobjekts.
 * 
 * @version 0.1 16.10.2018
 * @author Mike
 *
 */
public class Bibliothek{
	
	private int id;
	private String name;
	private String email;
	private String telefon;
	private String oeffnungszeiten;
	private Adresse adresse;
	private int leihfrist; // Anz. Tage
	
	public Bibliothek() {
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefon() {
		return telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getOeffnungszeiten() {
		return oeffnungszeiten;
	}

	public void setOeffnungszeiten(String oeffnungszeiten) {
		this.oeffnungszeiten = oeffnungszeiten;
	}

	public int getLeihfrist() {
		return leihfrist;
	}

	public void setLeihfrist(int leihfrist) {
		this.leihfrist = leihfrist;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
}