/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import java.util.ArrayList;
import java.util.List;

import com.banque.dao.CompteDAO;
import com.banque.dao.ICompteDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.ICompteEntity;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.EntityIntrouvableException;
import com.banque.service.ex.ErreurTechniqueException;

/**
 * Gestion des comptes.
 */
public class CompteService extends AbstractService implements ICompteService {

	private ICompteDAO compteDao;

	/**
	 * Constructeur de l'objet.
	 */
	public CompteService() {
		super();
		this.setCompteDao(new CompteDAO());
	}

	/**
	 * Recupere la propriete <i>compteDao</i>.
	 *
	 * @return the compteDao la valeur de la propriete.
	 */
	public ICompteDAO getCompteDao() {
		return this.compteDao;
	}

	/**
	 * Fixe la propriete <i>compteDao</i>.
	 *
	 * @param pCompteDao
	 *            la nouvelle valeur pour la propriete compteDao.
	 */
	public void setCompteDao(ICompteDAO pCompteDao) {
		this.compteDao = pCompteDao;
	}

	@Override
	public ICompteEntity select(Integer unUtilisateurId, Integer unCompteId)
			throws EntityIntrouvableException, AucunDroitException, NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteId == null) {
			throw new NullPointerException("compteId");
		}
		ICompteEntity resultat = null;
		try {
			resultat = this.getCompteDao().select(unCompteId, null);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (resultat == null) {
			throw new EntityIntrouvableException();
		}

		if (!unUtilisateurId.equals(resultat.getUtilisateurId())) {
			throw new AucunDroitException();
		}

		return resultat;
	}

	@Override
	public List<ICompteEntity> selectAll(Integer unUtilisateurId)
			throws EntityIntrouvableException, AucunDroitException, NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		List<ICompteEntity> resultat = new ArrayList<ICompteEntity>();
		try {
			resultat = this.getCompteDao().selectAll("entity.utilisateurId=" + unUtilisateurId, "entity.libelle ASC",
					null);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}

		return resultat;
	}
}