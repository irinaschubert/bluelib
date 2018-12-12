package services;

import java.util.List;

import dao.AnredeDAO;
import dao.AutorDAO;
import dao.VerlagDAO;
import dao.BibliothekDAO;
import dao.DezKlassifikationDAO;
import dao.DezKlassifikationGrpeDAO;
import dao.MitarbeiterDAO;
import dao.SchlagwortDAO;
import domain.Anrede;
import domain.Autor;
import domain.Verlag;
import domain.Bibliothek;
import domain.DezKlassifikation;
import domain.DezKlassifikationGrpe;
import domain.Schlagwort;
import domain.Mitarbeiter;

/**
 * Stellt die Services zum Verwalten der Normdaten zur Verfügung.
 * 
 * @version 1.0 15.11.2018
 * @author Irina, Mike, Ueli
 * 
 */

public class NormdatenService {

	// findAll
	public List<Anrede> alleAnreden() {
		return new AnredeDAO().findAll();
	}

	public List<Autor> alleautoren() {
		return new AutorDAO().findAll();
	}

	public List<Verlag> alleVerlage() {
		return new VerlagDAO().findAll();
	}

	public List<Schlagwort> alleSchlagworte() {
		return new SchlagwortDAO().findAll();
	}

	public List<DezKlassifikationGrpe> alleDezKlassifikationenGruppen() {
		return new DezKlassifikationGrpeDAO().findAll();
	}

	public List<DezKlassifikation> alleDezKlassifikationen() {
		return new DezKlassifikationDAO().findAll();
	}

	public List<Mitarbeiter> alleMitarbeiter() {
		return new MitarbeiterDAO().findAll();
	}


	public Verifikation speichernAutor(Autor autor) {
		Verifikation v = new Verifikation();

		if (new AutorDAO().save(autor) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Autor " + autor.getName() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Autor " + autor.getName() + " konnte nicht gespeichert werden.");
		}
		return v;
	}

	public Verifikation speichernVerlag(Verlag verlag) {
		Verifikation v = new Verifikation();
		if (new VerlagDAO().save(verlag) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Verlag " + verlag.getName() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Verlag " + verlag.getName() + " konnte nicht gespeichert werden.");
		}
		return v;
	}

	public Verifikation speichernSchlagwort(Schlagwort schlagwort) {
		Verifikation v = new Verifikation();

		if (new SchlagwortDAO().save(schlagwort) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Das Schlagwort " + schlagwort.getSchlagwort() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Schlagwort " + schlagwort.getSchlagwort() + " konnte nicht gespeichert werden.");
		}

		return v;
	}

	public Verifikation speichernMitarbeiter(Mitarbeiter mitarbeiter) {
		Verifikation v = new Verifikation();

		if (new MitarbeiterDAO().save(mitarbeiter) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " wurde gespeichert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Mitarbeiter " + mitarbeiter.getBenutzername() + " konnte nicht gespeichert werden.");
		}
		return v;
	}

	// aktualisieren
	public Verifikation aktualisierenVerlag(Verlag verlag) {
		Verifikation v = new Verifikation();
		if (new VerlagDAO().update(verlag) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Verlag " + verlag.getName() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Verlag " + verlag.getName() + " konnte nicht aktualisiert werden.");
		}
		return v;
	}

	public Verifikation aktualisierenAutor(Autor autor) {
		Verifikation v = new Verifikation();
		if (new AutorDAO().update(autor) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Autor " + autor.getName() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Autor " + autor.getName() + " konnte nicht aktualisiert werden.");
		}
		return v;
	}

	public Verifikation aktualisierenBibliothek(Bibliothek bibliothek) {
		Verifikation v = new Verifikation();
		if (new BibliothekDAO().update(bibliothek) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Die Bibliothek " + bibliothek.getName() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Die Bibliothek " + bibliothek.getName() + " konnte nicht aktualisiert werden.");
		}
		return v;
	}

	public Verifikation aktualisierenSchlagwort(Schlagwort schlagwort) {
		Verifikation v = new Verifikation();
		if (new SchlagwortDAO().update(schlagwort) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Das Schlagwort " + schlagwort.getSchlagwort() + " wurde aktualisiert.");
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Schlagwort " + schlagwort.getSchlagwort() + " konnte nicht aktualisiert werden.");
		}
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

	// Suchen
	public List<Autor> suchenAutor(Autor autor) {
		return new AutorDAO().getSelektion(autor);
	}

	public List<Verlag> suchenVerlag(Verlag verlag) {
		return new VerlagDAO().getSelektion(verlag);
	}

	public List<Mitarbeiter> suchenMitarbeiter(Mitarbeiter mitarbeiter) {
		return new MitarbeiterDAO().getSelektion(mitarbeiter);
	}

	public Bibliothek anzeigenBibliothek() {
		return new BibliothekDAO().findById(1);
	}

	public List<Schlagwort> suchenSchlagwort(Schlagwort schlagwort) {
		return new SchlagwortDAO().getSelektion(schlagwort);
	}

}
