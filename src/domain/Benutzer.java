package domain;

import java.util.Date;

/**
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private int benutzerStatus;
	private int mitarbeiterStatus;
	
	public Benutzer() {
		setBenutzerStatus(Status.AKTIV);
		setMitarbeiterStatus(Status.AKTIV);
	}

	public int getBenutzerStatus() {
		return benutzerStatus;
	}

	public void setBenutzerStatus(int beS) {
		this.benutzerStatus = beS;
	}
	
	public int getMitarbeiterStatus() {
		return mitarbeiterStatus;
	}

	public void setMitarbeiterStatus(int maS) {
		this.mitarbeiterStatus = maS;
	}
	
	
}
