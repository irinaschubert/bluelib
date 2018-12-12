package services;

import java.util.ArrayList;
import java.util.List;

import dao.AnredeDAO;
import dao.BenutzerDAO;
import domain.Anrede;
import domain.Ausleihe;
import domain.Benutzer;

/**
 * Die Serviceklasse vermittelt zwischen dem BenutzerController und dem
 * BenutzerDAO.
 * 
 * @version 0.1 16.10.2018
 * @author Irina
 * 
 */
public class BenutzerService {

	public BenutzerService() {
	}

	public List<Anrede> alleAnreden() {
		return new AnredeDAO().findAll();
	}

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

	public ArrayList<Ausleihe> leihlisteAnzeigen(Benutzer benutzer) {
		ArrayList<Ausleihe> list = new ArrayList<>();
		return list;
	}

	public List<Benutzer> alleBenutzer() {
		return new BenutzerDAO().findAll();
	}

	public Verifikation speichernBenutzer(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		if (new BenutzerDAO().save(benutzer) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Benutzer " + benutzer.getName() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Benutzer " + benutzer.getName() + " konnte nicht gespeichert werden.");
		}
		return v;
	}

	public Verifikation aktualisierenBenutzer(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		if (new BenutzerDAO().update(benutzer) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Benutzer " + benutzer.getName() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Benutzer " + benutzer.getName() + " konnte nicht aktualisiert werden.");
		}
		return v;
	}

	public List<Benutzer> suchenBenutzer(Benutzer benutzer) {
		return new BenutzerDAO().getSelektion(benutzer);
	}

}
