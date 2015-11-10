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

import fr.banque.entity.IClient;
import fr.banque.exception.ChampsVidesException;
import fr.banque.exception.ClientIntrouvableException;

/**
 * @author PC
 *
 */
public class TestUtilRecuperationClientBd
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
	public void testerRecuperationClientReussie() {
		IClient client = null;
		try {
			client = this.db.recupererClient("Fargis", "Denis");
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car client n'est pas null", client);
	}

	@Test
	public void testerRecuperationClientNomVidePrenomVide() {
		IClient client = null;
		try {
			client = this.db.recupererClient("", "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de nom ni de prenom donc pas de recherche", "Nom et prenom non renseignes",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni nom ni prenom", client);
	}

	@Test
	public void testerRecuperationClientNomNullPrenomVide() {
		IClient client = null;
		try {
			client = this.db.recupererClient(null, "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de nom ni de prenom donc pas de recherche", "Nom et prenom non renseignes",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni nom ni prenom", client);
	}

	@Test
	public void testerRecuperationClientNomVidePrenomNull() {
		IClient client = null;
		try {
			client = this.db.recupererClient("", null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de nom ni de prenom donc pas de recherche", "Nom et prenom non renseignes",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni nom ni prenom", client);
	}

	@Test
	public void testerRecuperationClientNomNullPrenomNull() {
		IClient client = null;
		try {
			client = this.db.recupererClient(null, null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.assertEquals("Pas de nom ni de prenom donc pas de recherche", "Nom et prenom non renseignes",
					e.getMessage());
		} catch (ClientIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("Le client est null car il n'y a ni nom ni prenom", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerRecuperationClientRatee() throws ClientIntrouvableException {
		IClient client = null;
		try {
			this.db.recupererClient("toto", "tata");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("La recherche a reussi mais le client n'est pas trouve", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerRecuperationClientRateeNomVide() throws ClientIntrouvableException {
		IClient client = null;
		try {
			this.db.recupererClient("", "Denis");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("La recherche a reussi mais le client n'est pas trouve", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerRecuperationClientRateePrenomVide() throws ClientIntrouvableException {
		IClient client = null;
		try {
			this.db.recupererClient("Fargis", "");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("La recherche a reussi mais le client n'est pas trouve", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerRecuperationClientRateeNomNull() throws ClientIntrouvableException {
		IClient client = null;
		try {
			this.db.recupererClient(null, "Denis");
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("La recherche a reussi mais le client n'est pas trouve", client);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerRecuperationClientRateePrenomNull() throws ClientIntrouvableException {
		IClient client = null;
		try {
			this.db.recupererClient("Fargis", null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (ChampsVidesException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("La recherche a reussi mais le client n'est pas trouve", client);
	}
}
