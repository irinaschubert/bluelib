package test;

import static org.junit.Assert.assertEquals;

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

public class VerlagAktualisierenTest {

	private Verlag v = new Verlag();
	private List<Verlag> liste = new ArrayList<>();
	private Verlag v2 = new Verlag();
	private Verifikation ver = new Verifikation();
	private NormdatenService n = new NormdatenService();
	private static String TESTNAME = "Testname1";

	@Before
	public void vorbereiten() throws Exception {
		v.setName("Testname");
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		v.setGeloescht(true);
		
		NormdatenService n = new NormdatenService();
		
		ver = n.sichereVerlag(v);

		liste = n.sucheVerlag(v);
		for (Verlag i : liste) {
			v2 = i;
		}
		v2.setName(TESTNAME);
		ver = n.aktualisiereVerlag(v2);
	}

	@Test
	public void test() {
		assertEquals(new VerlagDAO().findById(v2.getId()).getName(), TESTNAME);
	}

	@After
	public void neuenVerlagLoeschen() {
		new VerlagDAO().delete(v2);
	}

}
