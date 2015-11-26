package hr.fer.zemris.java.tecaj_14.web.servlets.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet za odjavu korisnika. Postavlja trenutnu sjednicu nevažećom te
 * preusmjerava korisnika na početnu stranicu. Korisnik se može odjaviti GET i
 * POST metodom.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "logout", urlPatterns = "/servleti/logout")
public class Logout extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = -4673446848230433932L;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logout(req, resp);
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logout(req, resp);
	}

	/**
	 * Poništava sjednicu ako postoji.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws IOException
	 *             u slučaju IO greške.
	 */
	private void logout(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		final HttpSession session = req.getSession();
		if (session != null) {
			session.invalidate();
		}
		this.redirectToMainPage(req, resp);
	}
}
