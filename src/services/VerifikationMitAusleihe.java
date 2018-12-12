package services;

import domain.Ausleihe;

/**
 * Erweitert die Verifikationsklasse um das Ausleihe-Objekt.
 * 
 * @version 1.0 2018-12-06
 * @author Ueli
 *
 */

public class VerifikationMitAusleihe extends Verifikation {
	private Ausleihe ausleihe;
	
	public Ausleihe getAusleihe() {
		return ausleihe;
	}

	public void setAusleihe(Ausleihe ausleihe) {
		this.ausleihe = ausleihe;
	}


}
