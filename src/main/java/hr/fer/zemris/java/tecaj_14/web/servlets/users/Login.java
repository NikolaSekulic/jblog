package hr.fer.zemris.java.tecaj_14.web.servlets.users;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.model.auth.ShaUtilities;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet za prijavu korisnika. Obrađuje podatke iz obrasca za prijavu iz
 * skripte loginForm.jsp. Ukoliko su korisničko ime i sha-1 sažetak lozinke
 * točni, u sjednicu stavlja identifikator korisnika, korisničko ime korisnika,
 * te ime i prezime korisnika pod parametrima 'current.user.id',
 * 'current.user.nick', 'current.user.fn' i 'current.user.ln' respektivno.
 * Ukoliko podaci za prijavu nisu točni, ponovno traži unos podataka.
 * 
 * @author Nikola Sekulić
 * 
 */
@SuppressWarnings("serial")
@WebServlet(name = "login", urlPatterns = {"/servleti/login"})
public class Login extends AbstractServlet {

	/**
	 * Samo prosljeđuje korisnika na početnu stranicu jer se korisnik
	 * prijavljuje POST metodom.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		this.redirectToMainPage(req, resp);
	}

	/**
	 * Obrađuje podatke iz obrasca za prijavu iz skripte loginForm.jsp. Ukoliko
	 * su korisničko ime i sha-1 sažetak lozinke točni, u sjednicu stavlja
	 * identifikator korisnika, korisničko ime korisnika, te ime i prezime
	 * korisnika pod parametrima 'current.user.id', 'current.user.nick',
	 * 'current.user.fn' i 'current.user.ln' respektivno. Ukoliko podaci za
	 * prijavu nisu točni, ponovno traži unos podataka.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String username = req.getParameter("username");
		String password = req.getParameter("password");

		if (username == null || password == null) {
			this.usernameOrPasswordIncorrect(username,
					"Korisničko ime i lozinka moraju biti zadani!", req, resp);
			return;
		}

		username = username.trim();
		password = password.trim();

		if (username.isEmpty() || password.isEmpty()) {
			this.usernameOrPasswordIncorrect(username,
					"Korisničko ime i lozinka moraju biti zadani!", req, resp);
			return;
		}

		final String passHash = ShaUtilities.getShaHash(password);

		BlogUser user = null;

		try {
			user = DAOProvider.getDAO().getUserByNick(username);
		} catch (final Exception e) {
			this.fowardError("Pogreška prilikom pristupanja bazi", req, resp);
			return;
		}

		if (user == null || !user.getPasswordHash().equalsIgnoreCase(passHash)) {
			this.usernameOrPasswordIncorrect(username,
					"Neispravno korisničko ime ili lozinka!", req, resp);

			return;
		} else {

			final HttpSession session = req.getSession();
			if (session != null) {
				session.invalidate();
			}

			req.getSession(true).setAttribute("current.user.id", user.getId());
			req.getSession().setAttribute("current.user.fn",
					user.getFirstName());
			req.getSession()
					.setAttribute("current.user.ln", user.getLastName());
			req.getSession().setAttribute("current.user.nick", user.getNick());

			this.redirectToMainPage(req, resp);

		}
	}

	/**
	 * Prosljeđuje korisnika na početnu stranicu sa opisom greške. Greška se
	 * nalazi u atributu 'LoginError'.
	 * 
	 * @param username
	 *            korisničko ime
	 * @param message
	 *            poruka o grešci
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws ServletException
	 *             u slučaju HTTP greške
	 * @throws IOException
	 *             u slučaju IO greške
	 */
	private void usernameOrPasswordIncorrect(String username, String message,
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("username", username);

		req.setAttribute("LoginError", message);
		req.getRequestDispatcher("/servleti/main").forward(req, resp);
	}

}
