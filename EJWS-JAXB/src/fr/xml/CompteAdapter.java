package fr.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import fr.banque.Compte;
import fr.banque.CompteASeuil;
import fr.banque.CompteASeuilRemunere;
import fr.banque.CompteRemunere;

public class CompteAdapter extends XmlAdapter<ConteneurCompte, Compte> {

	@Override
	public ConteneurCompte marshal(Compte arg0) throws Exception {
		ConteneurCompte cpte = null;
		if (arg0 != null) {
			cpte = new ConteneurCompte();
			cpte.setLibelle(arg0.getLibelle());
			cpte.setNumero(arg0.getNumero());
			cpte.setSolde(arg0.getSolde());
			cpte.setType("Compte");
			if (arg0 instanceof CompteASeuilRemunere) {
				CompteASeuilRemunere cpteASeuilRemunere = (CompteASeuilRemunere) arg0;
				cpte.setTaux(cpteASeuilRemunere.getTaux());
				cpte.setSeuil(cpteASeuilRemunere.getSeuil());
				cpte.setType("Compte � seuil r�mun�r�");
			} else {
				if (arg0 instanceof CompteASeuil) {
					CompteASeuil cpteASeuil = (CompteASeuil) arg0;
					cpte.setSeuil(cpteASeuil.getSeuil());
					cpte.setType("Compte � seuil");
				} else {
					if (arg0 instanceof CompteRemunere) {
						CompteRemunere cpteRemunere = (CompteRemunere) arg0;
						cpte.setTaux(cpteRemunere.getTaux());
						cpte.setType("Compte r�mun�r�");
					}
				}
			}
		}
		return cpte;
	}

	@Override
	public Compte unmarshal(ConteneurCompte arg0) throws Exception {
		Compte cpte = new Compte(arg0.getNumero(), arg0.getSolde());
		if (arg0.getType().equals("Compte � seuil r�mun�r�")) {
			cpte = new CompteASeuilRemunere(arg0.getNumero(), arg0.getSolde(), arg0.getTaux(), arg0.getSeuil());
		} else {
			if (arg0.getType().equals("Compte � seuil")) {
				cpte = new CompteASeuil(arg0.getNumero(), arg0.getSolde(), arg0.getSeuil());
			} else {
				if (arg0.getType().equals("Compte r�mun�r�")) {
					cpte = new CompteRemunere(arg0.getNumero(), arg0.getSolde(), arg0.getTaux());
				}
			}
		}
		cpte.setLibelle(arg0.getLibelle());
		return cpte;
	}

}
