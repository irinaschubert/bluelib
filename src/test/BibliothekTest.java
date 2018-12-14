package test;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.BibliothekDAO;
import dao.OrtDAO;
import domain.Adresse;
import domain.Bibliothek;
import domain.Ort;
import services.BibliothekService;
import services.Verifikation;

public class BibliothekTest {
	Bibliothek b = new Bibliothek();
	Bibliothek bZumVergleich = new Bibliothek();
	BibliothekDAO bibliothekDAO = new BibliothekDAO();
	Verifikation ver = new Verifikation();
	BibliothekService bs = new BibliothekService();
	OrtDAO ortDAO = new OrtDAO();
	Ort ort = ortDAO.findById(3);
	Ort ortZumVergleich = ortDAO.findById(8);
	String strasse = new String("abcdStrasse 12");
	String strasseZumVergleich = new String("abcdStrasse 18");
	Adresse adresse = new Adresse(strasse, ort);
	Adresse adresseZumVergleich = new Adresse(strasseZumVergleich, ortZumVergleich);

	@Before
	public void setUp() {
		b.setName("Bluelib");
		b.setEmail("info@bluelib.ch");
		b.setTelefon("+41791234567");
		b.setAdresse(adresse);
		b.setLeihfrist(5);
		bs.aktualisierenBibliothek(b);
		b = bs.suchenBibliothek();
	}

	@Test
	public void aktualisierenTest() {
		b.setName("TestBibliothek geändert");
		b.setEmail("Email#@Email.email");
		b.setTelefon("123123123");
		b.setAdresse(adresseZumVergleich);
		b.setLeihfrist(50);
		bs.aktualisierenBibliothek(b);
		b = bs.suchenBibliothek();
		assertEquals(b.getName(), "TestBibliothek geändert");
		assertEquals(b.getEmail(), "Email#@Email.email");
		assertEquals(b.getTelefon(), "123123123");
		assertEquals(b.getAdresse().getStrasse(), "abcdStrasse 18");
		assertEquals(b.getAdresse().getOrt().getId(), 8);
		assertEquals(b.getLeihfrist(), 50);
	}

	@After
	public void tearDown() {
		b = null;
	}
}
