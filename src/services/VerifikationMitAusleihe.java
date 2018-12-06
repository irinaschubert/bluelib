package services;

import domain.Ausleihe;
import domain.Benutzer;
import domain.Buch;

public class VerifikationMitAusleihe extends Verifikation {
	private Ausleihe ausleihe;
	private Buch buch;
	private Benutzer benutzer;

	public Ausleihe getAusleihe() {
		return ausleihe;
	}

	public void setAusleihe(Ausleihe ausleihe) {
		this.ausleihe = ausleihe;
	}

	public Buch getBuch() {
		return buch;
	}

	public void setBuch(Buch buch) {
		this.buch = buch;
	}

	public Benutzer getBenutzer() {
		return benutzer;
	}

	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}

}
