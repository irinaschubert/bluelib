package domain;

/**
 * Die Klasse der Adress-Objekte. Adress-Objekte bestehen aus einer Strasse (inkl. Hausnr.) und einem Ort.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 *
 */
public class Adresse {
	private String strasse;
	private Ort ort;
    
    public Adresse(String strasse, Ort ort) {
    	this.strasse = strasse;
    	this.ort = ort;
    }
    
    public Adresse(Ort ort) {
    	this.strasse = "";
    	this.ort = ort;
    }

	public Ort getOrt() {
		return ort;
	}

	public void setOrt(Ort ort) {
		this.ort = ort;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}
}
