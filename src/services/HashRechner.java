package services;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Wird zur Berechnung des Hashwertes von Passwörtern verwendet.
 * 
 * @version 0.1 16.10.2018
 * @author irina
 */
public class HashRechner {
	public static String hashBerechnen(String eingabe) {
		if (!eingabe.isEmpty()) {
			String returnValue = DigestUtils.md5Hex(eingabe);
			return returnValue;
		}
		return "";
	}
}
