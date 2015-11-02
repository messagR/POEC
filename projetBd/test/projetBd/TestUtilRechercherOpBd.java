/**
 * test
 */
package projetBd;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.banque.entity.IOperation;
import fr.banque.exception.CompteIntrouvableException;

/**
 * @author PC
 *
 */
public class TestUtilRechercherOpBd
{
	AccesDB db;

	@Before
	public void beforeClass() {
		try {
			this.db = new AccesDB("com.mysql.jdbc.Driver");
			this.db.seConnecter("root", "root", "jdbc:mysql://localhost:3306/banque");
		} catch (SQLException e) {
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
	public void testerRechercherOpReussiSansDateDebSansDateFinSansCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, null, null, null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebSansDateFinSansCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, null, null, null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebSansDateFinAvecCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, null, null, true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebSansDateFinAvecCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, null, null, true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebSansDateFinSansCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, new Date('1'), null, null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebSansDateFinSansCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, new Date('1'), null, null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebSansDateFinAvecCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, new Date('1'), null, true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebSansDateFinAvecCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, new Date('1'), null, true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebAvecDateFinSansCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, new Date('1'), new Date(), null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebAvecDateFinSansCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, new Date('1'), new Date(), null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebAvecDateFinAvecCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, new Date('1'), new Date(), true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiAvecDateDebAvecDateFinAvecCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, new Date('1'), new Date(), true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebAvecDateFinSansCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, null, new Date(), null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebAvecDateFinSansCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, null, new Date(), null);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebAvecDateFinAvecCD() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(12, null, new Date(), true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null et n'est pas vide",
				listeOperation.size() > 0);
	}

	@Test
	public void testerRechercherOpReussiSansDateDebAvecDateFinAvecCDVide() {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(16, null, new Date(), true);
		} catch (Exception e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNotNull("Recherche reussie car listeOperation n'est pas null", listeOperation);
		Assert.assertTrue("Recherche reussie car listeOperation n'est pas null mais est vide",
				listeOperation.size() == 0);
	}

	@Test(expected = CompteIntrouvableException.class)
	public void testerRechercherOpCompteInvalid() throws CompteIntrouvableException {
		List<IOperation> listeOperation = null;
		try {
			listeOperation = this.db.rechercherOp(-1, null, null, null);
		} catch (SQLException e) {
			Assert.fail("Erreur SQL (" + e.getMessage() + ")");
		}
		Assert.assertNull("ListeOperation est null car on a pas trouve le compte", listeOperation);
	}
}
