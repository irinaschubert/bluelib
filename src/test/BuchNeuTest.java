package test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.BuchDAO;
import domain.Autor;
import domain.Buch;
import domain.Schlagwort;
import domain.Status;
import domain.Verlag;
import services.MedienhandlingService;
import services.Verifikation;

public class BuchNeuTest {
	MedienhandlingService medienhandlingService;
	TestDomaenenObjekte testDomaenenObjekte;
	Buch buch;


	@Before
	public void setUp() {
		medienhandlingService = new MedienhandlingService();
		testDomaenenObjekte = new TestDomaenenObjekte();
		buch = testDomaenenObjekte.dummyBuch();

	}

	
	@Test
	public void neuTest() {
		Verifikation v = medienhandlingService.buchNeuErfassen(buch);
		assertTrue(v.isAktionErfolgreich());
	}

	@After
	public void tearDown() {
		testDomaenenObjekte.dummyBuchLoeschen();
//		BuchDAO buchDAO = new BuchDAO();
//		buchDAO.delete(buch);
//		testDomaenenObjekte.loeschenDummyAutor1();
//		testDomaenenObjekte.loeschenDummyVerlag1();
//		testDomaenenObjekte.loeschenDummySchlagwort1();
	}

}
