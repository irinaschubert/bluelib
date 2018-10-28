package services;

import java.util.List;

import dao.AnredeDAO;
import dao.AutorDAO;
import dao.VerlagDAO;
import domain.Anrede;
import domain.Autor;
import domain.Verlag;

/**
 * @version 0.2 18.10.2018
 * @author Schmutz
 *
 */

public class NormdatenService {

	public List<Anrede> alleAnreden(){
		return new AnredeDAO().findAll();
	}
	
	public List<Autor> alleautoren(){
		return new AutorDAO().findAll();
	}
	
	public List<Verlag> alleVerlage(){
		return new VerlagDAO().findAll();
	}
	
	public Verifikation sichereAutor(Autor autor) {
		Verifikation v = new Verifikation();
		
		if (new AutorDAO().save(autor) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Autor "
					+ autor.getName()
					+" wurde gespeichert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Autor "
					+ autor.getName()
					+" konnte nicht gespeichert werden.");
		}
		
		return v;
	}
	
	public Verifikation sichereVerlag(Verlag verlag) {
		Verifikation v = new Verifikation();
		
		if (new VerlagDAO().save(verlag) != null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Verlag "
					+ verlag.getName()
					+" wurde gespeichert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Verlag "
					+ verlag.getName()
					+" konnte nicht gespeichert werden.");
		}
		
		return v;
	}
	
	public Verifikation aktualisiereAutor(Autor autor) {
		Verifikation v = new Verifikation();
		if (new AutorDAO().update(autor)!= null) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Der Verlag "
					+ autor.getName()
					+" wurde aktualisiert.");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Der Verlag "
					+ autor.getName()
					+" konnte nicht aktualisiert werden.");
		}
		return v;	
	}
}

