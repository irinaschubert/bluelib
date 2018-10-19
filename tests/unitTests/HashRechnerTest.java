package unitTests;

import static org.junit.jupiter.api.Assertions.*;
import services.HashRechner;

import org.junit.jupiter.api.Test;

class HashRechnerTest {

	@Test
	void test() {
		String resultat;
		String eingabe;
		String md5;
		
		eingabe = "hallo";
		md5 = "598d4c200461b81522a3328565c25f7c";
		HashRechner hashRechner = new HashRechner();
		
		resultat = hashRechner.hashBerechnen(eingabe);
		assertEquals(md5, resultat);
	}

}
