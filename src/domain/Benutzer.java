package domain;

/**
 * Die Klasse der Benutzer-Objekte. Ein Benutzer erbt von Person und
 * h�lt zus�tzlich einen status
 * @version 0.1 06.11.2018
 * @author irina
 */
public class Benutzer extends Person {

	private Status benutzerStatus;
	
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
