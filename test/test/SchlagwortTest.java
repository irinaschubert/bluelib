package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.SchlagwortDAO;
import domain.Schlagwort;
import services.NormdatenService;
import services.Verifikation;

public class SchlagwortTest {
	Schlagwort s = new Schlagwort();
	Schlagwort sZumVergleich = new Schlagwort();
	SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
	Verifikation ver = new Verifikation();
	NormdatenService n = new NormdatenService();

	@Before
	public void setUp() {
		s.setSchlagwort("Testschlagwort initial");
		s.setGeloescht(false);
		n.speichernSchlagwort(s);
		List<Schlagwort> schlagworte = n.suchenSchlagwort(s);
		s = schlagworte.get(0);
	}

	@Test
	public void aktualisierenTest() {
		s.setSchlagwort("Testschlagwort geändert");
		s.setGeloescht(true);
		n.aktualisierenSchlagwort(s);
		List<Schlagwort> schlagworte = n.suchenSchlagwort(s);
		s = schlagworte.get(0);
		assertEquals(s.getSchlagwort(), "Testschlagwort geändert");
		assertEquals(s.getGeloescht(), true);
	}

	@Test
	public void neuTest() {
		Schlagwort sNeu = new Schlagwort();
		sNeu.setSchlagwort("Testschlagwort neu");
		sNeu.setGeloescht(false);
		assertTrue(n.speichernSchlagwort(sNeu).isAktionErfolgreich());
		List<Schlagwort> schlagworte = n.suchenSchlagwort(sNeu);
		sNeu = schlagworte.get(0);
		schlagwortDAO.delete(sNeu);
	}

	@Test
	public void loeschenTest() {
		s.setGeloescht(true);
		n.aktualisierenSchlagwort(s);
		List<Schlagwort> schlagworte = n.suchenSchlagwort(s);
		s = schlagworte.get(0);
		assertTrue(s.getGeloescht());
	}

	@Test
	public void suchenTest() {
		assertTrue(n.suchenSchlagwort(s).size() == 1);
	}

	@After
	public void tearDown() {
		List<Schlagwort> schlagworte = n.suchenSchlagwort(s);
		s = schlagworte.get(0);
		schlagwortDAO.delete(s);
	}
}
