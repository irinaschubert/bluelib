package services;

import java.util.List;

import dao.AnredeDAO;
import dao.AutorDAO;
import domain.Anrede;
import domain.Autor;

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
}

