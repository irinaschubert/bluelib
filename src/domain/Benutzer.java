package domain;

/**
 * Die Klasse der Benutzer-Objekte. Ein Benutzer erbt von Person und hält
 * zusätzlich einen status.
 * 
 * @version 0.1 06.11.2018
 * @author Irina
 * 
 */
public class Benutzer extends Person {

	private Status benutzerStatus;
	private int maid;

	// Benutzer wird mit Status "aktiv" (=1) initialisiert
	public Benutzer() {
		this.benutzerStatus = new Status(1);
	}

	public Status getBenutzerStatus() {
		return benutzerStatus;
	}

	public void setBenutzerStatus(Status beS) {
		this.benutzerStatus = beS;
	}
}
