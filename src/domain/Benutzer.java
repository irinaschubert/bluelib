package domain;

import java.util.Date;

/**
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private int status;
	
	public Benutzer() {
		status = Status.AKTIV;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
