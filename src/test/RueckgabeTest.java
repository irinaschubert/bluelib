package test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dao.AusleiheDAO;
import domain.Ausleihe;
import services.AusleiheService;
import services.MedienhandlingService;
import services.RueckgabeService;

/**
 * Prueft, ob die Autorensuche korrekt funktioniert
 * 
 * @version 1.0 2018-12-10
 * @author Ueli
 *
 */

public class RueckgabeTest {

	MedienhandlingService medienhandlingService;
	TestDomaenenObjekte testDomaenenObjekte;
	AusleiheService ausleiheService;
	RueckgabeService rueckgabeService;
	Ausleihe ausleihe;
	AusleiheDAO ausleiheDAO;

	@Before
	public void setUp() {
		testDomaenenObjekte = new TestDomaenenObjekte();
		ausleihe = new Ausleihe();
		ausleihe.setAusleiheDatum(new Date());
		ausleihe.setAusleiheMitarbeiterID(testDomaenenObjekte.getFertigerMitarbeiter().getId());
		ausleihe.setBenutzer(testDomaenenObjekte.getFertigerDummyBenutzer());
		ausleihe.setMedium(testDomaenenObjekte.getFertigesDummyBuch());
		ausleiheService = new AusleiheService();
		ausleiheService.speichernAusleihe(ausleihe);

	}

	@Test
	public void test() {
		ausleihe.setRueckgabeDatum(new Date());
		ausleihe.setRueckgabeMitarbeiterID(testDomaenenObjekte.getFertigerMitarbeiter().getId());
		rueckgabeService = new RueckgabeService();
		rueckgabeService.rueckgabe(ausleihe);
		assertTrue(rueckgabeService.istAusgeliehen(ausleihe.getMedium().getId()));

	}

	@After
	public void tearDown() {
		List<Ausleihe> ausleihen = ausleiheService.suchenAusleihenProBenutzer(ausleihe.getBenutzer());
		Ausleihe a = ausleihen.get(0);
		ausleiheDAO = new AusleiheDAO();
		ausleiheDAO.deleteByAusleihe(a);
		testDomaenenObjekte.loeschenDummyBenutzer();
		testDomaenenObjekte.loeschenDummyBuch();
	}

}
