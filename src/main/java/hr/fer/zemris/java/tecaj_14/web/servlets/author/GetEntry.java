package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet koji dohvaća jedan zapis. Id zapisa se nalazi u atributima zahtjeva.
 * Prosljeđuje zahtjev skripti listEntries.jsp sa traženim zahtjevom.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "GetEntry", urlPatterns = {"/servleti/entry"})
public class GetEntry extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju
	 */
	private static final long serialVersionUID = 7523659428257324985L;

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
	 * Dohvaća listu zapis za zadanog korisnika sa zadanim identifikatorom
	 * zahtjeva. Korisnik je definiran nadimkom, spremljenim kao atribut // *
	 * 'nickname', a identifikator zahtjeva sa atributom 'eid'. Zapis dodaje u
	 * praznu listu zahejtva. Listu zapisa sprema u zahtjev kao atribut imena 'entries',
	 * zatim prosljeđuje zahtjev skripti listEntries.jsp.
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

		BlogEntry entry = null;

		try {
			entry = DAOProvider.getDAO().getBlogEntry(
					(Long) req.getAttribute("eid"));
		} catch (final Exception e) {
			this.fowardError("DATABASE ERROR!", req, resp);
			return;
		}

		if (entry == null) {
			this.fowardError("Neispravan zahtjev!", req, resp);
			return;
		}

		if (!entry.getCreator().getNick().equals(nickname)) {
			this.fowardError("Neispravan zahtjev!", req, resp);
			return;
		}

		final List<BlogEntry> entries = new ArrayList<>();
		entries.add(entry);

		req.setAttribute("entries", entries);

		req.getRequestDispatcher("/WEB-INF/pages/listEntries.jsp").forward(req,
				resp);

	}

}
