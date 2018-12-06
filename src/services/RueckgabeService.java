package services;

import dao.AusleiheDAO;
import dao.BenutzerDAO;
import dao.BuchDAO;
import domain.Ausleihe;

/**
 * 
 * Die Klasse stellt die Methoden zur Rückgabe von Medien zur Verfügung
 * 
 * @version 1.0 2018-12-07
 * @author Schmutz
 *
 */

public class RueckgabeService {
	
	public Verifikation zurueckgeben(Ausleihe ausleihe ) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public VerifikationMitAusleihe ausleiheAnzeigenByBuchId(int id) {
		VerifikationMitAusleihe v = new VerifikationMitAusleihe();
		if (istAusgeliehen(id)) {
			v.setAktionErfolgreich(true);
			Ausleihe a = new Ausleihe();
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		v.setAusleihe(ausleiheDAO.findAusgeliehenesBuchById(id));
		
		BuchDAO buchDAO = new BuchDAO();
		v.setBuch(buchDAO.findById(v.getAusleihe().getMediumID()));
		
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		v.setBenutzer(benutzerDAO.findById(v.getAusleihe().getBenutzerID()));
		
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Buch ist nicht ausgeliehen.");
		}
			return v;
	}
		
	
	private Boolean istAusgeliehen(int id) {
		Boolean r = false;
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		r = ausleiheDAO.mediumIstAusgeliehen(id);
		return r;
	}

}
