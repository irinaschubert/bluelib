package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.AutorDAO;
import dao.BenutzerDAO;
import dao.BuchDAO;
import dao.MitarbeiterDAO;
import dao.SchlagwortDAO;
import dao.VerlagDAO;
import domain.Adresse;
import domain.Anrede;
import domain.Autor;
import domain.Benutzer;
import domain.Buch;
import domain.Mitarbeiter;
import domain.Ort;
import domain.Schlagwort;
import domain.Status;
import domain.Verlag;
import hilfsklassen.DateConverter;
import services.BenutzerService;
import services.MedienhandlingService;
import services.NormdatenService;

/**
 * Stellt Objekte für die Unittests zur Verfuegung
 * 
 * @version 1.0 2018-12-10
 * @author Ueli
 *
 */

public class TestDomaenenObjekte {
	private NormdatenService normdatenService;
	private Buch buch;
	private Schlagwort schlagwort1;
	private Schlagwort schlagwort2;
	private Autor autor1;
	private Autor autor2;
	private Benutzer benutzer;
	private Verlag verlag1;
	private Verlag verlag2;

	public TestDomaenenObjekte() {
		normdatenService = new NormdatenService();
	}

	/**
	 * Vollstaendig ausgefülltest, nicht in DB gespeichertes Buch-Objekt ohne id
	 * 
	 * @return Buch-Objekt 
	 */
	public Buch getDummyBuch() {
		Buch b = new Buch();
		b.setTitel("Dummy-Titel");
		b.setAnzahlSeiten(30);
		b.setAuflage("3. Auflage");
		b.setAutor(getFertigerDummyAutor1());
		b.setBarcodeNr(1111111);
		b.setBemerkung("Dummy-Bemerkung Buch");
		b.setErfasserId(1);
		b.setErfasserName("Dummy-Erfasser");
		b.setErfassungDatum(new Date());
		b.setErscheinungsJahr(1950);
		b.setErscheinungsOrt("Bern");
		b.setIsbn(1234567890);
		b.setPreis(new BigDecimal("30.50"));
		b.setReihe("Dummy-Reihe");
		b.setSchlagwort(getFertigesDummySchlagwort1());
		b.setVerlag(getFertigerDummyVerlag1());
		b.setSignatur("DMY");
		b.setStatus(getMedienStatusAktiv());

		return b;

	}

	/**
	 * 
	 * @return in DB neu erstelltes Buch-Objekt
	 */
	public Buch getFertigesDummyBuch() {
		if (buch == null) {
			MedienhandlingService medienhandlingService = new MedienhandlingService();
			buch = getDummyBuch();
			medienhandlingService.speichernBuch(buch);
			List<Buch> bL = new ArrayList<>();
			bL = medienhandlingService.suchenBuch(buch);
			buch = bL.get(0);
		}
		return buch;

	}

	/**
	 * Loescht das Dummy-Buch mit den zugehoerigen Verlag Autor, und Schlagwort in der DB
	 */
	public void loeschenDummyBuch() {
		BuchDAO buchDAO = new BuchDAO();
		Boolean r = buchDAO.delete(buch);
		loeschenDummyVerlag1();
		loeschenDummyAutor1();
		loeschenDummySchlagwort1();
	}
/**
 *Vollstaendig ausgefülltest, nicht in DB gespeichertes Autoren-Objekt ohne id
 * 
 * @return Autor
 */
	public Autor getDummyAutor1() {
		Autor a = new Autor();
		a.setName("Dummy-Autor 1");
		a.setVorname("Vorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("11.09.1963"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.01.2015"));
		a.setGeloescht(false);
		return a;
	}

	/**
	 * 
	 * @return in DB neu erstellter Autor
	 */
	public Autor getFertigerDummyAutor1() {
		if (autor1 == null) {
			normdatenService.speichernAutor(getDummyAutor1());
			List<Autor> aL = new ArrayList<>();
			aL = normdatenService.suchenAutor(getDummyAutor1());
			autor1 = aL.get(0);
		}
		return autor1;
	}

	/**
	 * Loescht den Dummy-Autor1 in der DB
	 */
	public void loeschenDummyAutor1() {
		AutorDAO autorDAO = new AutorDAO();
		autorDAO.delete(autor1);
	}
	
	/**
	 *Vollstaendig ausgefülltest, nicht in DB gespeichertes Autoren-Objekt ohne id
	 * 
	 * @return Autor
	 */
	public Autor getDummyAutor2() {
		Autor a = new Autor();
		a.setName("Dummy-Autor 2");
		a.setVorname("Vorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("11.09.1963"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.01.2015"));
		a.setGeloescht(false);
		return a;
	}
	
	/**
	 * 
	 * @return in DB neu erstellter Autor
	 */
	public Autor getFertigerDummyAutor2() {
		if (autor2 == null) {
			normdatenService.speichernAutor(getDummyAutor2());
			List<Autor> aL = new ArrayList<>();
			aL = normdatenService.suchenAutor(getDummyAutor2());
			autor2 = aL.get(0);
		}
		return autor2;
	}

	/**
	 * Loescht den Dummy-Autor2 in der DB
	 */
	public void loeschenDummyAutor2() {
		AutorDAO autorDAO = new AutorDAO();
		autorDAO.delete(autor2);
	}

	/**
	 *Vollstaendig ausgefülltest, nicht in DB gespeichertes Schlagwort-Objekt ohne id
	 * 
	 * @return Schlagwort
	 */
	public static Schlagwort getDummySchlagwort1() {
		Schlagwort s = new Schlagwort();
		s.setSchlagwort("Dummy-Schlagwort 1000");
		s.setGeloescht(false);
		return s;
	}

	/**
	 * 
	 * @return in DB neu erstelltes Schlagwort
	 */
	public Schlagwort getFertigesDummySchlagwort1() {
		if (schlagwort1 == null) {
			normdatenService.speichernSchlagwort(getDummySchlagwort1());
			List<Schlagwort> sL = new ArrayList<>();
			sL = normdatenService.suchenSchlagwort(getDummySchlagwort1());
			schlagwort1 = sL.get(0);
		}
		return schlagwort1;
	}

	/**
	 * Loescht das Schlagwort in der DB
	 */
	public void loeschenDummySchlagwort1() {
		SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
		Boolean r = schlagwortDAO.delete(schlagwort1);
	}

	/**
	 *Vollstaendig ausgefülltest, nicht in DB gespeichertes Schlagwort-Objekt ohne id
	 * 
	 * @return Schlagwort
	 */
	public static Schlagwort getDummySchlagwort2() {
		Schlagwort s = new Schlagwort();
		s.setSchlagwort("Dummy-Schlagwort 1001");
		s.setGeloescht(false);
		return s;
	}

	/**
	 * 
	 * @return in DB neu erstelltes Schlagwort
	 */
	public Schlagwort getFertigesDummySchlagwort2() {
		if (schlagwort2 == null) {
			normdatenService.speichernSchlagwort(getDummySchlagwort2());
			List<Schlagwort> sL = new ArrayList<>();
			sL = normdatenService.suchenSchlagwort(getDummySchlagwort2());
			schlagwort2 = sL.get(0);
		}
		return schlagwort2;
	}

	/**
	 * Loescht das Schlagwort in der DB
	 */
	public void loeschenDummySchlagwort2() {
		SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
		Boolean r = schlagwortDAO.delete(schlagwort2);
	}


	/**
	 *Vollstaendig ausgefülltes, nicht in DB gespeichertes Benutzer-Objekt ohne id
	 * 
	 * @return Benutzer
	 */
	public Benutzer getDummyBenutzer() {
		Benutzer b = new Benutzer();
		b.setName("Dummy-Benutzer");
		b.setVorname("Vorname");
		b.setEmail("dummybenutze@test.com");
		b.setAnrede(getAnredeHerr());
		b.setAdresse(getDummyAdresse());
		b.setBemerkung("Dummy-Bemerkung");
		b.setBenutzerStatus(getBenuterStatusAktiv());
		b.setErfassungDatum(new Date());
		b.setErfassungMitarbeiterId(1);
		b.setErfassungMitarbeiterName("Dummy-Mitarbeiter");
		b.setTelefon("055 555 55 55");
		return b;

	}

	/**
	 * 
	 * @return in DB neu erstellter Benutzer
	 */
	public Benutzer getFertigerDummyBenutzer() {
		if (benutzer == null) {
			BenutzerService benutzerService = new BenutzerService();
			benutzerService.speichernBenutzer(getDummyBenutzer());
			List<Benutzer> bL = new ArrayList<>();
			bL = benutzerService.suchenBenutzer(getDummyBenutzer());
			benutzer = bL.get(0);
		}
		return benutzer;
	}

	
	/**
	 * Loescht den Benutzer in der DB
	 */
	public void loeschenDummyBenutzer() {
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		Boolean b = benutzerDAO.delete(benutzer);
	}

	/**
	 *Vollstaendig ausgefülltes, nicht in DB gespeichertes Verlag-Objekt ohne id
	 * 
	 * @return Verlag
	 */
	public Verlag getDummyVerlag1() {
		Verlag v = new Verlag();
		v.setName("Dummy-Verlag 1");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.02.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("30.06.2018"));
		v.setGeloescht(false);
		return v;
	}

	/**
	 * 
	 * @return in DB neu erstellter Verlag
	 */
	public Verlag getFertigerDummyVerlag1() {
		if (verlag1 == null) {
			normdatenService.speichernVerlag(getDummyVerlag1());
			List<Verlag> vL = new ArrayList<>();
			vL = normdatenService.suchenVerlag(getDummyVerlag1());
			verlag1 = vL.get(0);
		}
		return verlag1;
	}

	
	/**
	 * Loescht den Verlagin der DB
	 */
	public void loeschenDummyVerlag1() {
		VerlagDAO verlagDAO = new VerlagDAO();
		verlagDAO.delete(verlag1);
	}
	
	/**
	 *Vollstaendig ausgefülltes, nicht in DB gespeichertes Verlag-Objekt ohne id
	 * 
	 * @return Verlag
	 */
	public Verlag getDummyVerlag2() {
		Verlag v = new Verlag();
		v.setName("Dummy-Verlag 2");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.02.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("30.06.2018"));
		v.setGeloescht(false);
		return v;
	}

	/**
	 * 
	 * @return in DB neu erstellter Verlag
	 */
	public Verlag getFertigerDummyVerlag2() {
		if (verlag2 == null) {
			normdatenService.speichernVerlag(getDummyVerlag2());
			List<Verlag> vL = new ArrayList<>();
			vL = normdatenService.suchenVerlag(getDummyVerlag2());
			verlag2 = vL.get(0);
		}
		return verlag2;
	}

	/**
	 * Loescht den Verlagin der DB
	 */
	public void loeschenDummyVerlag2() {
		VerlagDAO verlagDAO = new VerlagDAO();
		verlagDAO.delete(verlag2);
	}


	public Mitarbeiter getFertigerMitarbeiter() {
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		return mitarbeiterDAO.findById(1);
	}

	/**
	 * 
	 * Zum initialen Login muss in der DB zwingend ein Mitarbeiter vorhanden sein. Daher ist es nicht 
	 * noetig, zuerst einen Mitarbeiter in der DB zu erstellen
	 * 
	 * @return Mitarbeiter
	 */
	public Anrede getAnredeHerr() {
		Anrede a = new Anrede();
		a.setBezeichnung("Frau");
		a.setId(2);
		return a;
	}

	/**
	 * 
	 * @return Adresse
	 */
	private Adresse getDummyAdresse() {
		Adresse a = new Adresse(getOrtLausanne());
		a.setStrasse("Dummy-Strasse 1");
		a.setOrt(getOrtLausanne());
		return a;
	}

	/**
	 * 
	 * @return Lausanne als Ort
	 */
	private Ort getOrtLausanne() {
		Ort o = new Ort();
		o.setId(1);
		o.setOrt("Lausanne");
		o.setPlz(1000);
		return o;

	}

	public Status getBenuterStatusAktiv() {
		Status s = new Status();
		s.setId(1);
		s.setBezeichnung("aktiv");
		return s;

	}

	public Status getBenutzerStatusGesperrt() {
		Status s = new Status();
		s.setId(2);
		s.setBezeichnung("gesperrt");
		return s;
	}

	public Status getMedienStatusAktiv() {
		Status s = new Status();
		s.setId(1);
		s.setBezeichnung("aktiv");
		return s;
	}

	public Status getMedienStatusGesperrt() {
		Status s = new Status();
		s.setId(2);
		s.setBezeichnung("gesperrt");
		return s;
	}

}
