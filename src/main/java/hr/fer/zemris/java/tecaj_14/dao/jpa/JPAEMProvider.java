package hr.fer.zemris.java.tecaj_14.dao.jpa;

import hr.fer.zemris.java.tecaj_14.dao.DAOException;

import javax.persistence.EntityManager;


/**
 * Razred koji čuva upravitelje entitetima. Svaka dretva ima spremljen svoj
 * upravitelj.
 * 
 * @author Nikola Sekulić
 *
 */
public class JPAEMProvider {

	/**
	 * Mapa koja čuva upravitelje entiteta. Ključ mape je identifikator dretve.
	 */
	private static ThreadLocal<LocalData> locals = new ThreadLocal<>();

	/**
	 * Dohvaća upravitelja entiteta za dretvu koja poziva ovu metodu. U
	 * upravitelju započinje transakciju.
	 * 
	 * @return upravitelj entitetima
	 */
	public static EntityManager getEntityManager() {
		LocalData ldata = locals.get();
		if (ldata == null) {
			ldata = new LocalData();
			ldata.em = JPAEMFProvider.getEmf().createEntityManager();
			ldata.em.getTransaction().begin();
			locals.set(ldata);
		}
		return ldata.em;
	}

	/**
	 * Briše upravitelja za dretvu koja poziva ovu metodu. Prije brisanja
	 * zatvara transakciju u upravitelju.
	 * 
	 * @throws DAOException
	 *             ako transkcija nije uspješno izvršena.
	 */
	public static void close() throws DAOException {
		final LocalData ldata = locals.get();
		if (ldata == null) {
			return;
		}
		DAOException dex = null;
		try {
			ldata.em.getTransaction().commit();
		} catch (final Exception ex) {
			// dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			ldata.em.close();
		} catch (final Exception ex) {
			dex = new DAOException("Unable to close entity manager.", ex);
		}

		locals.remove();
		if (dex != null) {
			throw dex;
		}
	}

	/**
	 * Omotač oko upravitelja entiteta.
	 * 
	 * @author Nikola Sekulić
	 *
	 *         //
	 */
	private static class LocalData {
		
		/**
		 * Instanca upravitelja
		 */
		EntityManager em;
	}

}