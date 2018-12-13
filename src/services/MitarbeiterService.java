package services;

import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import domain.Benutzer;
import domain.Mitarbeiter;
import domain.Schlagwort;

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
    
    private boolean immerEinAdmin(Mitarbeiter mitarbeiter) {
    	boolean yes = true;
    	return yes;
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
		System.out.println("Benutername1111: "+mitarbeiter.getBenutzername());
		if (new MitarbeiterDAO().save(mitarbeiter) != null) {
			System.out.println("Benutername2222: "+mitarbeiter.getBenutzername());
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " konnte nicht gespeichert werden.");
		}
		return v;

	}
    
    public Mitarbeiter suchenBenutzerMitID(int id) {
		return new MitarbeiterDAO().findById(id);
	}
}
