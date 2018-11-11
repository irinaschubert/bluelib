package services;

import java.util.ArrayList;
import java.util.List;

import dao.AnredeDAO;
import dao.AutorDAO;
import dao.BenutzerDAO;
import dao.VerlagDAO;
import domain.Anrede;
import domain.Ausleihe;
import domain.Autor;
import domain.Benutzer;
import domain.Verlag;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class BenutzerService {
	
	public BenutzerService() {}
	
	public List<Anrede> alleAnreden(){
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
	
	public ArrayList<Ausleihe> leihlisteAnzeigen(Benutzer benutzer){
		ArrayList<Ausleihe> list = new ArrayList<>();
		return list;
	}

	public List<Benutzer> alleBenutzer(){
		return new BenutzerDAO().findAll();
	}
	
	public Verifikation sichereBenutzer(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		if (new BenutzerDAO().save(benutzer) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Benutzer "
					+ benutzer.getName()
					+" wurde gespeichert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Benutzer "
					+ benutzer.getName()
					+" konnte nicht gespeichert werden.");
		}
		
		return v;
	}
	
	public Verifikation aktualisiereBenutzer(Benutzer benutzer) {
		Verifikation v = new Verifikation();
		if (new BenutzerDAO().update(benutzer)!= null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Benutzer "
					+ benutzer.getName()
					+" wurde aktualisiert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Benutzer "
					+ benutzer.getName()
					+" konnte nicht aktualisiert werden.");
		}
		return v;	
	}
	
	public List<Benutzer> sucheBenutzer(Benutzer benutzer){
		return new BenutzerDAO().getSelektion(benutzer);
	}

}
