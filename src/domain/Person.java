package domain;

import java.util.Date;

/**
 * Die Klasse der Person-Objekte. Von ihr werden Mitarbeiter und Benutzer abgeleitet.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public abstract class Person {
	private int id;
	private Anrede anrede;
	private String name;
	private String vorname;
	private Date geburtsdatum;
	private Adresse adresse;
	private String email;
	private String telefon;
	private String bemerkung;
	private Date erfassungDatum;
	private int erfassungMitarbeiterId;
	private String erfassungMitarbeiterName;
	
	public Person() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Anrede getAnrede() {
		return anrede;
	}

	public void setAnrede(Anrede anrede) {
		this.anrede = anrede;
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

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public Date getErfassungDatum() {
		return erfassungDatum;
	}

	public void setErfassungDatum(Date erfassungDatum) {
		this.erfassungDatum = erfassungDatum;
	}

	public int getErfassungMitarbeiterId() {
		return erfassungMitarbeiterId;
	}

	public void setErfassungMitarbeiterId(int erfassungMitarbeiterId) {
		this.erfassungMitarbeiterId = erfassungMitarbeiterId;
	}

	public String getErfassungMitarbeiterName() {
		return erfassungMitarbeiterName;
	}

	public void setErfassungMitarbeiterName(String erfassungMitarbeiterName) {
		this.erfassungMitarbeiterName = erfassungMitarbeiterName;
	}
}
