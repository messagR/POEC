/**
 * Copyright : Ferret Renaud 2002 <br/>
 *
 * @version 1.0<br/>
 */
package com.banque.service;

import java.util.Date;
import java.util.List;

import com.banque.entity.IOperationEntity;
import com.banque.service.ex.AucunDroitException;
import com.banque.service.ex.DecouvertException;
import com.banque.service.ex.EntityIntrouvableException;
import com.banque.service.ex.ErreurTechniqueException;

/**
 * Service Operation
 */
public interface IOperationService {

	/**
	 * Recupere une operation.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @param unCompteId
	 *            un id de compte
	 * @param uneOperationId
	 *            un id d'operation
	 *
	 * @return l'operation trouvee
	 * @throws EntityIntrouvableException
	 *             si l'operation n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a cette operation
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract IOperationEntity select(Integer unUtilisateurId, Integer unCompteId, Integer uneOperationId)
			throws EntityIntrouvableException, AucunDroitException, NullPointerException, ErreurTechniqueException;

	/**
	 * Recupere toutes les operations d'un compte.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @param unCompteId
	 *            un id de compte
	 *
	 * @return les operations trouvees, une liste vide si aucunne.
	 * @throws EntityIntrouvableException
	 *             si le compte, l'utilisateur n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a aux operations
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract List<IOperationEntity> selectAll(Integer unUtilisateurId, Integer unCompteId)
			throws EntityIntrouvableException, AucunDroitException, NullPointerException, ErreurTechniqueException;

	/**
	 * Recupere toutes les operations respectant les criteres donnees.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @param unCompteId
	 *            un id de compte
	 * @param unDebut
	 *            une date de debut
	 * @param uneFin
	 *            une date de fin
	 * @param pCredit
	 *            si vrai remonte les credit
	 * @param pDebit
	 *            si vrai remmonte les debits
	 *
	 * @return les operations trouvees, une liste vide si aucunne.
	 * @throws EntityIntrouvableException
	 *             si le compte, l'utilisateur n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a aux operations
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract List<IOperationEntity> selectCritere(Integer unUtilisateurId, Integer unCompteId, Date unDebut,
			Date uneFin, boolean pCredit, boolean pDebit) throws EntityIntrouvableException, AucunDroitException,
					NullPointerException, ErreurTechniqueException;

	/**
	 * Fait un virement entre deux comptes.
	 *
	 * @param unUtilisateurId
	 *            un id d'utilisateur
	 * @param unCompteIdSrc
	 *            compte source
	 * @param unCompteIdDst
	 *            compte destination
	 * @param unMontant
	 *            la somme +/-
	 *
	 * @return les deux operations crees par le virement
	 * @throws EntityIntrouvableException
	 *             si le compte, l'utilisateur n'existe pas
	 * @throws AucunDroitException
	 *             si l'utilisateur n'a pas le droit d'acceder a aux comptes
	 * @throws NullPointerException
	 *             si un des parametres est nul
	 * @throws DecouvertException
	 *             si le decouvert est depasse
	 * @throws ErreurTechniqueException
	 *             si un probleme survient
	 */
	public abstract List<IOperationEntity> faireVirement(Integer unUtilisateurId, Integer unCompteIdSrc,
			Integer unCompteIdDst, Double unMontant) throws EntityIntrouvableException, AucunDroitException,
					NullPointerException, DecouvertException, ErreurTechniqueException;

}