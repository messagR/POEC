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
				cpte.setType("Compte à seuil rémunéré");
			} else {
				if (arg0 instanceof CompteASeuil) {
					CompteASeuil cpteASeuil = (CompteASeuil) arg0;
					cpte.setSeuil(cpteASeuil.getSeuil());
					cpte.setType("Compte à seuil");
				} else {
					if (arg0 instanceof CompteRemunere) {
						CompteRemunere cpteRemunere = (CompteRemunere) arg0;
						cpte.setTaux(cpteRemunere.getTaux());
						cpte.setType("Compte rémunéré");
					}
				}
			}
		}
		return cpte;
	}

	@Override
	public Compte unmarshal(ConteneurCompte arg0) throws Exception {
		Compte cpte = new Compte(arg0.getNumero(), arg0.getSolde());
		if (arg0.getType().equals("Compte à seuil rémunéré")) {
			cpte = new CompteASeuilRemunere(arg0.getNumero(), arg0.getSolde(), arg0.getTaux(), arg0.getSeuil());
		} else {
			if (arg0.getType().equals("Compte à seuil")) {
				cpte = new CompteASeuil(arg0.getNumero(), arg0.getSolde(), arg0.getSeuil());
			} else {
				if (arg0.getType().equals("Compte rémunéré")) {
					cpte = new CompteRemunere(arg0.getNumero(), arg0.getSolde(), arg0.getTaux());
				}
			}
		}
		cpte.setLibelle(arg0.getLibelle());
		return cpte;
	}

}
