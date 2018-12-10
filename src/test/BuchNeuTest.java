package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Buch;
import services.MedienhandlingService;
import services.Verifikation;

/**
 * Prueft, ob ein Buch korrekt neu erstellt wird
 * 
 * @version 1.0 2018-12-10
 * @author Ueli
 *
 */

public class BuchNeuTest {
	MedienhandlingService medienhandlingService;
	TestDomaenenObjekte testDomaenenObjekte;
	Buch buch;


	@Before
	public void setUp() {
		medienhandlingService = new MedienhandlingService();
		testDomaenenObjekte = new TestDomaenenObjekte();
		buch = testDomaenenObjekte.getDummyBuch();

	}

	
	@Test
	public void neuTest() {
		Verifikation v = medienhandlingService.speichernBuch(buch);
		assertTrue(v.isAktionErfolgreich());
	}

	@After
	public void tearDown() {
		testDomaenenObjekte.loeschenDummyBuch();
//		BuchDAO buchDAO = new BuchDAO();
//		buchDAO.delete(buch);
//		testDomaenenObjekte.loeschenDummyAutor1();
//		testDomaenenObjekte.loeschenDummyVerlag1();
//		testDomaenenObjekte.loeschenDummySchlagwort1();
	}

}
