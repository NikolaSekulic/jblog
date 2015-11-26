package hr.fer.zemris.java.tecaj_14.web.servlets.users;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;
import hr.fer.zemris.java.tecaj_14.model.auth.ShaUtilities;
import hr.fer.zemris.java.tecaj_14.web.servlets.AbstractServlet;

/**
 * Servlet za registraciju korisnika. Obrađuje podatke is obrasca iz skripte
 * registerForm.jsp. Ukoliko neki podatak nije u zadanom formatu, skripti sa
 * obrascem šalje informaciju o grešci te ponovno traži unos podataka. Ukoliko
 * su svi podaci ispravnog foramta, te uneseno korisničko ie ne postoji, stvara
 * novog korisnika te preusmjerava zahtjev na početnu stranicu.
 * 
 * @author Nikola Sekulić
 * 
 */
@SuppressWarnings("serial")
@WebServlet(name = "registration", urlPatterns = {"/servleti/register"})
public class Registration extends AbstractServlet {

	/**
	 * Maksimalna dopuštena veličina parametra.
	 */
	private final int MAX_PARAMETER_LENGTH = 256;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/pages/registerForm.jsp").forward(
				req, resp);

	}

	/**
	 * Obrađuje podatke is obrasca iz skripte registerForm.jsp. Ukoliko neki
	 * podatak nije u zadanom formatu, skripti sa obrascem šalje informaciju o
	 * grešci te ponovno traži unos podataka. Ukoliko su svi podaci ispravnog
	 * foramta, te uneseno korisničko ie ne postoji, stvara novog korisnika te
	 * preusmjerava zahtjev na početnu stranicu.
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if ("Odustani".equals(req.getParameter("metoda"))) {
			this.redirectToMainPage(req, resp);
			return;
		}

		final Map<String, String> errors = new HashMap<>();
		final Map<String, String> parameters = new HashMap<>();

		this.checkParameters(errors, parameters, req, resp);

		if (errors.isEmpty()) {
			final String error = this.createUser(parameters,
					req.getParameter("password").trim(), req, resp);

			if (error != null) {
				this.fowardError(error, req, resp);
				return;
			} else {
				this.redirectToMainPage(req, resp);
			}

		} else {

			req.setAttribute("errors", errors);
			req.setAttribute("parameters", parameters);
			req.getRequestDispatcher("/WEB-INF/pages/registerForm.jsp")
					.forward(req, resp);

		}

	}

	/**
	 * Stvara novog korisnika. U bazu zapisuje sve parameter o korisniku, te
	 * sha-1 sažetak loznike.
	 * 
	 * @param parameters
	 *            mapa sa parametrima za novog korisnika
	 * @param password
	 *            lozinka novog korisnika
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @return poruka o grešci, <code>null</code> ako se greška nije dogodila.
	 */
	private String createUser(Map<String, String> parameters, String password,
			HttpServletRequest req, HttpServletResponse resp) {

		final BlogUser newUser = new BlogUser();
		newUser.setEmail(parameters.get("email"));
		newUser.setFirstName(parameters.get("email"));
		newUser.setLastName(parameters.get("lastname"));
		newUser.setNick(parameters.get("username"));
		newUser.setPasswordHash(ShaUtilities.getShaHash(password));

		String error = null;

		try {
			DAOProvider.getDAO().registerUser(
					parameters.get("firstname").trim(),
					parameters.get("lastname").trim(),
					parameters.get("email").trim(),
					parameters.get("username").trim(),
					ShaUtilities
							.getShaHash(req.getParameter("password").trim()));
		} catch (final Exception e) {
			error = "Pogreška prilikom pisanja u bazu!";
		}

		return error;
	}

	/**
	 * Provjerava jeli e-mail adresa u ispravnom formatu.
	 * 
	 * @param email
	 *            e-mail adresa
	 * @return <code>true</code> ako i samo ako je adresa u ispravnom formatu
	 */
	private boolean emailIsInValidFormat(String email) {
		return email
				.matches("(([\\w\\Q-_\\E]+)(\\.([\\w\\Q-_\\E]+))*)@(([\\w\\Q-_\\E]+)(\\.([\\w\\Q-_\\E]+))+)");
	}

	/**
	 * Provjerava jesu li podaci ispravnog formata. Ukoliko neki podatak nije
	 * ispravnog formata, u mapu <errors> stavlja poruku o pogrešci, pod ključem
	 * pod kojim je taj podatak spremljen u map parameters.
	 * 
	 * @param errors
	 *            mapa pogrešaka
	 * @param parameters
	 *            mapa podataka
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws ServletException
	 *             u slučaju HTTP greške
	 * @throws IOException
	 *             u slučaju IO greške
	 */
	private void checkParameters(Map<String, String> errors,
			Map<String, String> parameters, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		if (username == null || username.trim().isEmpty()) {
			errors.put("username", "Korisničko ime mora biti zadano!");
			parameters.put("username", "");
		} else {
			username = username.trim();
			parameters.put("username", username);

			if (username.length() > this.MAX_PARAMETER_LENGTH) {
				errors.put("username",
						"Koirsničko ime može imati najviše 256 znakova!");
			} else {

				BlogUser user = null;

				try {
					user = DAOProvider.getDAO().getUserByNick(username);
				} catch (final Exception e) {
					errors.put("username", "Ne mogu pristupiti bazi!");
				}

				if (user != null) {
					errors.put("username", "Korisničko ime " + username
							+ " je zauzeto!");
				}
			}

		}

		String firstname = req.getParameter("firstname");
		if (firstname == null || firstname.trim().isEmpty()) {
			errors.put("firstname", "Ime ne smije biti prazno");
			parameters.put("firstname", "");
		} else {

			firstname = firstname.trim();
			parameters.put("firstname", firstname);

			if (firstname.length() > this.MAX_PARAMETER_LENGTH) {
				errors.put("firstname", "Ime može imati najviše 256 znakova!");
			}
		}

		String lastname = req.getParameter("lastname");
		if (lastname == null || lastname.trim().isEmpty()) {
			errors.put("lastname", "Prezime ne smije biti prazno");
			parameters.put("lastname", "");
		} else {

			lastname = lastname.trim();
			parameters.put("lastname", lastname);

			if (lastname.length() > this.MAX_PARAMETER_LENGTH) {
				errors.put("lastname",
						"Prezime može imati najviše 256 znakova!");
			}
		}

		String email = req.getParameter("email");
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "E-mail mora biti zadan!");
			parameters.put("email", "");
		} else {

			email = email.trim();
			parameters.put("email", email);

			if (email.length() > this.MAX_PARAMETER_LENGTH) {
				errors.put("email", "E-mail može imati najviše 256 znakova!");
			} else if (!this.emailIsInValidFormat(email)) {
				errors.put("email", "E-mail nije ispravnog formata!");
			}
		}

		final String password = req.getParameter("password");
		if (password == null || password.trim().isEmpty()) {
			errors.put("password", "Lozinka mora biti zadana!");
		}
	}

}
