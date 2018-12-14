package services;

import java.util.ArrayList;
import java.util.List;

import dao.AnredeDAO;
import dao.BenutzerDAO;
import dao.BuchDAO;
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

	/**
	 * Prueft, ob es sich beim Parameter um eine valide id handelt
	 * 
	 * @param id
	 * @return id valide: true, id falsch: false
	 */
	public Verifikation istBenutzerID(String id) {
		Verifikation v = new Verifikation();
		v.setAktionErfolgreich(true);
		try 
        { 
            // checking valid integer using parseInt() method 
            Integer.parseInt(id); 
        }  
        catch (NumberFormatException e)  
        { 
            v.setAktionErfolgreich(false);
            v.setNachricht("Ungültige ID");
        } 
		return v;
	}
	
	public Verifikation idZugeordnet(int id) {
		Verifikation v = new Verifikation();
		v.setAktionErfolgreich(false);
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		if (benutzerDAO.idZugeordnet(id)) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Diese ID ist bereits einem Benutzer zugeordnet.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Diese ID ist keinem Benutzer zugeordnet.");
			
		}
		return v;
	}

	public Verifikation darfAusleihen(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		return v;
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
	
	public Benutzer suchenBenutzerById(int id) {
		return new BenutzerDAO().findById(id);
	}
	
	
	public Verifikation updateMitarbeiterID(int id, int maid) {
		Verifikation v = new Verifikation();
		v.setAktionErfolgreich(false);
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		if (benutzerDAO.updateMAID(id, maid)) {
			v.setAktionErfolgreich(true);
		} else {
			v.setAktionErfolgreich(false);
		}
		return v;
	}
}
