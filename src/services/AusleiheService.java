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
	
	public List<Anrede> alleAnreden(){
		return new AnredeDAO().findAll();
	}
	
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

	public List<Ausleihe> alleAusleihe(){
		return new AusleiheDAO().findAll();
	}
	
	public Verifikation sichereAusleihe(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		if (new AusleiheDAO().save(ausleihe) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Ausleihe "
					+ ausleihe.getName()
					+" wurde gespeichert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Ausleihe "
					+ ausleihe.getName()
					+" konnte nicht gespeichert werden.");
		}
		
		return v;
	}
	
	public Verifikation aktualisiereAusleihe(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		if (new AusleiheDAO().update(ausleihe)!= null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Ausleihe "
					+ ausleihe.getName()
					+" wurde aktualisiert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Ausleihe "
					+ ausleihe.getName()
					+" konnte nicht aktualisiert werden.");
		}
		return v;	
	}
	
	public List<Ausleihe> sucheAusleihe(Ausleihe ausleihe){
		return new AusleiheDAO().getSelektion(ausleihe);
	}

}
