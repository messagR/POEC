/**
 * test
 */
package com.test.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.banque.entity.IUtilisateurEntity;
import com.banque.service.IAuthentificationService;
import com.banque.service.ex.AuthentificationException;
import com.banque.service.ex.ErreurTechniqueException;
import com.banque.service.ex.MauvaisMotdepasseException;
import com.banque.service.ex.UtilisateurInconnuException;

/**
 * @author PC
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(name = "applicationContext", locations = { "classpath*:*-context.xml" })
@Rollback(value = true)
@Transactional(transactionManager = "transactionManager")
public class AuthenticationTestCase {
	protected final static Log LOG = LogFactory.getLog(AuthenticationTestCase.class);

	@Autowired(required = true)
	@Qualifier(value = "authentificationService")
	protected IAuthentificationService service;

	// TODO

	/**
	 * Execute avant chaque test
	 */
	@Before
	public void beforeEachTest() {
		if (AuthenticationTestCase.LOG.isInfoEnabled()) {
			AuthenticationTestCase.LOG.info("-----> beforeEachTest");
		}
		Assert.assertNotNull("Service ne doit pas être null", this.service);
	}

	/**
	 * Execute après chaque test
	 */
	@After
	public void afterEachTest() {
		if (AuthenticationTestCase.LOG.isInfoEnabled()) {
			AuthenticationTestCase.LOG.info("<----- afterEachTest");
		}
	}

	/**
	 * Authentification valide.
	 */
	@Test
	public void testAuthentifierOk() {
		IUtilisateurEntity utilisateur = null;
		try {
			utilisateur = this.service.authentifier("df", "df");
		} catch (AuthentificationException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur Authentification (" + e.getMessage() + ")");
		} catch (ErreurTechniqueException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur technique (" + e.getMessage() + ")");
		}

		Assert.assertNotNull("Authentification reussie car client n'est pas null", utilisateur);
	}

	@Test
	public void testAuthentifierKoNull() {
		IUtilisateurEntity utilisateur = null;
		try {
			utilisateur = this.service.authentifier("", "df");
			utilisateur = this.service.authentifier("df", null);
		} catch (NullPointerException e) {
			Assert.assertNull("L'utilisateur est null car il n'y a pas de " + e.getMessage(), utilisateur);
		} catch (AuthentificationException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur Authentification (" + e.getMessage() + ")");
		} catch (ErreurTechniqueException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur technique (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testAuthentifierKoLogin() {
		IUtilisateurEntity utilisateur = null;
		try {
			utilisateur = this.service.authentifier("df1", "df");
		} catch (UtilisateurInconnuException e) {
			Assert.assertNull("L'utilisateur est null car il est inconnu", utilisateur);
		} catch (AuthentificationException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur Authentification (" + e.getMessage() + ")");
		} catch (ErreurTechniqueException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur technique (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testAuthentifierKoPassword() {
		IUtilisateurEntity utilisateur = null;
		try {
			utilisateur = this.service.authentifier("df", "df1");
		} catch (MauvaisMotdepasseException e) {
			Assert.assertNull("L'utilisateur est null car son mot de passe n'est pas le bon", utilisateur);
		} catch (AuthentificationException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur Authentification (" + e.getMessage() + ")");
		} catch (ErreurTechniqueException e) {
			if (AuthenticationTestCase.LOG.isInfoEnabled()) {
				AuthenticationTestCase.LOG.error(e.getMessage());
			}
			Assert.fail("Erreur technique (" + e.getMessage() + ")");
		}
	}
}
