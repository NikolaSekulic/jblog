package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstraktni servlet koji sadrži metode zajedničke svim servletima u
 * aplikaciji.
 * 
 * @author Nikola Sekulić
 *
 */
public abstract class AbstractServlet extends HttpServlet {

	/**
	 * Identifikator za serijalizaciju.
	 */
	private static final long serialVersionUID = -4456506868984034486L;

	/**
	 * Prosljeđuje informaiju o grešci sckripti Error.jsp
	 * 
	 * @param message
	 *            informacija o grešci
	 * @param req
	 *            zathjev
	 * @param resp
	 *            odgovor
	 * @throws ServletException
	 *             u slučaju HTTP greške
	 * @throws IOException
	 *             u slučaju IO greške
	 */
	protected void fowardError(String message, HttpServletRequest req,
			HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
	}

	/**
	 * Prosljeđuje zahtjev na početnu stranicu.
	 * 
	 * @param req
	 *            zahtjev
	 * @param resp
	 *            odgovor
	 * @throws IOException
	 */
	protected void redirectToMainPage(HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.sendRedirect(req.getServletContext().getContextPath()
				+ "/servleti/main");
	}
}
