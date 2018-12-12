package services;

import java.util.List;

import dao.AusleiheDAO;
import domain.Ausleihe;
import domain.Benutzer;

/**
 * Die Serviceklasse vermittelt zwischen dem AusleiheController und dem
 * AusleiheDAO.
 * 
 * @version 0.1 22.10.2018
 * @author Irina
 * 
 */
public class AusleiheService {

	public AusleiheService() {
	}

	public Verifikation darfAusleihen(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}

	public Verifikation loeschen(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}

	public List<Ausleihe> alleAusleihen() {
		return new AusleiheDAO().findAll();
	}

	public Verifikation speichernAusleihe(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		if (new AusleiheDAO().save(ausleihe) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Ausleihe wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Ausleihe konnte nicht gespeichert werden.");
		}
		return v;
	}

	public List<Ausleihe> sucheAusleihenProBenutzer(Benutzer benutzer) {
		return new AusleiheDAO().getSelektionByBenutzer(benutzer);
	}
}
