package services;


import dao.MitarbeiterDAO;
import domain.Mitarbeiter;

/**
 * @version 2 16.12.2018
 * @author Mike
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
