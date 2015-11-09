package fr.banque;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.banque.entity.Factory;
import fr.banque.entity.IClient;
import fr.banque.entity.ICompte;
import fr.banque.entity.ICompteASeuil;
import fr.banque.entity.ICompteASeuilRemunere;
import fr.banque.entity.ICompteRemunere;
import fr.banque.exception.BanqueException;
import fr.banque.log.CustomMessage;

public class Run {
	private final static Logger LOG = LogManager.getLogger();

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Run.LOG.info("-- Start --");

		Factory f = Factory.getInstance();
		IClient[] listeClient = new IClient[5];
		Run.LOG.trace("Creation des clients");
		listeClient[0] = f.creerClient(1, "DUPONT", "Jean", 20, null, null, null, null, 0, 0, null);
		listeClient[1] = f.creerClient(2, "DUPOND", "Jeanne", 25, null, null, null, null, 0, 0, null);
		listeClient[2] = f.creerClient(3, "DURANT", "Paul", 30, null, null, null, null, 0, 0, null);
		listeClient[3] = f.creerClient(4, "DURAND", "Paula", 35, null, null, null, null, 0, 0, null);

		Run.LOG.trace("Creation des comptes du client");
		ICompte compte = null;
		ICompteRemunere compteRemunere = null;
		ICompteASeuil compteASeuil = null;
		ICompteASeuilRemunere compteASeuilRemunere = null;
		try {
			compte = f.creerCompte();
			listeClient[0].ajouterCompte(compte);
			compte.ajouter(100d);
		} catch (BanqueException e1) {
			Run.LOG.error(new CustomMessage(CustomMessage.TypeCible.COMPTE, f.getDernierNumeroCompte(),
					"Erreur lors de la cr�ation du compte ", e1));
		}

		compte = null;
		try {
			compte = f.creerCompte(ICompte.class, 1, "", 100d);
			listeClient[0].ajouterCompte(compte);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteRemunere = null;
		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class);
			listeClient[0].ajouterCompte(compteRemunere);
			compteRemunere.ajouter(100d);
			compteRemunere.setTaux(0.2);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteRemunere = null;
		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class, 1, "", 100d);
			listeClient[0].ajouterCompte(compteRemunere);
			compteRemunere.setTaux(0.4);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteRemunere = null;
		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class, 1, "", 100d, 0.6);
			listeClient[0].ajouterCompte(compteRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compte = null;
		try {
			compte = f.creerCompte(ICompte.class);
			listeClient[0].ajouterCompte(compte);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class);
			listeClient[1].ajouterCompte(compteASeuil);
			compteASeuil.ajouter(200d);
			compteASeuil.setSeuil(10d);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class);
			listeClient[1].ajouterCompte(compteASeuil);
			compteASeuil.ajouter(100d);
			compteASeuil.setSeuil(150d);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 1, "", 100d);
			listeClient[1].ajouterCompte(compteASeuil);
			compteASeuil.setSeuil(10d);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 1, "", 100d);
			listeClient[1].ajouterCompte(compteASeuil);
			compteASeuil.setSeuil(150d);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 1, "", 100d, 150d);
			listeClient[2].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuil = null;
		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 1, "", 100d, 10d);
			listeClient[2].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuilRemunere = null;
		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class);
			listeClient[2].ajouterCompte(compteASeuilRemunere);
			compteASeuilRemunere.ajouter(100d);
			compteASeuilRemunere.setSeuil(150d);
			compteASeuilRemunere.setTaux(0.2);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuilRemunere = null;
		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 1, "", 100d);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
			compteASeuilRemunere.setSeuil(150d);
			compteASeuilRemunere.setTaux(0.2);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuilRemunere = null;
		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 1, "", 100d,
					150d);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
			compteASeuilRemunere.setTaux(0.2);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		compteASeuilRemunere = null;
		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 1, "", 100d, 150d,
					0.2);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		for (IClient client : listeClient) {
			if (client != null) {
				System.out.println("Client n�" + client.getNumero());
				if (client.getComptes() != null) {
					for (ICompte compteClient : client.getComptes()) {
						if (compteClient != null) {
							System.out.println(
									"    " + compteClient.getClass().getSimpleName() + " n�" + compteClient.getNumero());
							try {
								compteClient.retirer(100);
								System.out.println("         retrait effectue");
							} catch (BanqueException e) {
								e.printStackTrace();
							}
							compteClient.ajouter(100);
							System.out.println("         ajout effectue");
							if (compteClient instanceof ICompteRemunere) {
								ICompteRemunere cptRem = (ICompteRemunere) compteClient;
								System.out.println("         " + cptRem.verserInterets());
							}
						}
					}
				}
			}
		}


		// if (listeClient[0].getCompte(1) == null) {
		// System.out.println("compte inexistant pour client " +
		// listeClient[0]);
		// } else {
		// System.out.println(listeClient[0].getCompte(1));
		// }
	}

}
