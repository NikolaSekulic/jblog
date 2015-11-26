package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet koji obrađuje zahtjeve formata /servleti/author/NICK/*. NICK
 * predstavlja korisničko ime nekog korisnika. Ukoliko zadnji parametar u adresi
 * nije zadan, zahtjev se prosljeđuje servletu {@link ListEntries}, koji
 * prikazuje sve zapise korisnika sa nadimkom NICK. Ako je zadnji parametar
 * broj, zahtjec se prosljeđuje servletu {@link GetEntry}. Ukolko je zadnji
 * parametar new, zahtjev se prosljeđuje servletu {@link NewEntry}, a koje je
 * edit, zahtjev se prosljeđuje servletu {@link EditEntry}. U svim ostalim
 * slučajevima, kao odgovor se šalje informacija o pogrešci 404.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "rest", urlPatterns = {"/servleti/author/*"})
public class RestServlet extends AbstractServlet {

	/**
	 * Serialization identifier
	 */
	private static final long serialVersionUID = -8041802685329836773L;

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
	 * Obrađuje zahtjeve formata /servleti/author/NICK/*. NICK predstavlja
	 * korisničko ime nekog korisnika. Ukoliko zadnji parametar u adresi nije
	 * zadan, zahtjev se prosljeđuje servletu {@link ListEntries}, koji
	 * prikazuje sve zapise korisnika sa nadimkom NICK. Ako je zadnji parametar
	 * broj, zahtjec se prosljeđuje servletu {@link GetEntry}. Ukolko je zadnji
	 * parametar new, zahtjev se prosljeđuje servletu {@link NewEntry}, a koje
	 * je edit, zahtjev se prosljeđuje servletu {@link EditEntry}. U svim
	 * ostalim slučajevima, kao odgovor se šalje informacija o pogrešci 404.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws IOException
	 *             u slučaju IO pogreške
	 * @throws ServletException
	 *             u slučaju HTTP pogreške
	 */
	private void servletJob(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		String info = req.getPathInfo();

		if (info == null) {
			this.redirectToMainPage(req, resp);
			return;
		}

		info = info.substring(1);

		final String[] infoParts = info.split("/");

		if (infoParts.length == 1) {
			final String nickname = infoParts[0];
			req.setAttribute("nickname", nickname);
			req.getRequestDispatcher("/servleti/entries").forward(req, resp);
			return;
		} else if (infoParts.length == 2) {
			final String nickname = infoParts[0];
			req.setAttribute("nickname", nickname);
			final String secondParameter = infoParts[1];

			boolean eid = false;

			try {
				Double.parseDouble(secondParameter);
				eid = true;
			} catch (final NumberFormatException nfe) {
			}

			if (eid) {
				req.setAttribute("eid", Long.parseLong(secondParameter));
				req.getRequestDispatcher("/servleti/entry").forward(req, resp);
				return;
			} else {
				if ("new".equalsIgnoreCase(secondParameter)) {

					req.getRequestDispatcher("/servleti/new")
							.forward(req, resp);
					return;
				} else if ("edit".equalsIgnoreCase(secondParameter)) {
					req.getRequestDispatcher("/servleti/edit").forward(req,
							resp);
					return;
				}
			}
		}

		this.fowardError("Page not found: 404", req, resp);

	}

}
