package Domain;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */

public class Adresse {
    int id;
    String strasse;
    Ort ort;
    
    public Adresse() {
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public Ort getOrt() {
		return ort;
	}

	public void setOrt(Ort ort) {
		this.ort = ort;
	}
    
}
