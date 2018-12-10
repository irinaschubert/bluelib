package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Buch;
import services.MedienhandlingService;

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
		buchListe = medienhandlingService.buchSuchen(buch);
		
	}

	
	@Test
	public void suchenTest() {
		assertTrue(buchListe.size() > 0);

	}

	@After
	public void tearDown() {
		testDomaenenObjekte.loeschenDummyBuch();
//		BuchDAO buchDAO = new BuchDAO();
//		buchDAO.delete(buchListe.get(0));
//		testDomaenenObjekte.loeschenDummyAutor1();
//		testDomaenenObjekte.loeschenDummyVerlag1();
//		testDomaenenObjekte.loeschenDummySchlagwort1();
	}

}
