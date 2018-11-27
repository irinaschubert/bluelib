package services;

import java.util.ArrayList;
import java.util.List;

import dao.AnredeDAO;
import dao.AusleiheDAO;
import domain.Anrede;
import domain.Ausleihe;

/**
 * @version 0.1 22.10.2018
 * @author irina
 *
 */
public class AusleiheService {
	
	public AusleiheService() {}
	
	
	public Verifikation darfAusleihen(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation aktivieren(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation sperren(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public Verifikation loeschen(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		return v;
	}
	
	public ArrayList<Ausleihe> leihlisteAnzeigen(Ausleihe ausleihe){
		ArrayList<Ausleihe> list = new ArrayList<>();
		return list;
	}

	public List<Ausleihe> alleAusleihen(){
		return new AusleiheDAO().findAll();
	}
	
	public Verifikation sichereAusleihe(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		if (new AusleiheDAO().save(ausleihe) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Ausleihe wurde gespeichert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Ausleihe konnte nicht gespeichert werden.");
		}
		return v;
	}
	
	public List<Ausleihe> sucheAusleiheProBenutzer(Ausleihe ausleihe){
		return new AusleiheDAO().getSelektion(ausleihe);
	}
}
