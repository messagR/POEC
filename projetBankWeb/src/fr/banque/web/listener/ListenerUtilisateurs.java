package fr.banque.web.listener;

import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.entity.IUtilisateur;

/**
 * Application Lifecycle Listener implementation class ListenerUtilisateurs
 *
 */
@WebListener
public class ListenerUtilisateurs implements HttpSessionListener {
	private final static Logger LOG = LogManager.getLogger();

	/**
	 * Default constructor.
	 */
	public ListenerUtilisateurs() {
	}

	/**
	 * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	/**
	 * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		ListenerUtilisateurs.LOG.error("session supprimee: {}", event.getSource());
		Map<String, IUtilisateur> listeConnectes = (Map<String, IUtilisateur>) event.getSession().getServletContext()
				.getAttribute("listeConnectes");
		String idSession = event.getSession().getId();
		listeConnectes.remove(idSession);
	}
}
