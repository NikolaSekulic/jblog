package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet koji obrađuje zahtjev za dodavanje novog zapisa. Provjerava ima li
 * trenutni korisnik dozvole za mjanjanje zahtjeva. Ukoliko ima, proljeđuje
 * zahtjev skripti entryForm.jsp koja sadrži obrazac za unos novog zapisa. U
 * suprotnom dojavljuje pogrešku.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "newEntry", urlPatterns = "/servleti/new")
public class NewEntry extends AbstractServlet {

	/**
	 * Identifikator za seriajlizaciju.
	 */
	private static final long serialVersionUID = 3993620485472458630L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.servletJob(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.servletJob(req, resp);
	}

	/**
	 * Nadimak korisnika dohvaća kao parametar 'nickname',
	 * te ga uspoređuje sa nadimkom iz sjednice. Prosljeđuje zahtje skripti
	 * entryForm.jsp.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovvor
	 * @throws IOException
	 *             u slučaju IO pogreške
	 * @throws ServletException
	 *             u slučaju HTTP pogreške.
	 */
	private void servletJob(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		BlogUser user = null;

		try {
			user = DAOProvider.getDAO().getUserByNick(
					(String) req.getAttribute("nickname"));
		} catch (final Exception e) {
			this.fowardError("Database error!", req, resp);
			return;
		}

		if (user == null) {
			this.fowardError("Unknown blogger!", req, resp);
			return;
		}

		if (!user.getId().equals(
				req.getSession().getAttribute("current.user.id"))) {
			this.fowardError("Nuspjela autorizacija", req, resp);
			return;
		}

		req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(req,
				resp);

	}
}
