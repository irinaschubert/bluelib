package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AnredeDAO;
import dao.AusleiheDAO;
import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import dao.OrtDAO;
import dao.StatusDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Ausleihe;
import domain.Benutzer;
import domain.Buch;
import domain.Mitarbeiter;
import domain.Ort;
import domain.Status;
import hilfsklassen.DateConverter;
import services.AusleiheService;
import services.BenutzerService;
import services.Verifikation;

public class AusleiheTest {
	AusleiheDAO ausleiheDAO = new AusleiheDAO();
	BenutzerDAO benutzerDAO = new BenutzerDAO();
	Ausleihe a = new Ausleihe();
	Buch buch = new Buch();
	Benutzer benutzer = new Benutzer();
	Verifikation ver = new Verifikation();
	AusleiheService as = new AusleiheService();
	BenutzerService bs = new BenutzerService();

	@Before
	public void setUp() {

	}

	@Test
	public void ausleihenTest() {
		// Benutzer initialisieren
		benutzer.setName("Testbenutzer initial");
		benutzer.setVorname("Testvorname initial");
		AnredeDAO anredeDAO = new AnredeDAO();
		Anrede anrede = anredeDAO.findById(1); // Herr
		benutzer.setAnrede(anrede);
		OrtDAO ortDAO = new OrtDAO();
		Ort testort = ortDAO.findById(3); // Lausanne
		Adresse adresse = new Adresse("Teststrasse 13", testort);
		benutzer.setAdresse(adresse);
		benutzer.setGeburtsdatum(DateConverter.convertStringToJavaDate("01.01.1970"));
		benutzer.setTelefon("078 888 22 33");
		benutzer.setEmail("testmailadresse@test.com");
		benutzer.setBemerkung("Testbemerkung");
		StatusDAO statusDAO = new StatusDAO();
		Status status = statusDAO.findById(1); // aktiv
		benutzer.setBenutzerStatus(status);
		benutzer.setErfassungMitarbeiterId(1);
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		Mitarbeiter mitarbeiter = mitarbeiterDAO.findById(1);
		benutzer.setErfassungMitarbeiterName(mitarbeiter.getName());
		benutzer.setErfassungDatum(DateConverter.convertStringToJavaDate("01.01.1980"));
		bs.speichernBenutzer(benutzer);
		List<Benutzer> benutzerList = bs.suchenBenutzer(benutzer);
		benutzer = benutzerList.get(0);
		System.out.println("benutzer: " + benutzer.getId());
		// Buch initialisieren
		buch.setId(1);
		buch.setBemerkung("Testbemerkung");
		a.setMedium(buch);
		a.setBenutzer(benutzer);
		a.setAusleiheMitarbeiterID(1);
		a.setAusleiheMitarbeiterName("Testmitarbeiter");
		a.setAusleiheDatum(DateConverter.convertStringToJavaDate("01.01.1990"));
		assertTrue(as.speichernAusleihe(a).isAktionErfolgreich());
		List<Ausleihe> ausleihen = as.suchenAusleihenProBenutzer(benutzer);
		a = ausleihen.get(0);
		System.out.println(a.getId());
		assertEquals(a.getMedium().getId(), 1);
		assertEquals(a.getMedium().getBemerkung(), "Testbemerkung");
		assertEquals(a.getBenutzer().getId(), benutzer.getId());
		assertEquals(a.getAusleiheDatum(), DateConverter.convertStringToJavaDate("01.01.1990"));
		assertEquals(a.getRueckgabeDatum(), null);
		assertEquals(a.getAusleiheMitarbeiterID(), 1);
	}

	@Test
	public void suchenTest() {
		ausleihenTest();
		assertTrue(as.suchenAusleihenProBenutzer(benutzer).size() == 1);
	}

	@After
	public void tearDown() {
		List<Ausleihe> ausleihen = as.suchenAusleihenProBenutzer(benutzer);
		a = ausleihen.get(0);
		ausleiheDAO.deleteByAusleihe(a);
		benutzerDAO.delete(benutzer);
		a = null;
		benutzer = null;
		buch = null;
	}
}
