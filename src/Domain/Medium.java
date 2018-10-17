package Domain;

import java.util.Date;
import java.util.List;

/**
 * @version 0.1 16.10.2018
 * @author BlueLib
 *
 */
public abstract class Medium {

	int id;
	int barcodeNr;
	String titel;
	List<Schlagwort> schlagwoerter;
	Double preis;
	Status status;
	Date erfassungDatum;
	Person erfassungMitarbeiter;
	String bemerkung;
	
	public Medium() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBarcodeNr() {
		return barcodeNr;
	}

	public void setBarcodeNr(int barcodeNr) {
		this.barcodeNr = barcodeNr;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public List<Schlagwort> getSchlagwoerter() {
		return schlagwoerter;
	}

	public void setSchlagwoerter(List<Schlagwort> schlagwoerter) {
		this.schlagwoerter = schlagwoerter;
	}

	public Double getPreis() {
		return preis;
	}

	public void setPreis(Double preis) {
		this.preis = preis;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}
	
	
}

