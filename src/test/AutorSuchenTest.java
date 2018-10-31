package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.AutorDAO;
import domain.Autor;
import hilfsklassen.DateConverter;
import services.NormdatenService;
import services.Verifikation;

public class AutorSuchenTest {

	private Autor a = new Autor();
	private Autor an = new Autor();
	private List<Autor> liste = new ArrayList<>();
	private Verifikation b = new Verifikation();
	private static String TESTNAME = "TestnameSuchen";
	private NormdatenService n = new NormdatenService();

	@Before
	public void vorbereiten() throws Exception {
		a.setName(TESTNAME);
		a.setVorname("Testvorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		a.setGeloescht(false);
		b = n.sichereAutor(a);
	}

	@Test
	public void test() {
		an.setName(TESTNAME);
		assertTrue(n.sucheAutor(an).size() > 0);

	}

	@After
	public void neuenAutorLoeschen() {
		liste = new AutorDAO().getSelektion(a);
		for (Autor i : liste)
			new AutorDAO().delete(i);
	}
}
