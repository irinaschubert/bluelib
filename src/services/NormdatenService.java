package services;

import java.util.List;

import dao.AnredeDAO;
import dao.AutorDAO;
import dao.VerlagDAO;
import dao.BibliothekDAO;
import dao.DezKlassifikationGrpeDAO;
import dao.SchlagwortDAO;
import domain.Anrede;
import domain.Autor;
import domain.Verlag;
import domain.Bibliothek;
import domain.DezKlassifikationGrpe;
import domain.Schlagwort;

/**
 * @version 1.0 15.11.2018
 * @author Schmutz
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

	// sichern
	public Verifikation sichereAutor(Autor autor) {
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

	public Verifikation sichereVerlag(Verlag verlag) {
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

	public Verifikation sichereSchlagwort(Schlagwort schlagwort) {
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

	// aktualisiern
	public Verifikation aktualisiereVerlag(Verlag verlag) {
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

	public Verifikation aktualisiereAutor(Autor autor) {
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

	public Verifikation aktualisiereBibliothek(Bibliothek bibliothek) {
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

	public Verifikation aktualisiereSchlagwort(Schlagwort schlagwort) {
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

	// Suchen
	public List<Autor> sucheAutor(Autor autor) {
		return new AutorDAO().getSelektion(autor);
	}

	public List<Verlag> sucheVerlag(Verlag verlag) {
		return new VerlagDAO().getSelektion(verlag);
	}

	public List<Schlagwort> sucheSchlagwort(Schlagwort schlagwort) {
		return new SchlagwortDAO().getSelektion(schlagwort);
	}

	public Bibliothek bibliothekAnzeigen() {
		return new BibliothekDAO().findById(1);
	}
}
