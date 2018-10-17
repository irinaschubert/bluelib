package Domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Bibliothek{
	
	String name;
	String email;
	String telefon;
	String oeffnungszeiten;
	Adresse adresse;
	int leihdauer; // Anz. Tage
	
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

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public int getLeihdauer() {
		return leihdauer;
	}

	public void setLeihdauer(int leihdauer) {
		this.leihdauer = leihdauer;
	}
	
	
	
}