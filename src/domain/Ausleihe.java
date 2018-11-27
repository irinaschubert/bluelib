package domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Ausleihe {
	
	private int id;
	private int mediumID;
	private int benutzerID;
	private Date ausleiheDatum;
	private Date rueckgabeDatum;
	private int ausleiheMitarbeiterID;
	private int rueckgabeMitarbeiterID;
	private String notizAusleihe;
	
	
	public Ausleihe() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMediumID() {
		return mediumID;
	}

	public void setMediumID(int id) {
		this.mediumID = id;
	}

	public int getBenutzerID() {
		return benutzerID;
	}

	public void setBenutzerID(int id) {
		this.benutzerID = id;
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
	
	public String getNotizAusleihe() {
		return notizAusleihe;
	}

	public void setNotizAusleihe(String notizAusleihe) {
		this.notizAusleihe = notizAusleihe;
	}


}
