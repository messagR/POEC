/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.exo;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Exemple.
 */
public final class Main {
	/** Main log. */
	private final static Log LOG = LogFactory.getLog(Main.class);

	/**
	 * Charge un fichier Spring.
	 *
	 * @param args
	 *            ne sert a rien
	 */
	public static void main(String[] args) {
		if (Main.LOG.isDebugEnabled()) {
			Main.LOG.debug("-- Debut -- ");
		}

		ClassPathXmlApplicationContext appContext = null;
		try {
			// Chargement du fichier
			appContext = new ClassPathXmlApplicationContext("mesBeans.xml");
			// Récupération de notre instance de client
			Client cl1 = (Client) appContext.getBean("monClient");
			Client cl2 = (Client) appContext.getBean("monClientConstructor");
			Client cl3 = (Client) appContext.getBean("monClientProperty");
			Client cl4 = (Client) appContext.getBean("monClientConstructorProperty");

			Client cl5 = (Client) appContext.getBean("moi");
			Client cl6 = (Client) appContext.getBean("moi2");
			Client cl7 = (Client) appContext.getBean("moi3");

			// Affichage
			if (Main.LOG.isDebugEnabled()) {
				Main.LOG.debug(cl1.toString());
				cl1.setNom("Toto");
				Main.LOG.debug(cl1.toString());
				cl1 = appContext.getBean("monClient", Client.class);
				Main.LOG.debug(cl1.toString());
				Main.LOG.debug(cl2.toString());
				cl2.setNom("Titi");
				Main.LOG.debug(cl2.toString());
				cl2 = appContext.getBean("monClientConstructor", Client.class);
				Main.LOG.debug(cl2.toString());
				Main.LOG.debug(cl3.toString());
				Main.LOG.debug(cl4.toString());

				Main.LOG.debug(cl5.toString());
				Main.LOG.debug(cl6.toString());
				Main.LOG.debug(cl7.toString());
			}

			if (cl7.getAdresses() instanceof Vector) {
				Main.LOG.debug("ok");
			} else {
				Main.LOG.debug("ko : " + cl7.getAdresses().getClass());
			}

		} catch (BeansException e) {
			Main.LOG.fatal("Erreur", e);
			System.exit(-1);
		} finally {
			if (appContext != null) {
				appContext.close();
			}
		}
		if (Main.LOG.isDebugEnabled()) {
			Main.LOG.debug("-- Fin -- ");
		}
		System.exit(0);
	}
}