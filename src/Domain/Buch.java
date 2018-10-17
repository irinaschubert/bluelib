package Domain;

import java.util.List;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */
public class Buch {
	
	List<Autor> autoren;
	Verlag verlag;
	String reihe;
	String auflage;
	String ausgabe; // Was ist damit gemeint?
	int anzahlSeiten;
	String signatur;
	int isbn;
	String erscheinungsJahr;
	String erscheinungsOrt;
	
	public Buch() {
		
	}

	public List<Autor> getAutoren() {
		return autoren;
	}

	public void setAutoren(List<Autor> autoren) {
		this.autoren = autoren;
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

	public String getAusgabe() {
		return ausgabe;
	}

	public void setAusgabe(String ausgabe) {
		this.ausgabe = ausgabe;
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

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}

	public String getErscheinungsJahr() {
		return erscheinungsJahr;
	}

	public void setErscheinungsJahr(String erscheinungsJahr) {
		this.erscheinungsJahr = erscheinungsJahr;
	}

	public String getErscheinungsOrt() {
		return erscheinungsOrt;
	}

	public void setErscheinungsOrt(String erscheinungsOrt) {
		this.erscheinungsOrt = erscheinungsOrt;
	}

	

}
