package hr.fer.zemris.java.tecaj_14.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model korisnika.
 * 
 * @author Nikola Sekulić
 * 
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {

	/**
	 * Identifikator korisnika.
	 */
	private Long id;

	/**
	 * Ime korisnika
	 */
	private String firstName;

	/**
	 * Prezime korisnika
	 */
	private String lastName;

	/**
	 * Nadimak korisnika.
	 */
	private String nick;

	/**
	 * Korisnikova adresa elektroniče pošte.
	 */
	private String email;

	/**
	 * SHA-1 sažetak korisnikove lozinke. Sažetak je zapisan u heksadecimalnom
	 * formatu.
	 */
	private String passwordHash;

	/**
	 * Lista zapisa koje je korisnik unio.
	 */
	private List<BlogEntry> blogEntries = new ArrayList<>();

	/**
	 * Dohvaća identifikator korisnika.
	 * 
	 * @return identifikator korisnika
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * Postavlja identifikator korisnika.
	 * 
	 * @param id
	 *            identifikator korisnika.
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Dohvaća korisničko ime.
	 * 
	 * @return ime korisnika
	 */
	@Column(length = 50, nullable = false)
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Postavlja korisničko ime.
	 * 
	 * @param firstName
	 *            ime korisnika.
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Dohvaća prezime korisnika.
	 * 
	 * @return prezime korisnika.
	 */
	@Column(length = 100, nullable = false)
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * Postavlja prezime.
	 * 
	 * @param lastName
	 *            prezime.
	 */
	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Dohvaća nadimak.
	 * 
	 * @return nadimak
	 */
	@Column(length = 30, nullable = false, unique = true)
	public String getNick() {
		return this.nick;
	}

	/**
	 * Postavlja nadimak
	 * 
	 * @param nick
	 *            nadimak
	 */
	public void setNick(final String nick) {
		this.nick = nick;
	}

	/**
	 * Dohvaća adresu elektroničke pošte.
	 * 
	 * @return e-mail adresa
	 */
	@Column(length = 50, nullable = false)
	public String getEmail() {
		return this.email;
	}

	/**
	 * Postavlja adresu elektroničke pošte.
	 * 
	 * @param email
	 *            e-mail adresa
	 */
	public void setEmail(final String email) {
		this.email = email;
	}

	/**
	 * Dohvaća SHA-1 sažetak lozinke.
	 * 
	 * @return SHA-1 sažetak lozinke
	 */
	@Column(length = 50, nullable = false)
	public String getPasswordHash() {
		return this.passwordHash;
	}

	/**
	 * Postavlja SHA-1 sažetak lozinke.
	 * 
	 * @param passwordHash
	 *            sažetak lozinke.
	 */
	public void setPasswordHash(final String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Dohvaća listu korisničkih zapisa.
	 * 
	 * @return lista zapisa
	 */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	public List<BlogEntry> getBlogEntries() {
		return this.blogEntries;
	}

	/**
	 * Postavlja listu korisničkih zapisa.
	 * 
	 * @param blogEntries
	 *            lista zapisa.
	 */
	public void setBlogEntries(final List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/**
	 * Računa sažetak korisnika na temelju identifikatora.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	/**
	 * Provjerava jesu li dva korisnika ista. Korisnici su isti ako imaju isti
	 * identifikator.
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final BlogUser other = (BlogUser) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

}
