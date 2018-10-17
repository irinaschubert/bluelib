package services;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @version 0.1 16.10.2018
 * @author irina
 *
 */
public class HashRechner {
	
	String hash;
	
	public HashRechner() {
		hash = new String();
	}
	
	public String hashBerechnen(String eingabe) {
		hash = DigestUtils.md5Hex(eingabe);
		return hash;
	}

}
