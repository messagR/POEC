/**
 * test
 */
package projetBd;

import java.sql.SQLException;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.banque.entity.IUtilisateur;
import fr.banque.exception.ChampsVidesException;
import fr.banque.exception.ClientIntrouvableException;

/**
 * @author PC
 *
 */
public class TestUtilAuthentificationBd
{
	AccesDB db;

	@Before
	public void beforeClass() {
		try {
			// this.db = new AccesDB("com.mysql.jdbc.Driver");
			// this.db.seConnecter("root", "root",
			// "jdbc:mysql://localhost:3306/banque");
			this.db = new AccesDB("jdbc/dataSourceProjetBankWeb");
			this.db.seConnecter();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}

	@After
	public void afterClass() {
		if (this.db != null) {
			this.db.seDeconnecter();
		}
	}

	@Test
	public void testerAuthentificationReussie() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("df", "df");
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Authentification reussie car client n'est pas null", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerAuthentificationRatee() throws ClientIntrouvableException {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("toto", "tata");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car l'authentification est impossible", client);
	}

	@Test
	public void testerAuthentificationLoginVide() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("", "df");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login donc pas d'authentification", "Login non renseigne", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a pas de login", client);
	}

	@Test
	public void testerAuthentificationImpossibleLoginNull() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier(null, "df");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login donc pas d'authentification", "Login non renseigne", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a pas de login", client);
	}

	@Test
	public void testerAuthentificationMDPVide() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("df", "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de mot de passe donc pas d'authentification", "Mot de passe non renseigne",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a pas de mot de passe", client);
	}

	@Test
	public void testerAuthentificationMDPNull() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("df", null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de mot de passe donc pas d'authentification", "Mot de passe non renseigne",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a pas de mot de passe", client);
	}

	@Test
	public void testerAuthentificationLoginVideMDPVide() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("", "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login ni de mot de passe donc pas d'authentification",
					"Login et mot de passe non renseignes", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni login ni mot de passe", client);
	}

	@Test
	public void testerAuthentificationLoginVideMDPNull() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier("", null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login ni de mot de passe donc pas d'authentification",
					"Login et mot de passe non renseignes", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni login ni mot de passe", client);
	}

	@Test
	public void testerAuthentificationLoginNullMDPVide() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier(null, "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login ni de mot de passe donc pas d'authentification",
					"Login et mot de passe non renseignes", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni login ni mot de passe", client);
	}

	@Test
	public void testerAuthentificationLoginNullMDPNull() {
		IUtilisateur client = null;
		try {
			client = this.db.authentifier(null, null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de login ni de mot de passe donc pas d'authentification",
					"Login et mot de passe non renseignes", e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni login ni mot de passe", client);
	}
}
