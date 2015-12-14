/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Factory hibernate.
 */
public final class HibernateSessionFactory {

	protected static Log LOG = LogFactory.getLog(HibernateSessionFactory.class);

	private static SessionFactory sessionFactory;

	/**
	 * Constructeur de l'objet.
	 */
	private HibernateSessionFactory() {
		super();
	}

	/**
	 * Recupere la session factory.
	 *
	 * @return la session factory.
	 */
	public static synchronized SessionFactory getInstance() {
		if (HibernateSessionFactory.sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();
				configuration.configure();
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				HibernateSessionFactory.sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (HibernateException e) {
				HibernateSessionFactory.LOG.fatal("Impossible d'initialiser Hibernate", e);
			}
		}
		return HibernateSessionFactory.sessionFactory;
	}

}
