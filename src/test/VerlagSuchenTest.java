package test;

import static org.junit.Assert.*;

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

public class VerlagSuchenTest {

	private Verlag v = new Verlag();
	private Verlag v2 = new Verlag();
	private List<Verlag> liste = new ArrayList<>();
	private Verifikation b = new Verifikation();
	private static String TESTNAME = "TestnameSuchen";
	private NormdatenService n = new NormdatenService();

	@Before
	public void vorbereiten() throws Exception {
		v.setName(TESTNAME);
		v.setGruendungsDatum(DateConverter.convertStringToJavaDate("10.10.1950"));
		v.setEndDatum(DateConverter.convertStringToJavaDate("01.05.2005"));
		v.setGeloescht(false);
		b = n.sichereVerlag(v);
	}

	@Test
	public void test() {
		v2.setName(TESTNAME);
		assertTrue(n.sucheVerlag(v2).size() > 0);
	}

	@After
	public void neuenVerlagLoeschen() {
		liste = new VerlagDAO().getSelektion(v);
		for (Verlag i : liste)
			new VerlagDAO().delete(i);
	}
}

