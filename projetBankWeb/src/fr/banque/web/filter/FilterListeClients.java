package fr.banque.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet Filter implementation class Authentification
 */
@WebFilter("/FilterListeClients")
public class FilterListeClients implements Filter {
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * Default constructor.
	 */
	public FilterListeClients() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest hRequest = null;
		if (request instanceof HttpServletRequest) {
			hRequest = (HttpServletRequest) request;
		}
		HttpServletResponse hResponse = null;
		if (request instanceof HttpServletRequest) {
			hResponse = (HttpServletResponse) response;
		}
		if (hRequest.getParameter("listClient") == null) {
			RequestDispatcher dispatcher = hRequest.getRequestDispatcher("/ServletChoixClient");
			dispatcher.forward(hRequest, hResponse);
			return;
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
