package test;

import static org.junit.Assert.assertEquals;

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

public class AutorAktualisierenTest {

	private Autor a = new Autor();
	private List<Autor> liste = new ArrayList<>();
	private Autor an = new Autor();
	private Verifikation v = new Verifikation();
	private NormdatenService n = new NormdatenService();
	private static String TESTNAME = "Testname1";

	@Before
	public void vorbereiten() throws Exception {
		a.setName("Testname");
		a.setVorname("Testvorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		NormdatenService n = new NormdatenService();
		v = n.sichereAutor(a);

		liste = n.sucheAutor(a);
		for (Autor i : liste) {
			an = i;
		}

		an.setName(TESTNAME);
		System.out.println(an.getName());
		v = n.aktualisiereAutor(an);
	}

	@Test
	public void test() {

		assertEquals(new AutorDAO().findById(an.getId()).getName(), TESTNAME);

	}

	@After
	public void neuenAutorLoeschen() {
		new AutorDAO().delete(an);
	}

}
