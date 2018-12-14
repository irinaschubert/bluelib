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

/**
 * Prueft, ob alle Werte des Buchs korrekt auktualisiert werden
 * 
 * @version 1.0 2018-12-10
 * @author Ueli
 *
 */

public class BuchAktualisierenTest {
	MedienhandlingService medienhandlingService;
	TestDomaenenObjekte testDomaenenObjekte;
	Buch buch;
	Buch buchUpdate;

	@Before
	public void setUp() {
		medienhandlingService = new MedienhandlingService();
		testDomaenenObjekte = new TestDomaenenObjekte();
		buch = testDomaenenObjekte.getDummyBuch();
		medienhandlingService.speichernBuch(buch);
		List<Buch> bL = new ArrayList<>();
		bL = medienhandlingService.suchenBuch(buch);
		if (bL.size() > 0) {
			buch = bL.get(0);
		}

	}

	@Test
	public void bearbeitenTest() {
		String titel = "Dummy-Titel 1";
		int anzahlSeiten = 31;
		String auflage = "4. Auflage";
		Autor autor = testDomaenenObjekte.getFertigerDummyAutor2();
		List<Autor> autoren = new ArrayList<>();
		autoren.add(autor);
		int barcode = 1111110;
		String bemerkung = "Dummy-Bemerkung Buch 2";
		int jahr = 1951;
		String ort = "Berlin";
		long isbn = 1234567891;
		String preis = "30.60";
		String reihe = "Dummy-Reihe 2";
		Schlagwort schlagwort = testDomaenenObjekte.getFertigesDummySchlagwort2();
		List<Schlagwort> schlagwoerter = new ArrayList<>();
		schlagwoerter.add(schlagwort);
		Verlag verlag = testDomaenenObjekte.getFertigerDummyVerlag2();
		String signatur = "DMY2";
		Status status = testDomaenenObjekte.getMedienStatusGesperrt();

		buch.setTitel(titel);
		buch.setAnzahlSeiten(anzahlSeiten);
		buch.setAuflage(auflage);
		buch.setAutoren(autoren);
		buch.setBarcodeNr(barcode);
		buch.setBemerkung(bemerkung);
		buch.setErscheinungsJahr(jahr);
		buch.setErscheinungsOrt(ort);
		buch.setIsbn(isbn);
		buch.setPreis(new BigDecimal(preis));
		buch.setReihe(reihe);
		buch.setSchlagwoerter(schlagwoerter);
		buch.setVerlag(verlag);
		buch.setSignatur(signatur);
		buch.setStatus(status);

		medienhandlingService.aktualisierenBuch(buch);
		List<Buch> bL = new ArrayList<>();
		bL = medienhandlingService.suchenBuch(buch);
		if (bL.size() > 0) {
			buchUpdate = bL.get(0);
		}

		assertEquals(buchUpdate.getTitel(), titel);
		assertEquals(buchUpdate.getAnzahlSeiten(), anzahlSeiten);
		List<Autor> aLT = new ArrayList<>();
		aLT = buchUpdate.getAutoren();
		assertEquals(aLT.get(0).getId(), autor.getId());
		assertEquals(buchUpdate.getBarcodeNr(), barcode);
		assertEquals(buchUpdate.getBemerkung(), bemerkung);
		assertEquals(buchUpdate.getErscheinungsJahr(), jahr);
		assertEquals(buchUpdate.getErscheinungsOrt(), ort);
		assertEquals(buchUpdate.getIsbn(), isbn);
		assertEquals(buchUpdate.getPreis(), new BigDecimal(preis));
		assertEquals(buchUpdate.getReihe(), reihe);
		List<Schlagwort> sLT = new ArrayList<>();
		sLT = buchUpdate.getSchlagwoerter();
		assertEquals(sLT.get(0).getId(), schlagwort.getId());
		assertEquals(buchUpdate.getVerlag().getId(), verlag.getId());
		assertEquals(buchUpdate.getSignatur(), signatur);
		assertEquals(buchUpdate.getStatus().getId(), status.getId());

	}

	@After
	public void tearDown() {
		BuchDAO buchDAO = new BuchDAO();
		buchDAO.delete(buchUpdate);
		testDomaenenObjekte.loeschenDummyAutor1();
		testDomaenenObjekte.loeschenDummyVerlag1();
		testDomaenenObjekte.loeschenDummySchlagwort1();
		testDomaenenObjekte.loeschenDummyAutor2();
		testDomaenenObjekte.loeschenDummyVerlag2();
		testDomaenenObjekte.loeschenDummySchlagwort2();
	}

}
