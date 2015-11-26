package hr.fer.zemris.java.tecaj_14.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Razred koji čuva upravitelja entiteta za bazu podataka.
 * 
 * @author Nikola Sekulić
 *
 */
public class JPAEMFProvider {

	/**
	 * Instanca upravitelja
	 */
	public static EntityManagerFactory emf;

	/**
	 * Vraća instacnu upravitelja.
	 * 
	 * @return instanca upravitelja
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Postavlja instancu upravitelja.
	 * 
	 * @param emf
	 *            instanca upravitelja
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}