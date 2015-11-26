package hr.fer.zemris.java.tecaj_14.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model blog komentara.
 * 
 * @author Nikola Sekulić
 * 
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

	/**
	 * Identifikator komentara.
	 */
	private Long id;
	/**
	 * Blog kojem komentar pripada.
	 */
	private BlogEntry blogEntry;
	/**
	 * Email korisnika koji je napisao komentar.
	 */
	private String usersEMail;
	/**
	 * Poruka u komentaru
	 */
	private String message;
	/**
	 * Vrijeme nastanka komentara.
	 */
	private Date postedOn;

	/**
	 * Dohvaća identifikator komentara.
	 * 
	 * @return identikikator.
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * Postavlja identifikator komentara.
	 * 
	 * @param id
	 *            identifikator
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Dohvaća blog na koji se komentar odnosi.
	 * 
	 * @return blog
	 */
	@ManyToOne
	@JoinColumn(nullable = false)
	public BlogEntry getBlogEntry() {
		return this.blogEntry;
	}

	/**
	 * Postavlja blog na koji se komentar odnosi.
	 * 
	 * @param blogEntry
	 *            blog
	 */
	public void setBlogEntry(final BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Dohvaća mail adresu korisnika.
	 * 
	 * @return email
	 */
	@Column(length = 100, nullable = false)
	public String getUsersEMail() {
		return this.usersEMail;
	}

	/**
	 * Postavlja email adresu korisnika.
	 * 
	 * @param usersEMail
	 *            email
	 */
	public void setUsersEMail(final String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Dohvaća sadržaj komentara.
	 * 
	 * @return sadržaj komentara.
	 */
	@Column(length = 4096, nullable = false)
	public String getMessage() {
		return this.message;
	}

	/**
	 * Postavlja sadržaj komentara.
	 * 
	 * @param message
	 *            sadržaj komantara
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Dohvaća vrijeme nastanka komentara.
	 * 
	 * @return vrijeme nastanka
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getPostedOn() {
		return this.postedOn;
	}

	/**
	 * Postavlja vrijeme nastanka komentara
	 * 
	 * @param postedOn
	 *            vijreme nastanka
	 */
	public void setPostedOn(final Date postedOn) {
		this.postedOn = postedOn;
	}

	/**
	 * Računa sažetak na temelju identifikatora komentara.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	/**
	 * Provjerava jesu li dva komentara ista. Komentari su isti ako imaju isti
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
		final BlogComment other = (BlogComment) obj;
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