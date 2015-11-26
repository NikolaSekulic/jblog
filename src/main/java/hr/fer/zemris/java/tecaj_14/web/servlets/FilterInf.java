package hr.fer.zemris.java.tecaj_14.web.servlets;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Zajednički filter svim servletima. Postavlja kodnu stranicu zahtjeva i
 * odgovora na utf-8 te ispisuje adresu zahtjeva na standardni izlaz.
 * 
 * @author Nikola Sekulić
 * 
 */
@WebFilter("/*")
public class FilterInf implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * Postavlja kodnu stranicu zahtjeva i odgovora na utf-8 te ispisuje adresu
	 * zahtjeva na standardni izlaz.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String path = "";
		String info = "";

		try {
			path = ((HttpServletRequest) request).getServletPath();
			info = ((HttpServletRequest) request).getPathInfo();
		} catch (final Exception ignorable) {
		}

		System.out.println();
		System.out.println(path + (info != null ? info : ""));
		System.out.println();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {

	}

}