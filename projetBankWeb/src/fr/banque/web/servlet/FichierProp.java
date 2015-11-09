/**
 * test
 */
package fr.banque.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author PC
 *
 */
public class FichierProp {
	private final static Logger LOG = LogManager.getLogger();

	// Nom du driver pour acceder a la base de donnee.
	// Lire la documentation associee a sa base de donnees pour le connaitre
	String utilDbDriver;
	// url d'acces a la base de donnees
	String utilDbUrl;
	// login d'acces a la base de donnees
	String utilDbLogin;
	// mot de passe d'acces a la base de donnees
	String utilDbPassword;

	public FichierProp() {
		Properties mesProperties = new Properties();
		try (InputStream is = ServletLogin.class.getClassLoader().getResourceAsStream("mesPreferences.properties")) {
			mesProperties.load(is);
		} catch (IOException e) {
			FichierProp.LOG.error("Recuperation du fichier mesPreferences.properties impossible");
		}

		this.setUtilDbDriver(mesProperties.getProperty("utilDb.driver"));
		this.setUtilDbUrl(mesProperties.getProperty("utilDb.url"));
		this.setUtilDbLogin(mesProperties.getProperty("utilDb.login"));
		this.setUtilDbPassword(mesProperties.getProperty("utilDb.password"));
	}

	public String getUtilDbDriver() {
		return this.utilDbDriver;
	}

	public void setUtilDbDriver(String utilDbDriver) {
		this.utilDbDriver = utilDbDriver;
	}

	public String getUtilDbUrl() {
		return this.utilDbUrl;
	}

	public void setUtilDbUrl(String utilDbUrl) {
		this.utilDbUrl = utilDbUrl;
	}

	public String getUtilDbLogin() {
		return this.utilDbLogin;
	}

	public void setUtilDbLogin(String utilDbLogin) {
		this.utilDbLogin = utilDbLogin;
	}

	public String getUtilDbPassword() {
		return this.utilDbPassword;
	}

	public void setUtilDbPassword(String utilDbPassword) {
		this.utilDbPassword = utilDbPassword;
	}
}
