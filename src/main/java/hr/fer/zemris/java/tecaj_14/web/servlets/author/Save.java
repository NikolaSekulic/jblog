package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet koji obrađuje obrazac za unos zapisa u skripti entryForm.jsp. Naslov
 * i zapis nisu prazni, sprema zahtjev u bazu podataka te zahtjev prosljeđuje
 * servletu {@link ListEntries}. U suprotnom ponovno zahtjev šalje skripti
 * entryForm.jsp sa informacijom o grešci. Ukoliko obrazac pošalje parametar id,
 * ažurira se postojeći zahtjev u bazi, u suprotnom se stvara novi.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "save", urlPatterns = "/servleti/save")
public class Save extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = -853100453988255540L;

	/**
	 * Prosljeđuje informaciju o grešci.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.redirectToMainPage(req, resp);
	}

	/**
	 * Obrađuje obrazac za unos zapisa u skripti entryForm.jsp. Naslov i zapis
	 * nisu prazni, sprema zahtjev u bazu podataka te zahtjev prosljeđuje
	 * servletu {@link ListEntries}. U suprotnom ponovno zahtjev šalje skripti
	 * entryForm.jsp sa informacijom o grešci. Ukoliko obrazac pošalje parametar
	 * id, ažurira se postojeći zahtjev u bazi, u suprotnom se stvara novi.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if ("Odustani".equals(req.getParameter("metoda"))) {
			this.redirectToMainPage(req, resp);
			return;
		}

		final String idStr = req.getParameter("id");
		Long id = null;

		if (idStr != null) {
			try {
				id = Long.parseLong(idStr);
			} catch (final NumberFormatException nfe) {
			}
		}

		final Map<String, String> errors = new HashMap<>();

		final String nickname = req.getParameter("nickHidden");
		BlogUser user = null;
		try {
			user = DAOProvider.getDAO().getUserByNick(nickname);
		} catch (final Exception e) {
			this.fowardError("Database error!", req, resp);
			return;
		}

		if (user == null) {
			this.fowardError("Nepostojeći korisnik!", req, resp);
			return;
		}

		final String title = req.getParameter("title");

		if (title == null || title.trim().isEmpty()) {
			errors.put("title", "Naslov ne smije biti prazan!");
		}

		final String entry = req.getParameter("entry");
		if (entry == null || entry.trim().isEmpty()) {
			errors.put("title", "Zapis ne smije biti prazan");
		}

		if (errors.isEmpty()) {

			if (id == null) {
				try {
					DAOProvider.getDAO().createNewBlogEntry(user.getId(),
							title, entry);
				} catch (final Exception e) {
					this.fowardError("Database error!", req, resp);
					return;
				}
			} else {

				try {
					DAOProvider.getDAO().editBlogEntry(id, title, entry);
				} catch (final Exception e) {
					this.fowardError("Database error!", req, resp);
					return;
				}
			}

			resp.sendRedirect(req.getContextPath() + "/servleti/author/"
					+ nickname);
			return;

		} else {
			req.setAttribute("nickname", nickname);
			req.setAttribute("title", title);
			req.setAttribute("entry", entry);
			req.setAttribute("id", id);
			req.setAttribute("errors", errors);

			req.getRequestDispatcher("/WEB-INF/pages/entryForm.jsp").forward(
					req, resp);

			return;
		}
	}
}
