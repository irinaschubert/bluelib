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
		normdatenService =  new NormdatenService();
	}

	public Buch dummyBuch() {
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
	
	public Buch fertigesDummyBuch() {
		MedienhandlingService medienhandlingService = new MedienhandlingService();
		buch = dummyBuch();
		medienhandlingService.buchNeuErfassen(buch);
		List<Buch> bL = new ArrayList<>();
		bL = medienhandlingService.buchSuchen(buch);
		buch = bL.get(0);
		return buch;
		
	}
	
	public void dummyBuchLoeschen() {
		BuchDAO buchDAO = new BuchDAO();
		Boolean r = buchDAO.delete(buch);
		loeschenDummyVerlag1();
		loeschenDummyAutor1();
		loeschenDummySchlagwort1();
	}

	public Autor getDummyAutor1() {
		Autor a = new Autor();
		a.setName("Dummy-Autor 1");
		a.setVorname("Vorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("11.09.1963"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.01.2015"));
		a.setGeloescht(false);
		return a;
	}
	
	public Autor getFertigerDummyAutor1() {
		normdatenService.sichereAutor(getDummyAutor1());
		List<Autor> aL = new ArrayList<>();
		aL = normdatenService.sucheAutor(getDummyAutor1());
		autor1 = aL.get(0);
		return autor1;
	}
	
	public void loeschenDummyAutor1() {
		AutorDAO autorDAO = new AutorDAO();
		autorDAO.delete(autor1);
	}
	
	public Autor getDummyAutor2() {
		Autor a = new Autor();
		a.setName("Dummy-Autor 2");
		a.setVorname("Vorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("11.09.1963"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.01.2015"));
		a.setGeloescht(false);
		return a;
	}
	
	public Autor getFertigerDummyAutor2() {
		normdatenService.sichereAutor(getDummyAutor2());
		List<Autor> aL = new ArrayList<>();
		aL = normdatenService.sucheAutor(getDummyAutor2());
		autor2 = aL.get(0);
		return autor2;
	}
	
	public void loeschenDummyAutor2() {
		AutorDAO autorDAO = new AutorDAO();
		autorDAO.delete(autor2);
	}

	public static Schlagwort getDummySchlagwort1() {
		Schlagwort s = new Schlagwort();
		s.setSchlagwort("Dummy-Schlagwort 1000");
		s.setGeloescht(false);
		return s;
	}
	
	public Schlagwort getFertigesDummySchlagwort1() {
		normdatenService.sichereSchlagwort(getDummySchlagwort1());
		List<Schlagwort> sL = new ArrayList<>();
		sL = normdatenService.sucheSchlagwort(getDummySchlagwort1());
		schlagwort1 = sL.get(0);
		return schlagwort1;
	}
	
	public void loeschenDummySchlagwort1() {
		SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
		Boolean r = schlagwortDAO.delete(schlagwort1);
	}
	
	public static Schlagwort getDummySchlagwort2() {
		Schlagwort s = new Schlagwort();
		s.setSchlagwort("Dummy-Schlagwort 1001");
		s.setGeloescht(false);
		return s;
	}
	
	public Schlagwort getFertigesDummySchlagwort2() {
		normdatenService.sichereSchlagwort(getDummySchlagwort2());
		List<Schlagwort> sL = new ArrayList<>();
		sL = normdatenService.sucheSchlagwort(getDummySchlagwort2());
		schlagwort2 = sL.get(0);
		return schlagwort2;
	}
	
	public void loeschenDummySchlagwort2() {
		SchlagwortDAO schlagwortDAO = new SchlagwortDAO();
		Boolean r = schlagwortDAO.delete(schlagwort2);
	}

	public Benutzer dummyBenutzer() {
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
	
	public Benutzer fertigerDummyBenutzer() {
		BenutzerService benutzerService = new BenutzerService();
		benutzerService.sichereBenutzer(dummyBenutzer());
		List<Benutzer> bL = new ArrayList<>();
		bL = benutzerService.sucheBenutzer(dummyBenutzer());
		benutzer = bL.get(0);
		return benutzer;
	}
	
	public void dummyBenutzerLoeschen() {
		BenutzerDAO benutzerDAO = new BenutzerDAO();
		Boolean b = benutzerDAO.delete(benutzer);
	}

	public Verlag getDummyVerlag1() {
		Verlag v = new Verlag();
		v.setName("Dummy-Verlag 1");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.02.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("30.06.2018"));
		v.setGeloescht(false);
		return v;
	}
	
	public Verlag getFertigerDummyVerlag1() {
		normdatenService.sichereVerlag(getDummyVerlag1());
		List<Verlag> vL = new ArrayList<>();
		vL = normdatenService.sucheVerlag(getDummyVerlag1());
		verlag1 = vL.get(0);
		return verlag1;
	}
	
	public void loeschenDummyVerlag1() {
		VerlagDAO verlagDAO = new VerlagDAO();
		verlagDAO.delete(verlag1);
	}
	
	public Verlag getDummyVerlag2() {
		Verlag v = new Verlag();
		v.setName("Dummy-Verlag 2");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("01.02.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("30.06.2018"));
		v.setGeloescht(false);
		return v;
	}
	
	public Verlag getFertigerDummyVerlag2() {
		normdatenService.sichereVerlag(getDummyVerlag2());
		List<Verlag> vL = new ArrayList<>();
		vL = normdatenService.sucheVerlag(getDummyVerlag2());
		verlag2 = vL.get(0);
		return verlag2;
	}
	
	public void loeschenDummyVerlag2() {
		VerlagDAO verlagDAO = new VerlagDAO();
		verlagDAO.delete(verlag2);
	}

//	public Mitarbeiter dummyMitarbeiter() {
//		Mitarbeiter m = new Mitarbeiter();
//		m.setAdmin(true);
//		m.setAdresse(dummyAdresse());
//		m.setAktiv(true);
//		m.setAnrede(anredeHerr());
//		m.setBemerkung("Dummy-Bemerkung Mitarbeiter");
//		m.setBenutzername("dummybenutzer");
//		m.setEmail("dummymitarbeiter@test.com");
//		m.setErfassungDatum(new Date());
//		m.setErfassungMitarbeiterId(1);
//		m.setErfassungMitarbeiterName("Initialer Mitarbeiter");
//		m.setGeburtsdatum(DateConverter.convertStringToJavaDate("01.01.1995"));
//		m.setMAId(1);
//		m.setName("Dummy-Mitarbeiter");
//		m.setPasswort("1234");
//		m.setTelefon("044 444 44 44");
//		m.setVorname("Vorname");
//		return m;
//	}

	public Mitarbeiter getFertigerMitarbeiter() {
		MitarbeiterDAO mitarbeiterDAO = new MitarbeiterDAO();
		return mitarbeiterDAO.findById(1);
	}

	public Anrede getAnredeHerr() {
		Anrede a = new Anrede();
		a.setBezeichnung("Frau");
		a.setId(2);
		return a;
	}

	private Adresse getDummyAdresse() {
		Adresse a = new Adresse(getOrtLausanne());
		a.setStrasse("Dummy-Strasse 1");
		a.setOrt(getOrtLausanne());
		return a;
	}
	
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
