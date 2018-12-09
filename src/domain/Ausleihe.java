package domain;

import java.util.Date;

/**
 * Die Klasse der Ausleih-Objekte. Ausleihen haben eine id, ein Medium, ein Ausleih- und Rückgabedatum
 * sowie einen Ausleih- und Rückgabemitarbeiter
 * @version 0.1 16.10.2018
 * @author irina
 */
public class Ausleihe {
	
	private int id;
	private Medium medium;
	private Benutzer benutzer;
	private Date ausleiheDatum;
	private Date rueckgabeDatum;
	private int ausleiheMitarbeiterID;
	private String ausleiheMitarbeiterName;
	private int rueckgabeMitarbeiterID;
	private String rueckgabeMitarbeiterName;
	
	
	public Ausleihe() {
		this.id = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Medium getMedium() {
		return medium;
	}

	public void setMedium(Medium medium) {
		this.medium = medium;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

	public Date getAusleiheDatum() {
		return ausleiheDatum;
	}

	public void setAusleiheDatum(Date ausleiheDatum) {
		this.ausleiheDatum = ausleiheDatum;
	}

	public Date getRueckgabeDatum() {
		return rueckgabeDatum;
	}

	public void setRueckgabeDatum(Date rueckgabeDatum) {
		this.rueckgabeDatum = rueckgabeDatum;
	}

	public int getAusleiheMitarbeiterID() {
		return ausleiheMitarbeiterID;
	}

	public void setAusleiheMitarbeiterID(int id) {
		this.ausleiheMitarbeiterID = id;
	}

	public int getRueckgabeMitarbeiterID() {
		return rueckgabeMitarbeiterID;
	}

	public void setRueckgabeMitarbeiterID(int id) {
		this.rueckgabeMitarbeiterID = id;
	}
	
	public String getAusleiheMitarbeiterName() {
		return ausleiheMitarbeiterName;
	}

	public void setAusleiheMitarbeiterName(String ausleiheMitarbeiterName) {
		this.ausleiheMitarbeiterName = ausleiheMitarbeiterName;
	}

	public String getRueckgabeMitarbeiterName() {
		return rueckgabeMitarbeiterName;
	}

	public void setRueckgabeMitarbeiterName(String rueckgabeMitarbeiterName) {
		this.rueckgabeMitarbeiterName = rueckgabeMitarbeiterName;
	}

}
