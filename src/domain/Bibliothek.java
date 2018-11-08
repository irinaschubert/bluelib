package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Bibliothek{
	
	private String name;
	private String email;
	private String telefon;
	private String oeffnungszeiten;
	private String strasseUndNr;
	private int leihfrist; // Anz. Tage
	
	public Bibliothek() {
		
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

	public String getStrasseUndNr() {
		return strasseUndNr;
	}

	public void setStrasseUndNr(String strasseUndNr) {
		this.strasseUndNr = strasseUndNr;
	}

	public int getLeihfrist() {
		return leihfrist;
	}

	public void setLeihfrist(int leihleihfristdauer) {
		this.leihfrist = leihfrist;
	}
	
	
	
}