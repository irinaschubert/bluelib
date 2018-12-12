package test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import services.HashRechner;


public class HashRechnerTest {

	@Before
	public void test2() {
		System.out.println("test");
	}

	@Test
	public void test() {
		String resultat;
		String eingabe;
		String md5;

		eingabe = "hallo";
		md5 = "598d4c200461b81522a3328565c25f7c";

		resultat = HashRechner.hashBerechnen(eingabe);
		assertEquals(md5, resultat);
	}

}
