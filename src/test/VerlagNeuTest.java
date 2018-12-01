package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import dao.VerlagDAO;
import domain.Verlag;
import hilfsklassen.DateConverter;
import services.NormdatenService;
import services.Verifikation;

public class VerlagNeuTest {
	Verlag v = new Verlag();
	Verlag v2 = new Verlag();
	List<Verlag> liste = new ArrayList<>();
	Verifikation ver = new Verifikation();

	@Before
	public void vorbereiten() throws Exception {
		v.setName("Testverlag");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		v.setGeloescht(true);
	}

	@Test
	public void test() {
		NormdatenService n = new NormdatenService();
		assertTrue(n.sichereVerlag(v).isAktionErfolgreich());
	}

	@After
	public void neuenAutorLoeschen() {
		liste = new VerlagDAO().getSelektion(v);
		for (Verlag verlag : liste)
			new VerlagDAO().delete(verlag);
	}
}
