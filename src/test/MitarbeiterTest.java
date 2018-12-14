package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import domain.Mitarbeiter;
import services.MitarbeiterService;
import services.NormdatenService;
import services.Verifikation;

public class MitarbeiterTest {
	Mitarbeiter m = new Mitarbeiter();
	Mitarbeiter mZumVergleich = new Mitarbeiter();
	MitarbeiterDAO MitarbeiterDAO = new MitarbeiterDAO();
	BenutzerDAO BenutzerDAO = new BenutzerDAO();
	Verifikation ver = new Verifikation();
	NormdatenService n = new NormdatenService();
	MitarbeiterService ms = new MitarbeiterService();

	@Before
	public void setUp() {
		m.setName("TestMitarbeiter initial");
		m.setVorname("Vorname");
		m.setBenutzername("Benutzername");
		m.setPasswort("Passwort");
		m.setAktiv(false);
		m.setAdmin(false);
		n.speichernMitarbeiter(m);
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(m);
		m = Mitarbeitere.get(0);
	}

	@Test
	public void aktualisierenTest() {
		m.setName("TestMitarbeiter geändert");
		m.setVorname("Vorname1");
		m.setBenutzername("Benutzername1");
		m.setPasswort("Passwort1");
		m.setAktiv(true);
		m.setAdmin(true);
		n.aktualisierenMitarbeiter(m);
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(m);
		m = Mitarbeitere.get(0);
		assertEquals(m.getName(), "TestMitarbeiter geändert");
		assertEquals(m.getVorname(), "Vorname1");
		assertEquals(m.getBenutzername(), "Benutzername1");
		assertEquals(m.getBenutzername(), "Passwort1");
		assertEquals(m.isAktiv(), true);
		assertEquals(m.isAdmin(), true);
	}

	@Test
	public void neuTest() {
		Mitarbeiter mNeu = new Mitarbeiter();
		mNeu.setName("TestMitarbeiter inigeänderttial");
		mNeu.setVorname("Vorname1");
		mNeu.setBenutzername("Benutzername1");
		mNeu.setPasswort("Passwort1");
		mNeu.setAktiv(true);
		mNeu.setAdmin(true);
		assertTrue(n.speichernMitarbeiter(mNeu).isAktionErfolgreich());
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(mNeu);
		mNeu = Mitarbeitere.get(0);
		MitarbeiterDAO.delete(mNeu);
	}

	@Test
	public void adminTest() {
		m.setAdmin(true);
		n.aktualisierenMitarbeiter(m);
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(m);
		m = Mitarbeitere.get(0);
		assertTrue(m.isAdmin());
	}
	
	public void aktivTest() {
		m.setAktiv(true);
		n.aktualisierenMitarbeiter(m);
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(m);
		m = Mitarbeitere.get(0);
		assertTrue(m.isAktiv());
	}

	@Test
	public void suchenTest() {
		assertTrue(n.suchenMitarbeiter(m).size() == 1);
	}

	@After
	public void tearDown() {
		List<Mitarbeiter> Mitarbeitere = n.suchenMitarbeiter(m);
		m = Mitarbeitere.get(0);
		//BenutzerDAO.updateMitarbeiterID(m.getId(),m.getMAId());
		MitarbeiterDAO.delete(m);
	}
}
