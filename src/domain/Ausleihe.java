package domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Ausleihe {
	
	private int id;
	private Medium medium;
	private Person benutzer;
	private Date ausleiheDatum;
	private Date rueckgabeDatum;
	private Person ausleiheMitarbeiter;
	private Person rueckgabeMitarbeiter;
	
	public Ausleihe() {
		
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

	public Person getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Person benutzer) {
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

	public Person getAusleiheMitarbeiter() {
		return ausleiheMitarbeiter;
	}

	public void setAusleiheMitarbeiter(Person ausleiheMitarbeiter) {
		this.ausleiheMitarbeiter = ausleiheMitarbeiter;
	}

	public Person getRueckgabeMitarbeiter() {
		return rueckgabeMitarbeiter;
	}

	public void setRueckgabeMitarbeiter(Person rueckgabeMitarbeiter) {
		this.rueckgabeMitarbeiter = rueckgabeMitarbeiter;
	}
	
	

}
