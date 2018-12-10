package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AutorDAO;
import domain.Autor;
import hilfsklassen.DateConverter;
import services.NormdatenService;


/**
 * Prueft, ob alle Werte des Autors korrekt auktualisiert werden
 * 
 * @version 2.0 2018-12-10
 * @author Ueli
 *
 */
public class AutorAktualisierenTest {

	private Autor autor = null;
	private Autor autorTest = null;
	private TestDomaenenObjekte testDomaenenObjekte;
	private NormdatenService normdatenService;

	@Before
	public void setUp() throws Exception {
		testDomaenenObjekte = new TestDomaenenObjekte();
		autor = new Autor();
		autor = testDomaenenObjekte.getFertigerDummyAutor1();

		String name = "Dummy-Autor 3";
		String vorname = "Vorname 1";
		Date geburtsDatum = DateConverter.convertStringToJavaDate("01.03.1950");
		Date todesDatum = DateConverter.convertStringToJavaDate("01.01.2000");
		Boolean geloescht = true;

		autor.setName(name);
		autor.setVorname(vorname);
		autor.setGeburtsdatum(geburtsDatum);
		autor.setTodesdatum(todesDatum);
		autor.setGeloescht(geloescht);

		autor.setName("Testname");
		autor.setVorname("Testvorname");
		autor.setGeburtsdatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		autor.setTodesdatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		autor.setGeloescht(true);

	}

	@Test
	public void test() {

		String name = "Dummy-Autor 3";
		String vorname = "Vorname 1";
		Date geburtsDatum = DateConverter.convertStringToJavaDate("01.03.1950");
		Date todesDatum = DateConverter.convertStringToJavaDate("01.01.2000");
		Boolean geloescht = true;

		autor.setName(name);
		autor.setVorname(vorname);
		autor.setGeburtsdatum(geburtsDatum);
		autor.setTodesdatum(todesDatum);
		autor.setGeloescht(geloescht);

		normdatenService = new NormdatenService();
		normdatenService.aktualisiereAutor(autor);

		List<Autor> aL = new ArrayList<>();
		aL = normdatenService.sucheAutor(autor);
		if (aL.size() > 0) {
			autorTest = aL.get(0);
		}

		assertEquals(autor.getName(), autorTest.getName());
		assertEquals(autor.getVorname(), autorTest.getVorname());
		assertEquals(autor.getGeburtsdatum(), autorTest.getGeburtsdatum());
		assertEquals(autor.getTodesdatum(), autorTest.getTodesdatum());
		assertEquals(autor.getGeloescht(), autorTest.getGeloescht());

	}

	@After
	public void tearDown() {
		new AutorDAO().delete(autorTest);

	}

}
