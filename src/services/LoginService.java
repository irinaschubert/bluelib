package services;

import dao.MitarbeiterDAO;
import domain.EingeloggterMA;

/**
 * @version 1.0 08.11.2018
 * @author irina
 *
 */
public class LoginService {

	public LoginService() {
	}

	public Verifikation loginPruefen(String name, String pw) {
		Verifikation v = new Verifikation();
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		int id = mitarbeiterDAO.loginPruefung(name, pw);
		if (id > -1) {
			v.setAktionErfolgreich(true);

			// Erstellen der Singleton-Instanz und übergeben des Mitareiterobjektes
			EingeloggterMA eingeloggterMA = EingeloggterMA.getInstance();
			eingeloggterMA.setMitarbeiter(mitarbeiterDAO.findById(id));
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Login war nicht erfolgreich");
		}

		return v;
	}
}
