package hr.fer.zemris.java.tecaj_14.model.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Pomoćni razred za rad sa SHA-1 sažetkom
 * 
 * @author Nikola Sekulić
 *
 */
public class ShaUtilities {

	/**
	 * Računa sažetak iz string. Prvo string pretvara u niz okteta pomoću UTF-8
	 * kodne stranice. Zatim računa SHA-1 sažetak tog niza. Zatim sažetak kodira
	 * u string u heksadecimalnom formatu.
	 * 
	 * @param input
	 *            string iz kojeg se računa sažetak
	 * @return sažetak u heksadecimalnom formatu.
	 */
	public static String getShaHash(String input) {

		MessageDigest sha = null;

		try {
			sha = MessageDigest.getInstance("SHA-1");
		} catch (final NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		sha.update(input.getBytes(StandardCharsets.UTF_8));

		return byteToHex(sha.digest());
	}

	/**
	 * Pretvara niz okteta u heksadecimalni zapis.
	 * 
	 * @param array
	 *            niz okteta
	 * @return okteti i heksadeciomalnom zapisu.
	 */
	public static String byteToHex(byte[] array) {
		final StringBuilder sb = new StringBuilder();

		for (final byte b : array) {
			final String byteStr = Integer.toHexString(b & 0x00FF);
			if (byteStr.length() == 1) {
				sb.append('0');
			}
			sb.append(byteStr);
		}

		return sb.toString();
	}
}
