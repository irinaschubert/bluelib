package services;

import dao.MitarbeiterDAO;
import domain.EingeloggterMA;
import domain.Mitarbeiter;

/**
 * Bietet Services zum Login an
 * 
 * @version 1.0 08.11.2018
 * @author Ueli
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
			EingeloggterMA eingeloggterMA = EingeloggterMA.getInstance();
			Mitarbeiter ma = mitarbeiterDAO.findById(id);
			eingeloggterMA.setMitarbeiter(ma);
			
		} else {
			v.setAktionErfolgreich(false);
			v.setNachricht("Das Login war nicht erfolgreich");
		}

		return v;
	}
}
