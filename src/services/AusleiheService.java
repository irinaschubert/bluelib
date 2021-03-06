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
	
	public Ausleihe suchenAusleiheById(int id) {
		return new AusleiheDAO().findById(id);
	}
	
	public List<Ausleihe> suchenAlleAusleihen() {
		return new AusleiheDAO().findAll();
	}

	public List<Ausleihe> suchenAusleihenProBenutzer(Benutzer benutzer) {
		return new AusleiheDAO().getSelektionByBenutzer(benutzer);
	}
}
