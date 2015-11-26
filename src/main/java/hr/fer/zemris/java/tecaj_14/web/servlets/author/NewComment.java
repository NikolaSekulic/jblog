package hr.fer.zemris.java.tecaj_14.web.servlets.author;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet koji obrađuje formu za unos komentara. Identifikator zapisa na koji
 * se komentar odnosi je spremljen u parametru <code>eid</code>.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "newComment", urlPatterns = {"/servleti/comment/new"})
public class NewComment extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = 5263306821638682075L;

	/**
	 * Prosljeđuje korisnika na ispis o grešci jer ovaj servlet ne podržava POST
	 * metodu.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.fowardError("Neispravan zahtjev", req, resp);
	}

	/**
	 * Stvara novi komentar. Identifikator zapisa čita iz parametra 'eid'. Ako je
	 * korisnik prijavljen, za email komentara se korisnti mail korisnika koji
	 * je prijavljen. Sadržaj komentara čita iz parametra 'message'. Ako je
	 * komentar uspješno stvoren, zahtjev se preusmjerava na stranicu zapisa.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		final String eidString = req.getParameter("eid");

		if (eidString == null) {
			this.fowardError("Neispravan zahtjev", req, resp);
			return;
		}

		Long eid = null;

		try {
			eid = Long.parseLong(eidString);
		} catch (final NumberFormatException nfe) {
			this.fowardError("Neispravan zahtjev", req, resp);
			return;
		}

		String email = "";

		String nick = null;

		HttpSession session = req.getSession();
		if (session != null
				&& session.getAttribute("current.user.nick") != null) {
			nick = session.getAttribute("current.user.nick").toString();
		}

		BlogUser user = null;

		if (nick != null) {

			try {
				user = DAOProvider.getDAO().getUserByNick(nick);
			} catch (final Exception e) {
				this.fowardError("DATABASE ERROR", req, resp);
				return;
			}

			if (user != null) {
				email = user.getEmail();
			}
		}

		final String message = req.getParameter("message");

		if (message != null && !message.trim().isEmpty()) {
			final String comment = message.trim();

			try {
				DAOProvider.getDAO().addNewComment(eid, email, comment);
			} catch (final Exception e) {
				this.fowardError("Neispravan zahtjev", req, resp);
				return;
			}
		}

		final String path = req.getParameter("path");
		final String info = req.getParameter("info");

		if (path == null || path.isEmpty()) {
			this.fowardError("Neispravan zahtjev", req, resp);
			return;
		}

		resp.sendRedirect(req.getServletContext().getContextPath() + path
				+ info);
	}
}
