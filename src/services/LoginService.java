package services;

import dao.AutorDAO;
import dao.MitarbeiterDAO;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class LoginService {

	public LoginService() {}
	
	public Verifikation loginPruefen(String name, String pw) {
		Verifikation v = new Verifikation();
		if (new MitarbeiterDAO().loginPruefung(name, pw)) {
			v.setAktionErfolgreich(true);
			v.setNachricht("Willkommmen");
		}
		else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Login war nicht erfolgreich");
		}
		
		return v;
	}
}
