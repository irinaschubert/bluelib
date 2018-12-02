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
	Verifikation ver = new Verifikation();
	
	@Before
	public void setUp() throws Exception {
		v.setName("Testverlag");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.01.1970"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("31.12.1999"));
		v.setGeloescht(false);
	}

	@Test
	public void speichernTest() {
		NormdatenService n = new NormdatenService();
		assertTrue(n.sichereVerlag(v).isAktionErfolgreich());
	}
	
	@Test
	public void bearbeitenTest() {
		assertEquals(new VerlagDAO().findById(v2.getId()).getName(), TESTNAME);
	}
	
	@Test
	public void loeschenTest() {
		
	}
	
	@Test
	public void suchenTest() {
		assertTrue(n.sucheVerlag(v2).size() > 0);
	}

	@After
	public void tearDown() {
		v = null;
	}
}
