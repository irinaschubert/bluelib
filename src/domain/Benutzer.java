package domain;

/**
 * @version 0.1 06.11.2018
 * @author irina
 *
 */
public class Benutzer extends Person {

	private int benutzerStatus;
	
	public Benutzer() {
		setBenutzerStatus(1); // Benutzer hat zu Beginn den Status aktiv
	}

	public int getBenutzerStatus() {
		return benutzerStatus;
	}

	public void setBenutzerStatus(int beS) {
		this.benutzerStatus = beS;
	}
		
}
