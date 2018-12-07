package domain;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Buch extends Medium {
	private int buchId;
	private String barcode;
	private Verlag verlag;
	private String reihe;
	private String auflage;
	private int anzahlSeiten;
	private String signatur;
	private long isbn;
	private int erscheinungsJahr;
	private String erscheinungsOrt;
	
	public Buch() {

	}

	public Verlag getVerlag() {
		return verlag;
	}

	public void setVerlag(Verlag verlag) {
		this.verlag = verlag;
	}

	public String getReihe() {
		return reihe;
	}

	public void setReihe(String reihe) {
		this.reihe = reihe;
	}

	public String getAuflage() {
		return auflage;
	}

	public void setAuflage(String auflage) {
		this.auflage = auflage;
	}

	public int getAnzahlSeiten() {
		return anzahlSeiten;
	}

	public void setAnzahlSeiten(int anzahlSeiten) {
		this.anzahlSeiten = anzahlSeiten;
	}

	public String getSignatur() {
		return signatur;
	}

	public void setSignatur(String signatur) {
		this.signatur = signatur;
	}

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long l) {
		this.isbn = l;
	}

	public int getErscheinungsJahr() {
		return erscheinungsJahr;
	}

	public void setErscheinungsJahr(int erscheinungsJahr) {
		this.erscheinungsJahr = erscheinungsJahr;
	}

	public String getErscheinungsOrt() {
		return erscheinungsOrt;
	}

	public void setErscheinungsOrt(String erscheinungsOrt) {
		this.erscheinungsOrt = erscheinungsOrt;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public int getBuchId() {
		return buchId;
	}

	public void setBuchId(int buchId) {
		this.buchId = buchId;
	}

	

}
