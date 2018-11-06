package domain;

import java.util.Date;

/**
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private int status;
	private boolean mitarbeiter;
	
	public Benutzer() {
		status = Status.AKTIV;
		mitarbeiter = false;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public boolean getMitarbeiter() {
		return mitarbeiter;
	}

	public void setMitarbeiter(boolean b) {
		this.mitarbeiter = b;
	}
	
	
}
