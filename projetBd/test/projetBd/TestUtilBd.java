/**
 * test
 */
package projetBd;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.banque.entity.IClient;
import fr.banque.entity.ICompte;
import fr.banque.exception.BanqueException;
import fr.banque.exception.ClientIntrouvableException;
import fr.banque.exception.CompteIntrouvableException;

/**
 * @author PC
 *
 */
public class TestUtilBd
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
	public void testerListerCompteReussi() {
		List<ICompte> listeCompte = null;
		try {
			listeCompte = this.db.listeCompte(1);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeCompte n'est pas null", listeCompte);
		Assert.assertTrue("Recherche reussie car listeCompte n'est pas null et est remplie", listeCompte.size() > 0);
	}

	@Test
	public void testerListerCompteReussiVide() {
		List<ICompte> listeCompte = null;
		try {
			listeCompte = this.db.listeCompte(4);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeCompte n'est pas null", listeCompte);
		Assert.assertTrue("Recherche reussie car listeCompte n'est pas null mais est vide", listeCompte.size() == 0);
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerListerCompteClientInvalid() throws ClientIntrouvableException {
		List<ICompte> listeCompte = null;
		try {
			listeCompte = this.db.listeCompte(-1);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("ListeCompte est null car id client invalid", listeCompte);
	}

	@Test
	public void testerAfficherIdComptesUtilisateurReussi() {
		try {
			this.db.afficherIdComptesUtilisateur(1);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testerAfficherIdComptesUtilisateurReussiVide() {
		try {
			this.db.afficherIdComptesUtilisateur(10);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test(expected = ClientIntrouvableException.class)
	public void testerAfficherIdComptesUtilisateurClientInvalid() throws ClientIntrouvableException {
		try {
			this.db.afficherIdComptesUtilisateur(-1);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testerRechercherCompteReussi() {
		ICompte compte = null;
		try {
			compte = this.db.rechercherCompte(12);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car compte n'est pas null", compte);
	}

	@Test
	public void testerRechercherCompteRate() {
		ICompte compte = null;
		try {
			compte = this.db.rechercherCompte(4);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.assertEquals("Le compte n'est pas trouve", "Compte introuvable", e.getMessage());
		}
		Assert.assertNull("La recherche a reussie mais le compte n'est pas trouve", compte);
	}

	@Test
	public void testerRechercherCompteCompteInvalid() {
		ICompte compte = null;
		try {
			compte = this.db.rechercherCompte(-1);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.assertEquals("Id compte invalide donc pas de recherche", "Id compte invalide", e.getMessage());
		}
		Assert.assertNull("Le compte est null car l'id compte est invalide", compte);
	}

	@Test
	public void testerFaireVirementReussie() {
		double ancienSoldeSource = 0;
		double ancienSoldeDest = 0;
		double nouveauSoldeSource = 0;
		double nouveauSoldeDest = 0;
		try {
			ancienSoldeSource = this.db.rechercherCompte(15).getSolde();
			ancienSoldeDest = this.db.rechercherCompte(12).getSolde();
			this.db.faireVirement(15, 12, 50.0);
			nouveauSoldeSource = this.db.rechercherCompte(15).getSolde();
			nouveauSoldeDest = this.db.rechercherCompte(12).getSolde();
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertTrue("Virement reussi car nouveauSoldeSource = ancienSoldeSource - 50",
				nouveauSoldeSource == (ancienSoldeSource - 50));
		Assert.assertTrue("Virement reussi car nouveauSoldeDest = ancienSoldeDest + 50",
				nouveauSoldeDest == (ancienSoldeDest + 50));
	}

	@Test(expected = BanqueException.class)
	public void testerFaireVirementRate() throws BanqueException {
		try {
			this.db.faireVirement(14, 12, 50.0);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	public void testerFaireVirementCptSourceInvalid() throws CompteIntrouvableException {
		try {
			this.db.faireVirement(-1, 12, 50.0);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (BanqueException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test(expected = CompteIntrouvableException.class)
	public void testerFaireVirementCptDestInvalid() throws CompteIntrouvableException {
		try {
			this.db.faireVirement(15, -1, 50.0);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (BanqueException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testerFaireVirementSomInvalid() throws IllegalArgumentException {
		try {
			this.db.faireVirement(15, 12, -50.0);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (BanqueException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testerListerClientReussie() {
		List<IClient> listeClient = null;
		try {
			listeClient = this.db.listeClient();
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Liste reussi car listeClient n'est pas null", listeClient);
		Assert.assertTrue("Liste reussi car listeClient n'est pas null et n'est pas vide", listeClient.size() > 0);
	}

	@Test
	public void testerAfficherNomPrenomUtilisateurReussie() {
		try {
			this.db.afficherNomPrenomUtilisateur();
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testerAfficherIdOperationComptesReussi() {
		try {
			this.db.afficherIdOperationComptes(12);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
	}

	@Test
	public void testerfficherIdOperationComptesRate() {
		try {
			this.db.afficherIdOperationComptes(4);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.assertEquals("Le compte n'est pas trouve", "Compte introuvable", e.getMessage());
		}
	}

	@Test
	public void testerfficherIdOperationComptesCompteInvalid() {
		try {
			this.db.afficherIdOperationComptes(-1);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		} catch (CompteIntrouvableException e) {
			Assert.assertEquals("Id compte invalide donc pas de recherche", "Id compte invalide", e.getMessage());
		}
	}

}
