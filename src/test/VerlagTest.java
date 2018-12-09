package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.VerlagDAO;
import domain.Verlag;
import hilfsklassen.DateConverter;
import services.NormdatenService;
import services.Verifikation;

public class VerlagTest {
	Verlag v = new Verlag();
	Verlag vZumVergleich = new Verlag();
	VerlagDAO verlagDAO = new VerlagDAO();
	Verifikation ver = new Verifikation();
	NormdatenService n = new NormdatenService();

	@Before
	public void setUp() {
		v.setName("Testverlag initial");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.01.1970"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("31.12.1999"));
		v.setGeloescht(false);
		n.sichereVerlag(v);
		List<Verlag> verlage = n.sucheVerlag(v);
		v = verlage.get(0);
	}

	@Test
	public void aktualisierenTest() {
		v.setName("Testverlag geändert");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("05.05.1975"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("01.01.2000"));
		v.setGeloescht(true);
		n.aktualisiereVerlag(v);
		List<Verlag> verlage = n.sucheVerlag(v);
		v = verlage.get(0);
		assertEquals(v.getName(), "Testverlag geändert");
		assertEquals(v.getGruendungsDatum(), DateConverter.convertStringToJavaDate("05.05.1975"));
		assertEquals(v.getEndDatum(), DateConverter.convertStringToJavaDate("01.01.2000"));
		assertEquals(v.getGeloescht(), true);
	}

	@Test
	public void neuTest() {
		Verlag vNeu = new Verlag();
		vNeu.setName("Testverlag neu");
		vNeu.setGruendungsDatum(DateConverter.convertStringToJavaDate("05.05.1975"));
		vNeu.setEndDatum(DateConverter.convertStringToJavaDate("01.01.2000"));
		vNeu.setGeloescht(false);
		assertTrue(n.sichereVerlag(vNeu).isAktionErfolgreich());
		List<Verlag> verlage = n.sucheVerlag(vNeu);
		vNeu = verlage.get(0);
		verlagDAO.delete(vNeu);
	}

	@Test
	public void loeschenTest() {
		v.setGeloescht(true);
		n.aktualisiereVerlag(v);
		List<Verlag> verlage = n.sucheVerlag(v);
		v = verlage.get(0);
		assertTrue(v.getGeloescht());
	}

	@Test
	public void suchenTest() {
		assertTrue(n.sucheVerlag(v).size() == 1);
	}

	@After
	public void tearDown() {
		List<Verlag> verlage = n.sucheVerlag(v);
		v = verlage.get(0);
		verlagDAO.delete(v);
	}
}
