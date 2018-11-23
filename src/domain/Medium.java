package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public abstract class Medium {

	private int id;
	private int barcodeNr;
	private String titel;
	private List<Schlagwort> schlagwoerter;
	private List<Autor> autoren;
	private Double preis;
	private Status status;
	private Date erfassungDatum;
	private int erfasserId;
	private String erfasserName;
	private String bemerkung;
	
	public Medium() {
		this.setAutoren(new ArrayList<>());
		// Die Werte müssen initialisiert werden, da sie sonst den Wert 0 enthalten, was einen gültigen Wert darstellt
		this.id = -1;
		this.barcodeNr = -1;
				
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

	public String getBemerkung() {
		return bemerkung;
	}

	public void setBemerkung(String bemerkung) {
		this.bemerkung = bemerkung;
	}

	public List<Autor> getAutoren() {
		return autoren;
	}

	public void setAutoren(List<Autor> autoren) {
		this.autoren = autoren;
	}
	
	public void setAutor(Autor autor) {
		this.autoren.add(autor);
	}

	public int getErfasserId() {
		return erfasserId;
	}

	public void setErfasserId(int erfasserId) {
		this.erfasserId = erfasserId;
	}

	public String getErfasserName() {
		return erfasserName;
	}

	public void setErfasserName(String erfasserName) {
		this.erfasserName = erfasserName;
	}
	
	
}

