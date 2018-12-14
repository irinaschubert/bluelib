package services;

import dao.BibliothekDAO;
import domain.Bibliothek;

/**
 * Stellt die Services zum Verwalten der Bibliothek zur Verfügung.
 * 
 * @version 1.0 15.11.2018
 * @author Irina
 * 
 */

public class BibliothekService {

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

	/**
	*Gibt das Bibliotheksobjekt zurück. Es darf nur eines geben. Deshalb ist die id immer 1.
	*
	*@return Bibliotheksobjekt 
	*/
	public Bibliothek suchenBibliothek() {
		return new BibliothekDAO().findById(1);
	}
}
