package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.Buch;
import services.MedienhandlingService;

/**
 * Prueft, ob die Suche nach einen Buch korrekt funktioniert
 * 
 * @version 1.0 2018-12-10
 * @author Ueli
 *
 */

public class BuchSuchenTest {
	TestDomaenenObjekte testDomaenenObjekte;
	MedienhandlingService medienhandlingService;
	Buch buch;
	List<Buch> buchListe;


	@Before
	public void setUp() {
	
		medienhandlingService = new MedienhandlingService();
		testDomaenenObjekte = new TestDomaenenObjekte();
		buch = testDomaenenObjekte.getFertigesDummyBuch();
		buchListe = new ArrayList<>();
		buchListe = medienhandlingService.suchenBuch(buch);
		
	}

	
	@Test
	public void suchenTest() {
		assertTrue(buchListe.size() > 0);

	}

	@After
	public void tearDown() {
		testDomaenenObjekte.loeschenDummyBuch();
	}

}
