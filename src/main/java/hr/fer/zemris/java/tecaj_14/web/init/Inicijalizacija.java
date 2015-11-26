package hr.fer.zemris.java.tecaj_14.web.init;

import hr.fer.zemris.java.tecaj_14.dao.jpa.JPAEMFProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Promatrač koji se aktivira kada se aplikacija pokrene. Postavlja vezu prema
 * bazi podataka.
 * 
 * @author Nikola Sekulić
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Postavlja vezu prema bazi podataka.
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		
		EntityManagerFactory emf = null;
		emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");

		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);

	}

	/**
	 * Prekida vezu prema bazi podataka.
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {


		JPAEMFProvider.setEmf(null);
		final EntityManagerFactory emf = (EntityManagerFactory) sce
				.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}

	}
}