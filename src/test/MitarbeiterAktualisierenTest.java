package test;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import domain.Benutzer;
import domain.Mitarbeiter;
import services.HashRechner;
import services.MitarbeiterService;
import services.NormdatenService;

/**
 * Prueft, ob ein Mitarbeiter korrekt aktualisiert wird
 * 
 * @version 2.0 2018-12-10
 * @author Mike
 *
 */

public class MitarbeiterAktualisierenTest {
	Mitarbeiter m = new Mitarbeiter();
	MitarbeiterDAO MitarbeiterDAO = new MitarbeiterDAO();
	BenutzerDAO BenutzerDAO = new BenutzerDAO();
	NormdatenService n = new NormdatenService();
	MitarbeiterService ms = new MitarbeiterService();
	TestDomaenenObjekte testDomaenenObjekte;

	@Before
	public void setUp() {
		testDomaenenObjekte = new TestDomaenenObjekte();
		Benutzer b = testDomaenenObjekte.getFertigerDummyBenutzer();
		m.setBenutzername("Benutzername");
		m.setPasswort("Passwort");
		m.setAktiv(false);
		m.setAdmin(false);
		m.setId(b.getId());
		n.speichernMitarbeiter(m);
		List<Mitarbeiter> Mitarbeiter = n.suchenMitarbeiter(m);
		m = Mitarbeiter.get(0);
	}

	@Test
	public void BearbeitenTest() {
		m.setBenutzername("Benutzername1");
		m.setPasswort("Passwort1");
		m.setAktiv(true);
		m.setAdmin(true);
		n.aktualisierenMitarbeiter(m);
		List<Mitarbeiter> mL = n.suchenMitarbeiter(m);
		Mitarbeiter mResult = mL.get(0);
		assertEquals(mResult.getBenutzername(), "Benutzername1");
		assertEquals(mResult.getPasswort(), new HashRechner().hashBerechnen("Passwort1"));
		assertEquals(mResult.isAktiv(), true);
		assertEquals(mResult.isAdmin(), true);
	}

	@After
	public void tearDown() {
		testDomaenenObjekte.loeschenDummyBenutzer();
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		mitarbeiterDAO.delete(m);

	}

}
