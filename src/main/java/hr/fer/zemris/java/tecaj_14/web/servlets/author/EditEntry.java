package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogEntry;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet koji obrađuje uređivanje zapisa. Prosljeđuje inforaciju o zapisu i
 * autoru zapisa skripti entryForm.jsp koja iscrtava formu za uređivanje zapisa.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "edit", urlPatterns = {"/servleti/edit"})
public class EditEntry extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = -8547238123490287988L;

	/**
	 * Prosljeđuje pogrešku.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.fowardError("Neispravan zahtjev", req, resp);
	}

	/**
	 * Uređivanje zahtjeva.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.servletJob(req, resp);
	}

	/**
	 * Zahtjev prosljeđuje na skriptu sa formularom za uređivanje zapisa. Prije
	 * toga u zahtjev upisuje atribute: id: identifikator zapisa, nickname:
	 * korisničko ime, title: naslov zapisa entry: sadržaj zapisa.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odogovor
	 * @throws IOException
	 *             u slučaju HTTP pogreške
	 * @throws ServletException
	 *             u slučaju IO pogreške
	 */
	private void servletJob(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		final String id = req.getParameter("eid");
		final String nickname = req.getParameter("nickname");

		BlogEntry entry = null;
		BlogUser user = null;

		try {
			entry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(id));
			user = DAOProvider.getDAO().getUserByNick(nickname);
		} catch (final Exception e) {
			this.fowardError("Neispravan zahtjev", req, resp);
			return;
		}

		if (user == null || entry == null) {
			this.fowardError("Neispravan zahtjev!", req, resp);
			return;
		}

		if (!user.getId().equals(
				req.getSession().getAttribute("current.user.id"))) {
			this.fowardError("Nuspjela autorizacija", req, resp);
			return;
		}

		req.setAttribute("id", Long.parseLong(id));
		req.setAttribute("nickname", nickname);
		req.setAttribute("title", entry.getTitle());
		req.setAttribute("entry", entry.getText());

		req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(req,
				resp);

	}
}
