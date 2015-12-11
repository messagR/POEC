/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banque.dao.ICompteDAO;
import com.banque.dao.IOperationDAO;
import com.banque.dao.ex.ExceptionDao;
import com.banque.entity.ICompteEntity;
import com.banque.entity.IOperationEntity;
import com.banque.entity.OperationEntity;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.DecouvertException;
import com.banque.service.ex.EntityIntrouvableException;
import com.banque.service.ex.ErreurTechniqueException;

/**
 * Gestion des operations.
 */
@Service("operationService")
// @Transactional(propagation = Propagation.REQUIRED)
public class OperationService extends AbstractService implements
IOperationService {

	@Autowired
	private IOperationDAO operationDao;

	@Autowired
	private ICompteDAO compteDao;

	/**
	 * Constructeur de l'objet.
	 */
	public OperationService() {
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

	/**
	 * Recupere la propriete <i>operationDao</i>.
	 *
	 * @return the operationDao la valeur de la propriete.
	 */
	public IOperationDAO getOperationDao() {
		return this.operationDao;
	}

	@Override
	// @Transactional(readOnly = true)
	public IOperationEntity select(Integer unUtilisateurId, Integer unCompteId,
			Integer uneOperationId) throws EntityIntrouvableException,
	AucunDroitException, NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteId == null) {
			throw new NullPointerException("compteId");
		}
		if (uneOperationId == null) {
			throw new NullPointerException("operationId");
		}

		// On verifie que le compte appartient bien a l'utilisateur
		ICompteEntity compte = null;
		try {
			compte = this.getCompteDao().select(unCompteId);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (compte == null) {
			throw new EntityIntrouvableException();
		}
		if (!unUtilisateurId.equals(compte.getUtilisateurId())) {
			throw new AucunDroitException();
		}

		IOperationEntity resultat = null;
		try {
			resultat = this.getOperationDao().select(uneOperationId);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (resultat == null) {
			throw new EntityIntrouvableException();
		}
		if (!unCompteId.equals(resultat.getCompteId())) {
			throw new AucunDroitException();
		}

		return resultat;
	}

	@Override
	// @Transactional(readOnly = true)
	public List<IOperationEntity> selectAll(Integer unUtilisateurId,
			Integer unCompteId) throws EntityIntrouvableException,
	AucunDroitException, NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteId == null) {
			throw new NullPointerException("compteId");
		}

		ICompteEntity compte;
		try {
			compte = this.getCompteDao().select(unCompteId);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
		if (compte == null) {
			throw new EntityIntrouvableException();
		}
		if (!unUtilisateurId.equals(compte.getUtilisateurId())) {
			throw new AucunDroitException();
		}

		List<IOperationEntity> resultat = new ArrayList<IOperationEntity>();
		try {
			resultat = this.getOperationDao().selectAll(
					"compteId=" + unCompteId, "date DESC");
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}

		return resultat;
	}

	@Override
	// @Transactional(readOnly = true)
	public List<IOperationEntity> selectCritere(Integer unUtilisateurId,
			Integer unCompteId, Date unDebut, Date uneFin, boolean pCredit,
			boolean pDebit) throws EntityIntrouvableException,
	AucunDroitException, NullPointerException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteId == null) {
			throw new NullPointerException("compteId");
		}
		Boolean crediDebit = null;
		if (pCredit && !pDebit) {
			crediDebit = Boolean.TRUE;
		} else if (!pCredit && pDebit) {
			crediDebit = Boolean.FALSE;
		}

		try {
			return this.getOperationDao().selectCriteria(unCompteId, unDebut,
					uneFin, crediDebit);
		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}
	}

	@Override
	// @Transactional(rollbackFor = Throwable.class)
	public List<IOperationEntity> faireVirement(Integer unUtilisateurId, Integer unCompteIdSrc, Integer unCompteIdDst,
			Double unMontant) throws EntityIntrouvableException, AucunDroitException, NullPointerException,
	DecouvertException, ErreurTechniqueException {
		if (unUtilisateurId == null) {
			throw new NullPointerException("utilisateurId");
		}
		if (unCompteIdSrc == null) {
			throw new NullPointerException("compteIdSrc");
		}
		if (unCompteIdDst == null) {
			throw new NullPointerException("compteIdDst");
		}
		if (unMontant == null) {
			throw new NullPointerException("montant");
		}

		IOperationEntity opSrc = null;
		IOperationEntity opDst = null;
		try {
			ICompteEntity compteSrc = null;
			try {
				compteSrc = this.getCompteDao().select(unCompteIdSrc);
			} catch (ExceptionDao e) {
				throw new ErreurTechniqueException(e);
			}
			if (compteSrc == null) {
				throw new EntityIntrouvableException();
			}
			if (!unUtilisateurId.equals(compteSrc.getUtilisateurId())) {
				throw new AucunDroitException();
			}
			ICompteEntity compteDst = null;
			try {
				compteDst = this.getCompteDao().select(unCompteIdDst);
			} catch (ExceptionDao e) {
				throw new ErreurTechniqueException(e);
			}
			if (compteDst == null) {
				throw new EntityIntrouvableException();
			}
			if (!unUtilisateurId.equals(compteDst.getUtilisateurId())) {
				throw new AucunDroitException();
			}

			double montant = unMontant.doubleValue();
			// Simulation
			double soldeSrc = compteSrc.getSolde().doubleValue();
			final double decouvertSrc = compteSrc.getDecouvert().doubleValue();
			double soldeDst = compteDst.getSolde().doubleValue();
			final double decouvertDst = compteDst.getDecouvert().doubleValue();

			// On retire de la source
			soldeSrc -= montant;
			// On ajoute à destination
			soldeDst += montant;
			// On regarde si les decouverts suivent
			if (soldeSrc > decouvertSrc) {
				// tout est ok
			} else {
				throw new DecouvertException();
			}
			if (soldeDst > decouvertDst) {
				// tout est ok
			} else {
				throw new DecouvertException();
			}

			Timestamp now = new Timestamp(System.currentTimeMillis());
			opSrc = new OperationEntity();
			opSrc.setCompteId(unCompteIdSrc);
			opSrc.setDate(now);
			opSrc.setMontant(Double.valueOf(-montant));
			opSrc.setLibelle("Transaction avec le comte " + unCompteIdDst);

			opDst = new OperationEntity();
			opDst.setCompteId(unCompteIdDst);
			opDst.setDate(now);
			opDst.setMontant(unMontant);
			opDst.setLibelle("Transaction avec le comte " + unCompteIdSrc);

			opSrc = this.getOperationDao().insert(opSrc);
			opDst = this.getOperationDao().insert(opDst);
			if (true) {
				throw new ExceptionDao("");
			}
			compteSrc.setSolde(Double.valueOf(soldeSrc));
			compteDst.setSolde(Double.valueOf(soldeDst));
			this.getCompteDao().update(compteSrc);
			this.getCompteDao().update(compteDst);

		} catch (ExceptionDao e) {
			throw new ErreurTechniqueException(e);
		}

		List<IOperationEntity> resultat = new ArrayList<IOperationEntity>(2);
		resultat.add(opSrc);
		resultat.add(opDst);

		return resultat;
	}
}