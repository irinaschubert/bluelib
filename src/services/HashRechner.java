package services;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class HashRechner {
	

	
	public static String hashBerechnen(String eingabe) {
		String returnValue = null;
		if (!eingabe.isEmpty()) {
			returnValue = DigestUtils.md5Hex(eingabe);
		}
		return returnValue;
	}

}
