/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banque.dao.ICompteDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.ICompteEntity;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.EntityIntrouvableException;
import com.banque.service.ex.ErreurTechniqueException;

/**
 * Gestion des comptes.
 */
@Service("compteService")
// @Transactional(propagation = Propagation.REQUIRED)
public class CompteService extends AbstractService implements ICompteService {

	@Autowired
	private ICompteDAO compteDao;

	/**
	 * Constructeur de l'objet.
	 */
	public CompteService() {
		super();
	}

	/**
	 * Recupere la propriete <i>compteDao</i>.
	 *
	 * @return the compteDao la valeur de la propriete.
	 */
	public ICompteDAO getCompteDao() {
		return this.compteDao;
	}

	@Override
	// @Transactional(readOnly = true)
	public ICompteEntity select(Integer unUtilisateurId, Integer unCompteId)
			throws EntityIntrouvableException, AucunDroitException,
			NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteId == null) {
			throw new NullPointerException("compteId");
		}
		ICompteEntity resultat = null;
		try {
			resultat = this.getCompteDao().select(unCompteId);
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
	// @Transactional(readOnly = true)
	public List<ICompteEntity> selectAll(Integer unUtilisateurId)
			throws EntityIntrouvableException, AucunDroitException,
			NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		List<ICompteEntity> resultat = new ArrayList<ICompteEntity>();
		try {
			resultat = this.getCompteDao().selectAll(
					"utilisateurId=" + unUtilisateurId, "libelle ASC");
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}

		return resultat;
	}
}