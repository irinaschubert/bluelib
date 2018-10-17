package services;

import java.util.ArrayList;

import domain.Ausleihe;
import domain.Benutzer;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class BenutzerService {
	
	public BenutzerService() {}
	
	public Verifikation darfAusleihen(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation aktivieren(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation sperren(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation loeschen(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public ArrayList<Ausleihe> leihlisteAnzeigen(Benutzer benutzer){
		ArrayList<Ausleihe> list = new ArrayList<>();
		return list;
	}
	
	public ArrayList<Benutzer> sucheBenutzer(Benutzer benutzer){
		ArrayList<Benutzer> list = new ArrayList<>();
		return list;
	}

}
