package hr.fer.zemris.java.tecaj_14.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model zapisa u blogu.
 * 
 * @author Nikola Sekulić
 * 
 */
@Entity
@Table(name = "blog_entries")
@Cacheable(true)
@NamedQueries({@NamedQuery(name = "BlogEntry.upit1", query = "select b from BlogComment as b where b.blogEntry=:be and b.postedOn>:when")})
public class BlogEntry {

	/**
	 * Identifikator zapisa
	 */
	private Long id;

	/**
	 * Lista komentara
	 */
	private List<BlogComment> comments = new ArrayList<>();

	/**
	 * Vrijeme stvaranja zapisa
	 */
	private Date createdAt;

	/**
	 * Zadnje vrijeme promjene
	 */
	private Date lastModifiedAt;

	/**
	 * Naslov zapisa
	 */
	private String title;

	/**
	 * Sadržaj zapisa
	 */
	private String text;

	/**
	 * Korisnik koji je stvorio zapis
	 */
	private BlogUser creator;

	/**
	 * Dohvaća identifikator zapisa
	 * 
	 * @return identifikator zapisa
	 */
	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	/**
	 * Postavlja identifikator zapisa.
	 * 
	 * @param id
	 *            identifikator zapisa
	 */
	public void setId(final Long id) {
		this.id = id;
	}

	/**
	 * Dohvaća listu komentara
	 * 
	 * @return lista komentara
	 */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return this.comments;
	}

	/**
	 * Postavlja listu komentara.
	 * 
	 * @param comments
	 *            lista komentara.
	 */
	public void setComments(final List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Dohvaća vrijeme nastanka
	 * 
	 * @return vrijeme nastanka zapisa.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	/**
	 * Postavlja vrijeme nastanka.
	 * 
	 * @param createdAt
	 *            vrijeme nastaka
	 */
	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Dohvaća vrijeme zadnje promjene.
	 * 
	 * @return vrijeme zadnje promjene
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = true)
	public Date getLastModifiedAt() {
		return this.lastModifiedAt;
	}

	/**
	 * Postavlja vrijeme zadnje promjene.
	 * 
	 * @param lastModifiedAt
	 *            vrijeme zadnje promjene
	 */
	public void setLastModifiedAt(final Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Dohvaća naslov zapisa.
	 * 
	 * @return naslov zapisa.
	 */
	@Column(length = 200, nullable = false)
	public String getTitle() {
		return this.title;
	}

	/**
	 * Postavlja naslov zapisa.
	 * 
	 * @param title
	 *            naslov zapisa.
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * Dohvaća sadržaj zapisa.
	 * 
	 * @return sadržaj zapisa.
	 */
	@Column(length = 4096, nullable = false)
	public String getText() {
		return this.text;
	}

	/***
	 * Postavlja sadržaj zapisa.
	 * 
	 * @param text
	 *            sadržaj zapisa.
	 */
	public void setText(final String text) {
		this.text = text;
	}

	/**
	 * Dohvaća stvoritelja zapisa.
	 * 
	 * @return stvoritelj zapisa.
	 */
	@ManyToOne
	public BlogUser getCreator() {
		return this.creator;
	}

	/**
	 * Postavlja stvoritelja zapisa.
	 * 
	 * @param creator
	 *            stvoritelj zapisa.
	 */
	public void setCreator(final BlogUser creator) {
		this.creator = creator;
	}

	/**
	 * Računa sažetak na temelju identifikatora zapisa.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.id == null ? 0 : this.id.hashCode());
		return result;
	}

	/**
	 * Provjerava jesu li dva zapisa ista. Zapisi su isti ukoliko imaji isti
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
		final BlogEntry other = (BlogEntry) obj;
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