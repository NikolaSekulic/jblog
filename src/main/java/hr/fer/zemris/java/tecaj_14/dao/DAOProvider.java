package hr.fer.zemris.java.tecaj_14.dao;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPADAOImpl;

/**
 * Razred koji čuva jedinstvenu instancu DAO objekta.
 * 
 * @author Nikola Sekulić
 *
 */
public class DAOProvider {

	/**
	 * Instanca DAO sučelja
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * Vraća jedinstveenu instancu DAO objekta
	 * 
	 * @return instanca dao objekta
	 */
	public static DAO getDAO() {
		return dao;
	}

}