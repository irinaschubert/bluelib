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
import services.NormdatenService;
import services.Verifikation;

public class BibliothekTest {
	Bibliothek b = new Bibliothek();
	Bibliothek bZumVergleich = new Bibliothek();
	BibliothekDAO BibliothekDAO = new BibliothekDAO();
	Verifikation ver = new Verifikation();
	NormdatenService n = new NormdatenService();
	OrtDAO ortDAO = new OrtDAO();
	Ort ort = ortDAO.findById(3);
	String strasse = new String("abcdStrasse Nr. 12");
	Adresse adresse = new Adresse(strasse, ort);

	@Before
	public void setUp() {
		b.setName("Bluelib");
		b.setEmail("info@bluelib.ch");
		b.setTelefon("+41791234567");
		b.setAdresse(adresse);
		b.setLeihfrist(5);
		n.aktualisierenBibliothek(b);
		b = n.anzeigenBibliothek();
	}

	@Test
	public void aktualisierenTest() {
		b.setName("TestBibliothek geändert");
		b.setEmail("Email#@Email.email");
		b.setTelefon("123");
		b.setAdresse(adresse);
		b.setLeihfrist(5);
		n.aktualisierenBibliothek(b);
		b = n.anzeigenBibliothek();
		assertEquals(b.getName(), "TestBibliothek geändert");
		assertEquals(b.getEmail(), "Email#@Email.email");
		assertEquals(b.getTelefon(), "123");
		assertEquals(b.getAdresse().getStrasse(), "Str 2");
		assertEquals(b.getLeihfrist(), 5);
	}

	@After
	public void tearDown() {
		b.setName("Bluelib");
		b.setEmail("info@bluelib.ch");
		b.setTelefon("+41791234567");
		b.setAdresse(adresse);
		b.setLeihfrist(5);
		n.aktualisierenBibliothek(b);
		b = n.anzeigenBibliothek();
	}
}
