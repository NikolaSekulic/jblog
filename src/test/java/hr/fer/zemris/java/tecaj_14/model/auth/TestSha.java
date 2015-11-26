package hr.fer.zemris.java.tecaj_14.model.auth;


import org.junit.*;

import static org.junit.Assert.*;
public class TestSha {

	@Test
	/**
	 * Testira jesu li sažetci dva stringa isti.
	 */
	public void testShaSamePasswords() {
		String pass1 = "abc123";
		String pass2 = "abc123";
		
		String sha1 = ShaUtilities.getShaHash(pass1);
		String sha2 = ShaUtilities.getShaHash(pass2);
		
		assertEquals("Sažeci moraju biti isti!", sha1, sha2);
	}
}
