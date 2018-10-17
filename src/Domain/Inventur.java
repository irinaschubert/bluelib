package Domain;

import java.util.Date;
import java.util.List;
import Domain.Person;
import Domain.InventurPosition;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class Inventur {
	
	private int id;
	private String inventurBezeichnung;
	private Date inventurDatum;
	private Person inventurMitarbeiter;
	private List<InventurPosition> positionen;
	
	public Inventur() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInventurBezeichnung() {
		return inventurBezeichnung;
	}

	public void setInventurBezeichnung(String inventurBezeichnung) {
		this.inventurBezeichnung = inventurBezeichnung;
	}

	public Date getInventurDatum() {
		return inventurDatum;
	}

	public void setInventurDatum(Date inventurDatum) {
		this.inventurDatum = inventurDatum;
	}

	public Person getInventurMitarbeiter() {
		return inventurMitarbeiter;
	}

	public void setInventurMitarbeiter(Person inventurMitarbeiter) {
		this.inventurMitarbeiter = inventurMitarbeiter;
	}

	public List<InventurPosition> getPositionen() {
		return positionen;
	}

	public void setPositionen(List<InventurPosition> positionen) {
		this.positionen = positionen;
	}
	
	

}
