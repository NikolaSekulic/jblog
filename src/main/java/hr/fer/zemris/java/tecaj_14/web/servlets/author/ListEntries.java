package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet dohvaća sve zapise pojednog autora, te prosljeđje ih skripti
 * listEntries.jsp koja prikazuje sve zapise i komentare. Korisničko ime
 * korisnika se nalazi u atributima zahtjeva.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "entries", urlPatterns = {"/servleti/entries"})
public class ListEntries extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = -7753565165514784089L;

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
	 * Dohvaća listu svih zapisa za poejdnog autora. Autor je definiran
	 * atributom nickname. Listu zapisa sprema u zahtjev ako atribut imena
	 * 'entries', te prosljeđuje zahtjev skripti listEntries.jsp.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws ServletException
	 *             u slučaju HTTP pogreške
	 * @throws IOException
	 *             u slučaju IO pogreške
	 */
	private void servletJob(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String nickname = (String) req.getAttribute("nickname");

		BlogUser user = null;

		try {
			user = DAOProvider.getDAO().getUserByNick(nickname);
		} catch (final Exception e) {
			this.fowardError("Unknown user!", req, resp);
			return;
		}

		if (user == null) {
			this.fowardError("Nepostojeći korisnik: " + nickname, req, resp);
			return;
		}

		List<BlogEntry> entires = new ArrayList<>();

		try {
			entires = new ArrayList<>(user.getBlogEntries());
		} catch (final Exception ignorable) {
		}

		Collections.sort(entires, new Comparator<BlogEntry>() {

			@Override
			public int compare(BlogEntry o1, BlogEntry o2) {
				final Date date1 = o1.getCreatedAt();
				final Date date2 = o2.getCreatedAt();

				return date1.compareTo(date2);
			}
		});

		req.setAttribute("entries", entires);

		if (!entires.isEmpty()) {
			System.out.println(entires.get(0).getTitle());
			System.out.println(entires.size());
		}

		req.getRequestDispatcher("/WEB-INF/pages/listEntries.jsp").forward(req,
				resp);
	}
}
