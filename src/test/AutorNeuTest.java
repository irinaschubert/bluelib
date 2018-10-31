package test;

import static org.junit.Assert.assertTrue;

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

public class AutorNeuTest {
	Autor a = new Autor();
	Autor an = new Autor();
	List<Autor> liste = new ArrayList<>();
	Verifikation b = new Verifikation();

	@Before
	public void vorbereiten() throws Exception {
		a.setName("Testname");
		a.setVorname("Testvorname");
		a.setGeburtsdatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		a.setTodesdatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		a.setGeloescht(true);
	}

	@Test
	public void test() {
		NormdatenService n = new NormdatenService();
		assertTrue(n.sichereAutor(a).isAktionErfolgreich());

	}

	@After
	public void neuenAutorLoeschen() {
		liste = new AutorDAO().getSelektion(a);
		for (Autor i : liste)
			new AutorDAO().delete(i);
	}

}
