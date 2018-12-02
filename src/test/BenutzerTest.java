package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AnredeDAO;
import dao.BenutzerDAO;
import dao.MitarbeiterDAO;
import dao.OrtDAO;
import dao.StatusDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Benutzer;
import domain.Mitarbeiter;
import domain.Ort;
import domain.Status;
import hilfsklassen.DateConverter;
import services.BenutzerService;
import services.Verifikation;

public class BenutzerTest {
	Benutzer b = new Benutzer();
	BenutzerDAO benutzerDAO = new BenutzerDAO();
	Verifikation ver = new Verifikation();
	BenutzerService bs = new BenutzerService();	
	
	@Before
	public void setUp() {
		b.setName("Testbenutzer initial");
		b.setVorname("Testvorname initial");
		AnredeDAO anredeDAO = new AnredeDAO();
		Anrede anrede = anredeDAO.findById(1); // Herr
		b.setAnrede(anrede);
		OrtDAO ortDAO = new OrtDAO();
		Ort testort = ortDAO.findById(3); // Lausanne
		Adresse adresse =  new Adresse("Teststrasse 13", testort);
		b.setAdresse(adresse);
		b.setGeburtsdatum(DateConverter.convertStringToJavaDate("01.01.1970"));
		b.setTelefon("078 888 22 33");
		b.setEmail("testmailadresse@test.com");
		b.setBemerkung("Testbemerkung");
		StatusDAO statusDAO = new StatusDAO();
		Status status = statusDAO.findById(1); // aktiv
		b.setBenutzerStatus(status);
		b.setErfassungMitarbeiterId(1);
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		Mitarbeiter mitarbeiter = mitarbeiterDAO.findById(1);
		b.setErfassungMitarbeiterName(mitarbeiter.getName());
		b.setErfassungDatum(DateConverter.convertStringToJavaDate("01.01.1980"));
		bs.sichereBenutzer(b);
		List<Benutzer> benutzer = bs.sucheBenutzer(b);
		b = benutzer.get(0);
	}
	
	@Test
	public void bearbeitenTest() {
		b.setName("Testbenutzer geändert");
		b.setVorname("Testvorname geändert");
		AnredeDAO anredeDAO = new AnredeDAO();
		Anrede anrede = anredeDAO.findById(2);
		b.setAnrede(anrede);
		OrtDAO ortDAO = new OrtDAO();
		Ort testort = ortDAO.findById(8); // Prilly
		Adresse adresse =  new Adresse("Teststrasse 13 geändert", testort);
		b.setAdresse(adresse);
		b.setGeburtsdatum(DateConverter.convertStringToJavaDate("10.10.1970"));
		b.setTelefon("078 888 22 44");
		b.setEmail("testmailadresseGeaendert@test.com");
		b.setBemerkung("Testbemerkung geändert");
		StatusDAO statusDAO = new StatusDAO();
		Status status = statusDAO.findById(2); // gesperrt
		b.setBenutzerStatus(status);
		b.setErfassungMitarbeiterId(1);
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		Mitarbeiter mitarbeiter = mitarbeiterDAO.findById(1);
		b.setErfassungMitarbeiterName(mitarbeiter.getName());
		b.setErfassungDatum(DateConverter.convertStringToJavaDate("01.01.1980"));
		bs.aktualisiereBenutzer(b);
		List<Benutzer> benutzer = bs.sucheBenutzer(b);
		b = benutzer.get(0);
		
		assertEquals(b.getName(), "Testbenutzer geändert");
		assertEquals(b.getVorname(), "Testvorname geändert");
		int bAnredeId = b.getAnrede().getId();
		assertEquals(bAnredeId, 2);
		String bStrasse = b.getAdresse().getStrasse();
		assertEquals(bStrasse, "Teststrasse 13 geändert");
		String bOrt = b.getAdresse().getOrt().getOrt();
		assertEquals(bOrt, "Prilly");
		int bPLZ = b.getAdresse().getOrt().getPlz();
		assertEquals(bPLZ, 1008);
		assertEquals(b.getGeburtsdatum(), DateConverter.convertStringToJavaDate("10.10.1970"));
		assertEquals(b.getTelefon(), "078 888 22 44");
		assertEquals(b.getEmail(), "testmailadresseGeaendert@test.com");
		assertEquals(b.getBemerkung(), "Testbemerkung geändert");
		int bStatusId = b.getBenutzerStatus().getId();
		assertEquals(bStatusId, 2);
		assertEquals(b.getErfassungMitarbeiterName(), "Trösch Michael");
		assertEquals(b.getErfassungMitarbeiterId(), 1);
		assertEquals(b.getErfassungDatum(), DateConverter.convertStringToJavaDate("01.01.1980"));
	}
	
	@Test
	public void statusAendernTest() {
		StatusDAO statusDAO = new StatusDAO();
		Status s = statusDAO.findById(3);
		b.setBenutzerStatus(s);
		bs.aktualisiereBenutzer(b);
		assertEquals(b.getBenutzerStatus().getId(), 3);
	}
	
	@Test
	public void neuTest() {
		Benutzer bNeu = new Benutzer();
		bNeu.setName("Testbenutzer neu");
		bNeu.setVorname("Testvorname neu");
		AnredeDAO anredeDAO = new AnredeDAO();
		Anrede anrede = anredeDAO.findById(1); // Herr
		bNeu.setAnrede(anrede);
		OrtDAO ortDAO = new OrtDAO();
		Ort testort = ortDAO.findById(3); // Lausanne
		Adresse adresse =  new Adresse("Teststrasse 13", testort);
		bNeu.setAdresse(adresse);
		bNeu.setGeburtsdatum(DateConverter.convertStringToJavaDate("01.01.1970"));
		bNeu.setTelefon("078 888 22 33");
		bNeu.setEmail("testmailadresse@test.com");
		bNeu.setBemerkung("Testbemerkung");
		StatusDAO statusDAO = new StatusDAO();
		Status status = statusDAO.findById(1); // aktiv
		bNeu.setBenutzerStatus(status);
		bNeu.setErfassungMitarbeiterId(1);
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		Mitarbeiter mitarbeiter = mitarbeiterDAO.findById(1);
		bNeu.setErfassungMitarbeiterName(mitarbeiter.getName());
		bNeu.setErfassungDatum(DateConverter.convertStringToJavaDate("01.01.1980"));
		assertTrue(bs.sichereBenutzer(bNeu).isAktionErfolgreich());
		List<Benutzer> benutzer = bs.sucheBenutzer(bNeu);
		bNeu = benutzer.get(0);
		benutzerDAO.delete(bNeu);
	}

	@Test
	public void suchenTest() {
		assertTrue(bs.sucheBenutzer(b).size() == 1);
	}

	@After
	public void tearDown() {
		benutzerDAO.delete(b);
	}
}
