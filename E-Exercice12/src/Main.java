
/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.banque.entity.ICompteEntity;
import com.banque.entity.IUtilisateurEntity;
import com.banque.service.AuthentificationService;
import com.banque.service.CompteService;
import com.banque.service.IAuthentificationService;
import com.banque.service.ICompteService;
import com.banque.service.IOperationService;
import com.banque.service.OperationService;

/**
 * Exemple.
 */
public final class Main {
	/** Main log. */
	private final static Log LOG = LogFactory.getLog(Main.class);

	/**
	 * Exemple de fonctionnement.
	 *
	 * @param args
	 *            ne sert a rien
	 */
	public static void main(String[] args) {
		if (Main.LOG.isInfoEnabled()) {
			Main.LOG.info("-- Debut -- ");
		}
		try {
			// On instancie le service authentification afin de récupérer un
			// utilisateur
			IAuthentificationService serviceAuth = new AuthentificationService();
			IUtilisateurEntity utilisateur = serviceAuth.authentifier("dj", "dj");
			if (Main.LOG.isInfoEnabled()) {
				Main.LOG.info("Bonjour " + utilisateur.getNom() + " " + utilisateur.getPrenom());
			}
			// On instancie le service de gestion des comptes pour recuperer la
			// liste de ses comptes
			ICompteService serviceCpt = new CompteService();
			List<ICompteEntity> listeCpts = serviceCpt.selectAll(utilisateur.getId());
			if (Main.LOG.isInfoEnabled()) {
				Main.LOG.info("Vous avez " + listeCpts.size() + " comptes");
			}
			// On prend deux id de comptes pour faire un virement
			Integer[] deuxId = new Integer[2];
			Iterator<ICompteEntity> iter = listeCpts.iterator();
			int id = 0;
			while (iter.hasNext() && (id < deuxId.length)) {
				ICompteEntity compteEntity = iter.next();
				deuxId[id] = compteEntity.getId();
				id++;
			}

			// On Effectue un virement entre deux comptes, via le service des
			// operations
			IOperationService serviceOp = new OperationService();
			serviceOp.faireVirement(utilisateur.getId(), deuxId[0], deuxId[1], Double.valueOf(5));
			if (Main.LOG.isInfoEnabled()) {
				Main.LOG.info("Votre virement s'est bien effectué");
			}
		} catch (Exception e) {
			Main.LOG.fatal("Erreur", e);
			System.exit(-1);
		}
		if (Main.LOG.isInfoEnabled()) {
			Main.LOG.info("-- Fin -- ");
		}
		System.exit(0);
	}
}
