package hr.fer.zemris.java.tecaj_14.web.servlets;

import hr.fer.zemris.java.tecaj_14.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_14.model.BlogUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Glavni servlet. Prosljeđuje zahtjev skripti authors.jsp. U zahtjev stavlja
 * listu svih korisnika koja je sortirana po korisničkom imenu korisnika
 * uzlazno.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebServlet(name = "main", urlPatterns = {"/servleti/main/*"})
public class MainServlet extends AbstractServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = 6121742343823080301L;

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
	 * Dohvaća iz baze listu svih autora. Listu sortira po nadimku autora
	 * uzlazno. U zahtjev listu sprema pod atributom 'authors' te prosljeđuje
	 * zahtjev skripti index.jsp.
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

		List<BlogUser> users = null;

		try {
			users = DAOProvider.getDAO().listAllUsers();
		} catch (final Exception e) {
			this.fowardError("Pogreška prilikom pristupa bazi!", req, resp);
			return;
		}

		final List<BlogUser> authors = new ArrayList<>(users);

		Collections.sort(authors, new Comparator<BlogUser>() {

			@Override
			public int compare(BlogUser o1, BlogUser o2) {
				return o1.getNick().compareToIgnoreCase(o2.getNick());
			}
		});

		req.setAttribute("authors", authors);

		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}

}
