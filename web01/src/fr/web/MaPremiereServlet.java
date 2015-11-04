package fr.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MaPremiereServlet loadOnStartup force l'init au
 * demarrage du serveur _ avec un ordre_
 */
public class MaPremiereServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MaPremiereServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("-- Passage dans Init --");
		System.out.println(config.getInitParameter("exemple"));
	}

	@Override
	public void destroy() {
		System.out.println("-- Passage dans Destroy --");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		sb.append("<html>\n");
		sb.append("	<head>\n");
		sb.append("		<title>Ma page web</title>\n");
		sb.append("	</head>\n");
		sb.append("	<body>\n");
		sb.append("		<h1>Bonjour</h1> tout le monde<br/><br>\n");
		sb.append("		Le serveur porte le nom : ");
		sb.append(request.getServerName()).append("<br/>\n");
		sb.append("		Le context root est : ");
		sb.append(request.getContextPath()).append("<br/>\n");
		sb.append("		Votre adresse IP est : ");
		sb.append(request.getRemoteAddr()).append("<br/>\n");
		sb.append("		Votre protocol est : ");
		sb.append(request.getScheme()).append("<br/>\n");
		for(int i = 0 ; i < 10; i++){
			sb.append("		Val = ").append(i).append("<br/>\n");
		}
		sb.append("	</body>\n");
		sb.append("</html>");
		out.print(sb.toString());
	}
}
