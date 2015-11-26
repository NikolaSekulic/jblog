package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet mapiran na početne stranice. Zahtjeve preusmjerava na glavni servlet
 * (/servleti/main).
 * 
 * @author Nikola Sekulić
 *
 */
@WebServlet(urlPatterns = {"/index.html", "/", "/index.jsp"})
public class IndexServlet extends HttpServlet {

	/**
	 * Identifikator za serijalizaciju
	 */
	private static final long serialVersionUID = 7108223230759272793L;

	/**
	 * Obrada zahtjeva. Zahtjev preusmjerava na servlet mapiran sa
	 * /servleti/main.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
