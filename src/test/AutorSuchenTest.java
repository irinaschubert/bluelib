package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.Autor;
import services.NormdatenService;

public class AutorSuchenTest {

	private Autor autor = null;
	private TestDomaenenObjekte testDomaenenObjekte;
	private NormdatenService normdatenService;

	@Before
	public void setUp() throws Exception {
		testDomaenenObjekte = new TestDomaenenObjekte();
		autor = testDomaenenObjekte.getFertigerDummyAutor1();
	}

	@Test
	public void testSuchen() {
		normdatenService = new NormdatenService();
		List<Autor> aL = new ArrayList<>();
		aL = normdatenService.sucheAutor(autor);
		assertTrue(aL.size() > 0);

	}

	@After
	public void tearDown() {
		testDomaenenObjekte.loeschenDummyAutor1();
	}
}
