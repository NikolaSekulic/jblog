package hr.fer.zemris.java.tecaj_14.dao;

/**
 * Iznimka koju DAO seučelje baca ukoliko dođe do greške u sloju za
 * perzistenciju podataka.
 * 
 * @author Nikola Sekulić
 *
 */
public class DAOException extends RuntimeException {


	/**
	 * Identifikator za serijalizaciju
	 */
	private static final long serialVersionUID = 7783625000293764434L;

	/**
	 * Konstruktor.
	 * @param message Opis greške
	 * @param cause uzrok greške
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Konstruktor.
	 * @param message opis greške
	 */
	public DAOException(String message) {
		super(message);
	}
}