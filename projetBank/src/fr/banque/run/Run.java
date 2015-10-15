package fr.banque.run;

import fr.banque.BanqueException;
import fr.banque.Factory;
import fr.banque.IClient;
import fr.banque.ICompte;
import fr.banque.ICompteASeuil;
import fr.banque.ICompteASeuilRemunere;
import fr.banque.ICompteRemunere;

public class Run {

	public static void main(String[] args) {

		Factory f = Factory.getInstance();
		IClient[] listeClient = new IClient[5];
		listeClient[0] = f.creerClient("DUPONT", "Jean", 20);
		listeClient[1] = f.creerClient("DUPOND", "Jeanne", 25);
		listeClient[2] = f.creerClient("DURANT", "Paul", 30);
		listeClient[3] = f.creerClient("DURAND", "Paula", 35);

		ICompte compte;
		ICompteRemunere compteRemunere;
		ICompteASeuil compteASeuil;
		ICompteASeuilRemunere compteASeuilRemunere;
		try {
			compte = f.creerCompte();
			compte.ajouter(100d);
			listeClient[0].ajouterCompte(compte);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compte = f.creerCompte(ICompte.class, 100d);
			listeClient[0].ajouterCompte(compte);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class);
			compteRemunere.ajouter(100d);
			compteRemunere.setTaux(0.2);
			listeClient[0].ajouterCompte(compteRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class, 100d);
			compteRemunere.setTaux(0.4);
			listeClient[0].ajouterCompte(compteRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteRemunere = (ICompteRemunere) f.creerCompte(ICompteRemunere.class, 100d, 0.6);
			listeClient[0].ajouterCompte(compteRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compte = f.creerCompte(ICompte.class);
			listeClient[0].ajouterCompte(compte);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class);
			compteASeuil.ajouter(200d);
			compteASeuil.setSeuil(10d);
			listeClient[1].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class);
			compteASeuil.ajouter(100d);
			compteASeuil.setSeuil(150d);
			listeClient[1].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 100d);
			compteASeuil.setSeuil(10d);
			listeClient[1].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 100d);
			compteASeuil.setSeuil(150d);
			listeClient[1].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 100d, 150d);
			listeClient[2].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuil = (ICompteASeuil) f.creerCompte(ICompteASeuil.class, 100d, 10d);
			listeClient[2].ajouterCompte(compteASeuil);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class);
			compteASeuilRemunere.ajouter(100d);
			compteASeuilRemunere.setSeuil(150d);
			compteASeuilRemunere.setTaux(0.2);
			listeClient[2].ajouterCompte(compteASeuilRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 100d);
			compteASeuilRemunere.setSeuil(150d);
			compteASeuilRemunere.setTaux(0.2);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 100d, 150d);
			compteASeuilRemunere.setTaux(0.2);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		try {
			compteASeuilRemunere = (ICompteASeuilRemunere) f.creerCompte(ICompteASeuilRemunere.class, 100d, 150d, 0.2);
			listeClient[3].ajouterCompte(compteASeuilRemunere);
		} catch (BanqueException e1) {
			e1.printStackTrace();
		}

		for (IClient client : listeClient) {
			System.out.println("Client n°" + client.getNumero());
			if (client.getComptes() != null) {
				for (ICompte compteClient : client.getComptes()) {
					if (compteClient != null) {
						System.out.println(
								"    " + compteClient.getClass().getSimpleName() + " n°" + compteClient.getNumero());
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


		// if (listeClient[0].getCompte(1) == null) {
		// System.out.println("compte inexistant pour client " +
		// listeClient[0]);
		// } else {
		// System.out.println(listeClient[0].getCompte(1));
		// }
	}

}
