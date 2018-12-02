package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
		v = verlagDAO.save(v);
	}
	
	@Test
	public void bearbeitenTest() {
		v.setName("Testverlag geändert");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("05.05.1975"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("01.01.2000"));
		v.setGeloescht(true);
		verlagDAO.update(v);
		vZumVergleich.setName("Testverlag geändert");
		vZumVergleich.setGruendungsDatum(DateConverter.convertStringToJavaDate("05.05.1975"));
		vZumVergleich.setEndDatum(DateConverter.convertStringToJavaDate("01.01.2000"));
		vZumVergleich.setGeloescht(true);
		vZumVergleich = verlagDAO.save(vZumVergleich);
		assertEquals(v.getName(), vZumVergleich.getName());
		assertEquals(v.getGruendungsDatum(), vZumVergleich.getGruendungsDatum());
		assertEquals(v.getEndDatum(), vZumVergleich.getEndDatum());
		assertEquals(v.getGeloescht(), vZumVergleich.getGeloescht());
		verlagDAO.delete(vZumVergleich);
	}
	
	@Test
	public void loeschenTest() {
		v.setGeloescht(true);
		assertTrue(v.getGeloescht());
	}
	
	@Test
	public void suchenTest() {
		assertTrue(n.sucheVerlag(v).size() == 1);
	}

	@After
	public void tearDown() {
		verlagDAO.delete(v);
	}
}
