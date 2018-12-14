package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.AusleiheDAO;
import dao.BuchDAO;
import domain.Ausleihe;
import domain.Buch;

/**
 * 
 * Stellt die Services zum Rueckgabe der Medien zur Verfuegung
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
		BuchDAO buchDAO = new BuchDAO();
		if (istAusgeliehen(id)) {
			v.setAktionErfolgreich(true);
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		v.setAusleihe(ausleiheDAO.findAusgeliehenesBuchById(id));
			
		}
		else {
			v.setAktionErfolgreich(false);
			String titel = buchDAO.findById(id).getTitel();
			v.setNachricht("Das Buch '" + titel + "' ist nicht ausgeliehen.");
		}
			return v;
	}
		
	
	public Boolean istAusgeliehen(int id) {
		Boolean r = false;
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		r = ausleiheDAO.mediumIstAusgeliehen(id);
		return r;
	}
	
	public Verifikation rueckgabe(Ausleihe ausleihe) {
		Verifikation v = new Verifikation();
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		Ausleihe a = new Ausleihe();
		a = ausleiheDAO.update(ausleihe);
		if (a != null) {
			v.setAktionErfolgreich(true);
			BuchDAO buchDAO = new BuchDAO();
			Buch b = buchDAO.findById(ausleihe.getMedium().getId());
			b.setBemerkung(ausleihe.getMedium().getBemerkung());
			buchDAO.update(b);
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Buch konnte nicht zurückgegeben werden.");
		}
		
		return v;
		
	}
	
	public List<Ausleihe> heuteZurueckGegeben(){
		List<Ausleihe> zurueck = new ArrayList<>();
		Ausleihe ausleihe = new Ausleihe();
		ausleihe.setRueckgabeDatum(new Date());
		AusleiheDAO ausleiheDAO = new AusleiheDAO();
		zurueck = ausleiheDAO.getSelektion(ausleihe);
		return zurueck;
	}

}
