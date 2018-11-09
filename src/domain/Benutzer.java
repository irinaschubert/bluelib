package domain;

import java.util.Date;

/**
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private String benutzerStatus;
	private String mitarbeiterStatus;
	
	public Benutzer() {
		setBenutzerStatus(Status.AKTIV);
		setMitarbeiterStatus(Status.AKTIV);
	}

	public String getBenutzerStatus() {
		return benutzerStatus;
	}

	public void setBenutzerStatus(String beS) {
		this.benutzerStatus = beS;
	}
	
	public String getMitarbeiterStatus() {
		return mitarbeiterStatus;
	}

	public void setMitarbeiterStatus(String maS) {
		this.mitarbeiterStatus = maS;
	}
	
	
}
