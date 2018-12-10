package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dao.AutorDAO;
import domain.Autor;
import services.NormdatenService;


/**
 * Prueft, ob ein Autor korrekt neu erstellt wird
 * 
 * @version 2.0 2018-12-10
 * @author Ueli
 *
 */

public class AutorNeuTest {
	Autor autor = null;
	TestDomaenenObjekte testDomaenenObjekte;
	NormdatenService normdatenService;
	List<Autor> liste = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		testDomaenenObjekte = new TestDomaenenObjekte();
		autor = testDomaenenObjekte.getDummyAutor1();
	}

	@Test
	public void testNeu() {
		normdatenService = new NormdatenService();
		normdatenService.speichereAutor(autor);
		List<Autor> aL = new ArrayList<>();
		aL = normdatenService.sucheAutor(autor);
		if (aL.size() > 0) {
			autor = aL.get(0);
		}
		assertEquals(autor.getName(), testDomaenenObjekte.getDummyAutor1().getName());
		assertEquals(autor.getVorname(), testDomaenenObjekte.getDummyAutor1().getVorname());
		assertEquals(autor.getGeburtsdatum(), testDomaenenObjekte.getDummyAutor1().getGeburtsdatum());
		assertEquals(autor.getTodesdatum(), testDomaenenObjekte.getDummyAutor1().getTodesdatum());
		assertEquals(autor.getGeloescht(), testDomaenenObjekte.getDummyAutor1().getGeloescht());
	}

	@After
	public void tearDown() {
		liste = new AutorDAO().getSelektion(autor);
		for (Autor i : liste)
			new AutorDAO().delete(i);
	}

}
