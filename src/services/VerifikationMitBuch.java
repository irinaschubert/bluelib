package services;

import domain.Buch;

/**
 * Erweitert die Verifikationsklasse um das Buch-Objekt.
 * 
 * @version 1.0 2018-12-06
 * @author Ueli
 *
 */

public class VerifikationMitBuch extends Verifikation {
	private Buch buch;
	
	public Buch getBuch() {
		return buch;
	}

	public void setBuch(Buch buch) {
		this.buch = buch;
	}


}
