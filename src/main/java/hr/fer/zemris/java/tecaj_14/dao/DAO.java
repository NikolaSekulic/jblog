package hr.fer.zemris.java.tecaj_14.dao;

import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.util.List;

/**
 * Sučelje usluga koje sloj za perzistenciju podataka nudi implementacijama DAO
 * objekata.
 * 
 * @author Nikola Sekulić
 * 
 */
public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id
	 *            ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException
	 *             ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Dohvaća sve registrirane korisnike. Ako korisnici ne postoje, vraća
	 * <code>null</code>.
	 * 
	 * @return korisnici
	 */
	public List<BlogUser> listAllUsers();

	/**
	 * Dohvaća korisnika sa zadanim <code>nick</code>-om. Ako takav korisnik ne
	 * postoji, vraća <code>null</code>.
	 * 
	 * @param nick
	 *            nick korisnika
	 * @return korisnik sa zadanim nickom ako postoji, null inače
	 */
	public BlogUser getUserByNick(String nick);

	/**
	 * Registracija korisnika. Niti jedan parametar ne smije biti null i nick
	 * mora biti jedinstveno. Ukoliko nešto od navedenog nije zadovoljeno, vraća
	 * se false, inače true. Validacije je potrebno obaviti prije registracije.
	 * Preporuča se spremanje lozinke u hash obliku.
	 * 
	 * @param firstName
	 *            ime korisnika
	 * @param lastName
	 *            prezime korisnika
	 * @param email
	 *            email korisnika
	 * @param nick
	 *            nick korisnika
	 * @param hashPassword
	 *            hash lozinka
	 * @return true ukoliko je registracija uspjela, false inače
	 */
	public boolean registerUser(String firstName, String lastName,
			String email, String nick, String hashPassword);

	/**
	 * Dodavanje komentara blogu s eid. Pretpostavlja se da je eid ispravan, u
	 * suprotnom postoji mogućnost bacanja iznimke.
	 * 
	 * @param eid
	 *            id bloga
	 * @param email
	 *            email korisnika
	 * @param comment
	 *            komentar
	 */
	public void addNewComment(Long eid, String email, String comment);

	/**
	 * Stvaranje novog bloga. Pretpostavlja se da je uid ispravan, u suprotnom
	 * postoji mogućnost bacanja iznimke.
	 * 
	 * @param uid
	 *            korisnički id
	 * @param title
	 *            naslov bloga
	 * @param text
	 *            tekst bloga
	 */
	public void createNewBlogEntry(long uid, String title, String text);

	/**
	 * Izmjena bloga: naslov ili tekst. Pretpostavlja se da je eid ispravan, u
	 * suprotnom postoji mogućnost bacanja iznimke.
	 * 
	 * @param eid
	 *            id bloga
	 * @param title
	 *            naslov bloga
	 * @param text
	 *            tekst bloga
	 */
	public void editBlogEntry(Long eid, String title, String text);

}