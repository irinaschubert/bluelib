package services;


import dao.BenutzerDAO;
import dao.BuchDAO;
import dao.MitarbeiterDAO;
import domain.Benutzer;
import domain.Buch;
import domain.Mitarbeiter;
import domain.Schlagwort;
import ui.MA.MitarbeiterView;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class MitarbeiterService {
    public MitarbeiterService() {}
    
    public Verifikation darfEinloggen(String name, String pw) {
    	Verifikation v = new Verifikation();
		return v;
    }
     
    public Verifikation aktualisierenMitarbeiter(Mitarbeiter mitarbeiter) {
		Verifikation v = new Verifikation();
		if (new MitarbeiterDAO().update(mitarbeiter) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Das Mitarbeiter " + mitarbeiter.getBenutzername() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Mitarbeiter " + mitarbeiter.getBenutzername() + " konnte nicht aktualisiert werden.");
		}
		return v;
	}
    
    public Verifikation speichernMitarbeiter(Mitarbeiter mitarbeiter) { 	
		Verifikation v = new Verifikation();
		Mitarbeiter m = null;
		MitarbeiterDAO madao = new MitarbeiterDAO();
		m = madao.save(mitarbeiter);
		if (m != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " konnte nicht gespeichert werden.");
		}
		return v;

	}
    
    public String suchenNameVornameById(int id) {
		return new MitarbeiterDAO().findNameVornameById(id);
	}
    
    public Mitarbeiter suchenMitarbeiterById(int id) {
		return new MitarbeiterDAO().findById(id);
	}
    
    public int suchenMitarbeiterIdByName(String name, String vorname) {
		return new MitarbeiterDAO().findIdByName(name, vorname);
	}
}
