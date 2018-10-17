package domain;

import java.util.Date;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public abstract class Person {
	
	private int id;
	private String name;
	private String vorname;
	private Date geburtsdatum;
	private Adresse adresse;
	private String email;
	private String telefon;
	private Date erfassungDatum;
	private Person erfassungMitarbeiter;
	
	public Person() {
		
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

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
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

	public Date getErfassungDatum() {
		return erfassungDatum;
	}

	public void setErfassungDatum(Date erfassungDatum) {
		this.erfassungDatum = erfassungDatum;
	}

	public Person getErfassungMitarbeiter() {
		return erfassungMitarbeiter;
	}

	public void setErfassungMitarbeiter(Person erfassungMitarbeiter) {
		this.erfassungMitarbeiter = erfassungMitarbeiter;
	}
	
	

}
