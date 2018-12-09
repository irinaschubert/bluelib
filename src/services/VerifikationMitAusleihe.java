package services;

import domain.Ausleihe;
import domain.Benutzer;
import domain.Buch;

public class VerifikationMitAusleihe extends Verifikation {
	private Ausleihe ausleihe;
	
	public Ausleihe getAusleihe() {
		return ausleihe;
	}

	public void setAusleihe(Ausleihe ausleihe) {
		this.ausleihe = ausleihe;
	}


}
